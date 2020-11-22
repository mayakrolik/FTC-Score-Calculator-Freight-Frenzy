package com.example.ftcscorecalculatorbeta.ui.home;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Map;

public class HomeFragment extends Fragment {



    final static String TAG = "HomeFragment";

    private boolean blnInitalized = false;
    public ImageButton objKudosButton;

    private HomeViewModel homeViewModel;


    private void queryForRecentScoresForMyTeam() {
        if (blnInitalized) return;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        MainActivity activity = (MainActivity) getActivity();

        db.collection("Scores")
                .whereEqualTo("TeamNumber", activity.getMyTeam().TeamNumber)
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

                            RecyclerView rv = (RecyclerView) getView().findViewById(R.id.rv);
                            RVScoreAdapter adapter = new RVScoreAdapter(scores);
                            rv.setAdapter(adapter);
                            blnInitalized = true;
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    private List<Score> scores = new ArrayList<>();
    ;

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
        //doOnCreate(root);
        return root;
    }

}