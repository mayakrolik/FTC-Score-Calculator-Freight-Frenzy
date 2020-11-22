package com.example.ftcscorecalculatorbeta.ui.progress;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

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

import java.util.LinkedList;
import java.util.List;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class ProgressFragment extends Fragment {

    final static String TAG = "ProgressFragment";
    private ProgressViewModel progressViewModel;

    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        progressViewModel =  ViewModelProviders.of(this).get(ProgressViewModel.class);
        View root = inflater.inflate(R.layout.fragment_progress, container, false);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        MainActivity activity = (MainActivity) getActivity();

        final List<DataPoint> lstPoints = new LinkedList<DataPoint>();
        final GraphView graph = (GraphView) root.findViewById(R.id.progress_graph);

        db.collection("Scores")
                .whereEqualTo("TeamNumber", activity.getMyTeam().TeamNumber)
                .orderBy("CreatedTimestamp", Query.Direction.DESCENDING)
                .limit(5)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            double i = 1;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Double dval = new Double((long) document.get("TotalScore"));
                                DataPoint dp = new DataPoint( i, dval.doubleValue());
                                lstPoints.add(dp);
                                i++;

                            }
                            DataPoint[] arrPoints = new DataPoint[lstPoints.size()];

                            int j = 0;
                            for (DataPoint dp : lstPoints)
                            {
                                arrPoints[j] = dp;
                                j++;
                            }

                            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(arrPoints);

                            graph.addSeries(series);
                            Log.d(TAG, "Added data to graph");

                            StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                            staticLabelsFormatter.setHorizontalLabels(new String[] {"Old", "", "", "", "New"});

                            graph.getGridLabelRenderer().setHorizontalAxisTitle("Recent Activity");
                            graph.getGridLabelRenderer().setNumHorizontalLabels(0);
                            graph.getGridLabelRenderer().setNumVerticalLabels(5);

                            // set manual x bounds to have nice steps
                            graph.getViewport().setMinY(0.0);
                            graph.getViewport().setMinX(0);
                            graph.getViewport().setMaxX(4);
                            graph.getViewport().setXAxisBoundsManual(true);

                            // as we use dates as labels, the human rounding to nice readable numbers
                            // is not necessary
                            graph.getGridLabelRenderer().setHumanRounding(false);
                            graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);



                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }

                });

        // set date label formatter

        return root;
    }

}