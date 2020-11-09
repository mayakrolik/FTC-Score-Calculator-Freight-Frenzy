package com.example.ftcscorecalculatorbeta;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ftcscorecalculatorbeta.data.model.Score;
import com.example.ftcscorecalculatorbeta.ui.calculator.CalculatorViewModel;
import com.example.ftcscorecalculatorbeta.ui.home.HomeViewModel;

import java.util.List;
import android.util.Log;

public class RVScoreAdapter extends RecyclerView.Adapter<RVScoreAdapter.ScoreViewHolder> {


    List<Score> scores;

    public RVScoreAdapter(List<Score> scores) {
        this.scores = scores;
    }

    final static String TAG = "RVScoreAdapter";

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.progress_score, parent, false);
        ScoreViewHolder holder = new ScoreViewHolder(view);
        Log.d(TAG, "onCreateViewHolder");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {

        holder.teamName.setText(String.valueOf(scores.get(position).TeamNumber));
        holder.totalScore.setText(String.valueOf(scores.get(position).TotalScore));
        holder.autonomousScore.setText(String.valueOf(scores.get(position).AutScore));
        holder.tellyopScore.setText(String.valueOf(scores.get(position).TelScore));
        holder.endScore.setText(String.valueOf(scores.get(position).EndScore));

        //holder.personPhoto.setImageResource(scores.get(position).photoId);
        Log.d(TAG, "onBindViewHolder");

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount " + String.valueOf(scores.size()));
        return scores.size();

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ScoreViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView teamName;
        TextView autonomousScore;
        TextView totalScore;
        TextView tellyopScore;
        TextView endScore;
        // ImageView personPhoto;
        ImageButton objHomeExpandButton;
        TableLayout objHomeTable;
        CardView objHomeCard;


        ScoreViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            teamName = (TextView) itemView.findViewById(R.id.team_name);
            totalScore = (TextView) itemView.findViewById(R.id.total_score);
            autonomousScore = (TextView) itemView.findViewById(R.id.autonomous_score);
            tellyopScore = (TextView) itemView.findViewById(R.id.tellyop_score);
            endScore = (TextView) itemView.findViewById(R.id.end_score);
            //personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
            doOnCreate(itemView);
        }


        private void doOnCreate(View view) {

            objHomeExpandButton = view.findViewById(R.id.home_expand_card);

            objHomeTable = view.findViewById(R.id.home_table_initial);

            objHomeCard = view.findViewById(R.id.cv);

            if (objHomeTable.getVisibility() == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(objHomeCard,
                        new AutoTransition());
                objHomeTable.setVisibility(View.GONE);
                objHomeExpandButton.setImageResource(R.drawable.ic_baseline_expand_more_24);
            } else {

                TransitionManager.beginDelayedTransition(objHomeCard,
                        new AutoTransition());
                objHomeTable.setVisibility(View.VISIBLE);
                objHomeExpandButton.setImageResource(R.drawable.ic_baseline_expand_less_24);
            }

            objHomeExpandButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (objHomeTable.getVisibility() == View.VISIBLE) {

                        TransitionManager.beginDelayedTransition(objHomeCard,
                                new AutoTransition());
                        objHomeTable.setVisibility(View.GONE);
                        objHomeExpandButton.setImageResource(R.drawable.ic_baseline_expand_more_24);
                    } else {

                        TransitionManager.beginDelayedTransition(objHomeCard,
                                new AutoTransition());
                        objHomeTable.setVisibility(View.VISIBLE);
                        objHomeExpandButton.setImageResource(R.drawable.ic_baseline_expand_less_24);
                    }
                }
            });


        }

    }
}
