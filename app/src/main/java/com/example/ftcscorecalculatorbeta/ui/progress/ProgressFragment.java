package com.example.ftcscorecalculatorbeta.ui.progress;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Scanner;

public class ProgressFragment extends Fragment {

    final static String TAG = "ProgressFragment";
    private boolean blnInitalized = false;
    private ProgressViewModel progressViewModel;
    LineGraphSeries<DataPoint> series;
    public GraphView GraphView;
    //private ProgressViewModel progressViewModel;
    public Score totalScore;

    //totalScore = (TextView) itemView.findViewById(R.id.total_score);

   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       updateGraph();
       LayoutInflater inflater = null;
       ViewGroup container = null;
       RVScoreAdapter.ScoreViewHolder holder = null;
       int position = 0;
       View view = null;
       onCreateViewGraph(inflater, container, savedInstanceState, holder, position, view);

                //(LayoutInflater,
                //ViewGroup, Bundle savedInstanceState, @NonNull RVScoreAdapter.ScoreViewHolder holder, int position);
                //@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState
        //@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState, @NonNull RVScoreAdapter.ScoreViewHolder holder, int position, View view
    }



    private void updateGraph()
    {
        if (blnInitalized) return;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        MainActivity activity = (MainActivity) getActivity();

        db.collection("Scores")
                .whereEqualTo("TotalScore", activity.getMyTotalScore().TotalScore)
                .orderBy("CreatedTimestamp", Query.Direction.DESCENDING)
                .limit(5)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Score score = document.toObject(Score.class);
                            }


                            blnInitalized = true;
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }

                });


    }

   /* private void onDataChange() {

    };*/

        /*private List<Score> scores = new ArrayList<>();
            Arrays.asList*/
        List<Score> scores;

    public View onCreateViewGraph(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState, @NonNull RVScoreAdapter.ScoreViewHolder holder, int position, View view) {
        progressViewModel =  ViewModelProviders.of(this).get(ProgressViewModel.class);
        View root = inflater.inflate(R.layout.fragment_progress, container, false);
        updateGraph(); {


            ArrayList<Integer> scoreGraph;
            scoreGraph = new ArrayList<Integer>();
            scoreGraph.add(Integer.valueOf(String.valueOf(scores.get(position).TotalScore)));
        }


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        MainActivity activity = (MainActivity) getActivity();

        /*db.collection("Scores")
                .whereEqualTo("TotalScore", activity.getMyTeam().TotalScore)
                .orderBy("CreatedTimestamp", Query.Direction.DESCENDING)
                .limit(5);*/
       // List<Map<String, Object>> scores= chopped(scoreGraph, 1);
        //queryForRecentScoresForMyTeam();

        /*Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d4 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d5 = calendar.getTime();*/

        GraphView graph = (GraphView) root.findViewById(R.id.progress_graph);
        //GridLabelRenderer glr = gv.getGridLabelRenderer();
        //glr.setPadding(32);

// you can directly pass Date objects to DataPoint-Constructor
// this will convert the Date to double via Date#getTime()
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {

                new DataPoint(0, 1),
                new DataPoint(1, 6),
                new DataPoint(2, 1),
                new DataPoint(3, 3),
                new DataPoint(4, 1)

                /*new DataPoint(0, scoreGraph.get(0)),
                new DataPoint(1, scoreGraph.get(1)),
                new DataPoint(2, scoreGraph.get(2)),
                new DataPoint(3, scoreGraph.get(3)),
                new DataPoint(4, scoreGraph.get(4))*/
        });

        graph.addSeries(series);

// set date label formatter
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"old", "", "", "", "new"});
        //staticLabelsFormatter.setVerticalLabels(new String[] {"low", "middle", "high"});

        graph.getGridLabelRenderer().setHorizontalAxisTitle("Recent Activity");
                //(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(0);
        graph.getGridLabelRenderer().setNumVerticalLabels(11);

// set manual x bounds to have nice steps
        graph.getViewport().setMinY(0.0);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(4);
        graph.getViewport().setXAxisBoundsManual(true);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        /*GraphView graph = (GraphView) root.findViewById(R.id.progress_graph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });

        graph.addSeries(series);*/


        /*progressViewModel =
                ViewModelProviders.of(this).get(ProgressViewModel.class);
        View root = inflater.inflate(R.layout.fragment_progress, container, false);

        queryForRecentScoresForMyTeam();*/

        return root;
    }

}