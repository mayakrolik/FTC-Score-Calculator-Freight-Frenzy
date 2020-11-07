package com.example.ftcscorecalculatorbeta.ui.progress;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ftcscorecalculatorbeta.MainActivity;
import com.example.ftcscorecalculatorbeta.R;
import com.example.ftcscorecalculatorbeta.RVScoreAdapter;
import com.example.ftcscorecalculatorbeta.data.model.Score;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProgressFragment extends Fragment {

    final static String TAG = "ProgressFragment";

    private boolean blnInitalized = false;

    private ProgressViewModel progressViewModel;
    //private ArrayList<QueryDocumentSnapshot> results = new ArrayList<QueryDocumentSnapshot>();


    private void queryForRecentScoresForMyTeam()
    {
        if (blnInitalized) return;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        MainActivity activity = (MainActivity) getActivity();

        db.collection("Scores")
                .whereEqualTo("TeamId", activity.strFirstTeamId)
                .orderBy("CreatedTimestamp", Query.Direction.DESCENDING)
                .limit(50)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Score score = document.toObject(Score.class);
                                scores.add(score);
                                //results.add(document);
                            }

                            RecyclerView rv = (RecyclerView)  getView().findViewById(R.id.rv);
                            RVScoreAdapter adapter = new RVScoreAdapter(scores);
                            rv.setAdapter(adapter);
                            blnInitalized = true;
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    private List<Score> scores = new ArrayList<>();;

    // This method creates an ArrayList that has three Score objects
    //private void initializeData(){
    //    scores = new ArrayList<>();
    //    scores.add(new Score("Emma Wilson", 1));
    //    scores.add(new Score("Lavery Maiss", 43));
    //    scores.add(new Score("Lillie Watts", 4343));
    //}


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        progressViewModel =  ViewModelProviders.of(this).get(ProgressViewModel.class);
        View root = inflater.inflate(R.layout.fragment_progress, container, false);
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

        RecyclerView rv = (RecyclerView)root.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        RVScoreAdapter adapter = new RVScoreAdapter(scores);
        rv.setAdapter(adapter);

        //rv.setHasFixedSize(true);

        return root;
    }

}