package com.example.ftcscorecalculatorbeta.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ftcscorecalculatorbeta.MainActivity;
import com.example.ftcscorecalculatorbeta.R;
import com.example.ftcscorecalculatorbeta.RVScoreAdapter;
import com.example.ftcscorecalculatorbeta.data.model.Score;
import com.example.ftcscorecalculatorbeta.ui.home.HomeViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Date;
import java.util.Map;

public class HomeFragment extends Fragment {


    final static String TAG = "HomeFragment";

    private boolean blnInitalized = false;
    public ImageButton objKudosButton;
    private String filterOption = "MyTeam";
    private String filterCategoryOption = "Recent";
    private HomeViewModel homeViewModel;
    public Button FilterCity;
    public Button FilterState;
    public Button FilterCountry;
    public Button FilterTeam;
    public Button FilterRecentScores;
    public Button FilterTopScores;


    private void queryForRecentScoresForMyTeam() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        MainActivity activity = (MainActivity) getActivity();


        OnCompleteListener<QuerySnapshot> oncompletelistener = new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    scores.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        Score score = document.toObject(Score.class);
                        scores.add(score);
                    }

                    RecyclerView rv = (RecyclerView) getView().findViewById(R.id.rv);
                    RVScoreAdapter adapter = (RVScoreAdapter) rv.getAdapter();
                    adapter.notifyDataSetChanged();

                    blnInitalized = true;
                    Toast.makeText(getContext(), "Feed Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        };


        // Get the current date
        Date currentDate = new Date();
        // convert date to calendar
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        // manipulate date  - subtract seven days
        c.add(Calendar.DATE, -7);
        // convert calendar to date
        Date oneWeekAgo = c.getTime();

        if (filterOption.equals("City")) {
            Query query = db.collection("Scores")
                    .whereEqualTo("City", activity.getMyTeam().City);
            if (filterCategoryOption.equals("Recent")) {
                query.whereGreaterThan("CreatedTimestamp", new Timestamp(oneWeekAgo))
                        .orderBy("CreatedTimestamp", Query.Direction.DESCENDING)
                        .limit(50)
                        .get()
                        .addOnCompleteListener(oncompletelistener);
            } else {
                query.orderBy("TotalScore", Query.Direction.DESCENDING)
                        .limit(50)
                        .get()
                        .addOnCompleteListener(oncompletelistener);
            }
        }

        if (filterOption.equals("StateProv")) {
            Query query = db.collection("Scores")
                    .whereEqualTo("StateProv", activity.getMyTeam().StateProv);
            if (filterCategoryOption.equals("Recent")) {
                query.whereGreaterThan("CreatedTimestamp", new Timestamp(oneWeekAgo))
                    .orderBy("CreatedTimestamp", Query.Direction.DESCENDING)
                    .limit(50)
                    .get()
                    .addOnCompleteListener(oncompletelistener);
            } else {
                query.orderBy("TotalScore", Query.Direction.DESCENDING)
                        .limit(50)
                        .get()
                        .addOnCompleteListener(oncompletelistener);
            }
        }
        if (filterOption.equals("MyTeam")) {
            Query query = db.collection("Scores")
                    .whereEqualTo("TeamNumber", activity.getMyTeam().TeamNumber);

            if (filterCategoryOption.equals("Recent")) {
                query.whereGreaterThan("CreatedTimestamp", new Timestamp(oneWeekAgo))
                    .orderBy("CreatedTimestamp", Query.Direction.DESCENDING)
                    .limit(50)
                    .get()
                    .addOnCompleteListener(oncompletelistener);
            } else {
                query.orderBy("TotalScore", Query.Direction.DESCENDING)
                        .limit(50)
                        .get()
                        .addOnCompleteListener(oncompletelistener);
            }
        }

        if (filterOption.equals("CountryCode")) {
            Query query = db.collection("Scores")
                    .whereEqualTo("CountryCode", activity.getMyTeam().CountryCode);

            if (filterCategoryOption.equals("Recent")) {
                query.orderBy("CreatedTimestamp", Query.Direction.DESCENDING)
                    .limit(50)
                    .get()
                    .addOnCompleteListener(oncompletelistener);
            } else {
                query.orderBy("TotalScore", Query.Direction.DESCENDING)
                        .limit(50)
                        .get()
                        .addOnCompleteListener(oncompletelistener);
            }
        }

    }

    private List<Score> scores = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        /*
        final TextView textView = root.findViewById(R.id.text_progress);
        progressViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
         */
        queryForRecentScoresForMyTeam();
        //initializeData();

        RecyclerView rv = (RecyclerView) root.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        RVScoreAdapter adapter = new RVScoreAdapter(scores);
        rv.setAdapter(adapter);

        //rv.setHasFixedSize(true);
        doOnCreate(root);
        return root;

    }

    private void doOnCreate(View view) {
        FilterCity = view.findViewById(R.id.filter_button_city);
        FilterState = view.findViewById(R.id.filter_button_state);
        FilterCountry = view.findViewById(R.id.filter_button_country);
        FilterTeam = view.findViewById(R.id.filter_button_my_team);
        FilterRecentScores = view.findViewById(R.id.filter_button_recent);
        FilterTopScores = view.findViewById(R.id.filter_button_top);

        FilterCity.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterOption = "City";
                FilterCity.setBackgroundColor(getResources().getColor(R.color.colorButtonActivated));
                FilterState.setBackgroundColor(getResources().getColor(R.color.colorButtonDeactivated));
                FilterTeam.setBackgroundColor(getResources().getColor(R.color.colorButtonDeactivated));
                FilterCountry.setBackgroundColor(getResources().getColor(R.color.colorButtonDeactivated));
                queryForRecentScoresForMyTeam();
            }
        });

        FilterState.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterOption = "StateProv";
                FilterCity.setBackgroundColor(getResources().getColor(R.color.colorButtonDeactivated));
                FilterState.setBackgroundColor(getResources().getColor(R.color.colorButtonActivated));
                FilterTeam.setBackgroundColor(getResources().getColor(R.color.colorButtonDeactivated));
                FilterCountry.setBackgroundColor(getResources().getColor(R.color.colorButtonDeactivated));
                queryForRecentScoresForMyTeam();
            }
        });

        FilterCountry.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterOption = "CountryCode";
                FilterCity.setBackgroundColor(getResources().getColor(R.color.colorButtonDeactivated));
                FilterState.setBackgroundColor(getResources().getColor(R.color.colorButtonDeactivated));
                FilterTeam.setBackgroundColor(getResources().getColor(R.color.colorButtonDeactivated));
                FilterCountry.setBackgroundColor(getResources().getColor(R.color.colorButtonActivated));
                queryForRecentScoresForMyTeam();
            }
        });

        FilterTeam.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterOption = "MyTeam";
                FilterCity.setBackgroundColor(getResources().getColor(R.color.colorButtonDeactivated));
                FilterState.setBackgroundColor(getResources().getColor(R.color.colorButtonDeactivated));
                FilterTeam.setBackgroundColor(getResources().getColor(R.color.colorButtonActivated));
                FilterCountry.setBackgroundColor(getResources().getColor(R.color.colorButtonDeactivated));
                queryForRecentScoresForMyTeam();
            }
        });

        FilterRecentScores.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterCategoryOption = "Recent";
                FilterRecentScores.setBackgroundColor(getResources().getColor(R.color.colorButtonActivated));
                FilterTopScores.setBackgroundColor(getResources().getColor(R.color.colorButtonDeactivated));
                queryForRecentScoresForMyTeam();
            }
        });

        FilterTopScores.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterCategoryOption = "Top";
                FilterTopScores.setBackgroundColor(getResources().getColor(R.color.colorButtonActivated));
                FilterRecentScores.setBackgroundColor(getResources().getColor(R.color.colorButtonDeactivated));
                queryForRecentScoresForMyTeam();
            }
        });

    }

}