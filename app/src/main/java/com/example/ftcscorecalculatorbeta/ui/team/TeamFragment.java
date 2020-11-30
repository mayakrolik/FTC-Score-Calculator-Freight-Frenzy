package com.example.ftcscorecalculatorbeta.ui.team;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ftcscorecalculatorbeta.MainActivity;
import com.example.ftcscorecalculatorbeta.R;
import com.example.ftcscorecalculatorbeta.RVScoreAdapter;
import com.example.ftcscorecalculatorbeta.RVTeamAdapter;
import com.example.ftcscorecalculatorbeta.data.model.Score;
import com.example.ftcscorecalculatorbeta.data.model.UserProfile;
import com.example.ftcscorecalculatorbeta.ui.calculator.CalculatorViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class TeamFragment extends Fragment {

    private boolean blnInitalized = false;
    private TeamViewModel teamViewModel;
    private TextView TeamName;
    private TextView TeamNumber;
    private TextView State;
    private TextView City;
    private TextView RookieYear;
    private TextView Level;

    final static String TAG = "TeamFragment";

    /*public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }*/

    private void queryForUsersForMyTeam() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        MainActivity activity = (MainActivity) getActivity();

        db.collection("Users")
                .whereEqualTo("TeamNumber", activity.getMyTeam().TeamNumber)
                .orderBy("DisplayName", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            users.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                try {
                                    UserProfile userProfile = document.toObject(UserProfile.class);
                                    users.add(userProfile);
                                } catch (Exception ex)
                                {
                                    // some bad data got in, but at least the app did not crash.
                                }
                            }

                            RecyclerView rv = (RecyclerView) getView().findViewById(R.id.rv_team);
                            RVTeamAdapter adapter = new RVTeamAdapter(users);
                            rv.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    private List<UserProfile> users = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        teamViewModel =
                ViewModelProviders.of(this).get(TeamViewModel.class);
        View root = inflater.inflate(R.layout.fragment_team, container, false);
        doOnCreate(root);
        queryForUsersForMyTeam();
        RecyclerView rv_team = (RecyclerView) root.findViewById(R.id.rv_team);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv_team.setLayoutManager(llm);

        RVTeamAdapter adapter = new RVTeamAdapter(users);
        rv_team.setAdapter(adapter);

        return root;
    }


    private void doOnCreate(View view)
    {
        TeamName = view.findViewById(R.id.team_team_name);
        TeamNumber = view.findViewById(R.id.team_team_number);
        State = view.findViewById(R.id.team_team_state);
        City = view.findViewById(R.id.team_team_city);
        RookieYear = view.findViewById(R.id.rookie_year);
        Level = view.findViewById(R.id.level);


        MainActivity activity = (MainActivity) getActivity();
        TeamName.setText(activity.getMyTeam().NickName);
        TeamNumber.setText("#" + activity.getMyTeam().TeamNumber);
        State.setText(activity.getMyTeam().StateProv);
        City.setText(activity.getMyTeam().City);
        RookieYear.setText(String.valueOf(activity.getMyTeam().RookieYear));
        Level.setText(activity.getMyTeam().ProgramName);

    }

}
