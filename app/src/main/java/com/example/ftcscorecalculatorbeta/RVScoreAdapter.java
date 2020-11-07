package com.example.ftcscorecalculatorbeta;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ftcscorecalculatorbeta.data.model.Score;

import java.util.List;
import android.util.Log;

public class RVScoreAdapter extends RecyclerView.Adapter<RVScoreAdapter.ScoreViewHolder> {

    List<Score> scores;

    public RVScoreAdapter(List<Score> scores){
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

        holder.teamName.setText(scores.get(position).TeamId);
        holder.autonomousScore.setText(String.valueOf(scores.get(position).AutScore));
        //holder.personPhoto.setImageResource(scores.get(position).photoId);
        Log.d(TAG, "onBindViewHolder");

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount " + String.valueOf( scores.size() ) );
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
        // ImageView personPhoto;

        ScoreViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            teamName = (TextView) itemView.findViewById(R.id.team_name);
            autonomousScore = (TextView) itemView.findViewById(R.id.autonomous_score);
            //personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }

    }

}
