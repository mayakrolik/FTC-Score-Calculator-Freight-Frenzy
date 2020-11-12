package com.example.ftcscorecalculatorbeta;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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

        holder.teamName.setText(String.valueOf(scores.get(position).getSafeUserEmailAddress()));
        holder.totalScore.setText(String.valueOf(scores.get(position).TotalScore));
        holder.autonomousScore.setText(String.valueOf(scores.get(position).AutScore));
        holder.tellyopScore.setText(String.valueOf(scores.get(position).TelScore));
        holder.endScore.setText(String.valueOf(scores.get(position).EndScore));
        holder.autTopGoals.setText(String.valueOf(scores.get(position).AutLowGoals));
        holder.autMidGoals.setText(String.valueOf(scores.get(position).AutMiddleGoals));
        holder.autLowGoals.setText(String.valueOf(scores.get(position).AutTopGoals));
        holder.autPower1.setText(String.valueOf(scores.get(position).AutPowerShot1));
        holder.autPower2.setText(Boolean.toString(scores.get(position).AutPowerShot2));
        holder.autPower3.setText(Boolean.toString(scores.get(position).AutPowerShot3));
        holder.stoppedOnLine.setText(Boolean.toString(scores.get(position).AutStoppedOnLine));
        holder.wobbleGoalsDeposited.setText(Boolean.toString(scores.get(position).AutWobbleGoalDeposited));
        holder.telTopGoals.setText(String.valueOf(scores.get(position).TelHighGoals));
        holder.telMidGoals.setText(String.valueOf(scores.get(position).TelMiddleGoals));
        holder.telLowGoals.setText(String.valueOf(scores.get(position).TelLowGoals));
        holder.endTopGoals.setText(String.valueOf(scores.get(position).EndTopGoals));
        holder.endMidGoals.setText(String.valueOf(scores.get(position).EndMiddleGoals));
        holder.endLowGoals.setText(String.valueOf(scores.get(position).EndLowGoals));
//        holder.endPower1.setText(String.valueOf(scores.get(position).EndPowerShot1));
   //     holder.endPower2.setText(Boolean.toString(scores.get(position).EndPowerShot2));
   //     holder.endPower3.setText(Boolean.toString(scores.get(position).EndPowerShot3));
        holder.endWobbleInDropZone.setText(Boolean.toString(scores.get(position).EndWobbleInDropZone));
        //holder.endStartLinePark.setText(Boolean.toString(scores.get(position).EndStartLinePark));
        holder.endWobbleGoals.setText(String.valueOf(scores.get(position).EndWobbleGoals));

        if (scores.get(position).AutStoppedOnLine){
            holder.lineStop.setImageResource(R.drawable.ic_baseline_toggle_on_24);
        } else {
            holder.lineStop.setImageResource(R.drawable.ic_baseline_toggle_off_24);
        }

        if (scores.get(position).AutWobbleGoalDeposited){
            holder.goalDepositAuto.setImageResource(R.drawable.ic_baseline_toggle_on_24);
        } else {
            holder.goalDepositAuto.setImageResource(R.drawable.ic_baseline_toggle_off_24);
        }


        if (scores.get(position).AutPowerShot1){
            holder.objaut1.setImageResource(R.drawable.ic_baseline_check_box_24);
        } else {
            holder.objaut1.setImageResource(R.drawable.ic_baseline_check_box_outline_blank_24);
        }

        if (scores.get(position).AutPowerShot2){
            holder.objaut2.setImageResource(R.drawable.ic_baseline_check_box_24);
        } else {
            holder.objaut2.setImageResource(R.drawable.ic_baseline_check_box_outline_blank_24);
        }

        if (scores.get(position).AutPowerShot3){
            holder.objaut3.setImageResource(R.drawable.ic_baseline_check_box_24);
        } else {
            holder.objaut3.setImageResource(R.drawable.ic_baseline_check_box_outline_blank_24);
        }

        if (scores.get(position).EndStartLinePark) {
            holder.objendStartLinePark.setImageResource(R.drawable.ic_baseline_toggle_on_24);
        } else {
            holder.objendStartLinePark.setImageResource(R.drawable.ic_baseline_toggle_off_24);
        }

        if (scores.get(position).EndWobbleInDropZone){
            holder.objendWobbleInDropZone.setImageResource(R.drawable.ic_baseline_toggle_on_24);
        } else {
            holder.objendWobbleInDropZone.setImageResource(R.drawable.ic_baseline_toggle_off_24);
        }

        if (scores.get(position).AutWobbleGoalDeposited){
            holder.goalDepositAuto.setImageResource(R.drawable.ic_baseline_toggle_on_24);
        } else {
            holder.goalDepositAuto.setImageResource(R.drawable.ic_baseline_toggle_off_24);
        }

        if (scores.get(position).EndPowerShot1){
            holder.objend1.setImageResource(R.drawable.ic_baseline_check_box_24);
        } else {
            holder.objend1.setImageResource(R.drawable.ic_baseline_check_box_outline_blank_24);
        }

        if (scores.get(position).EndPowerShot2){
            holder.objend2.setImageResource(R.drawable.ic_baseline_check_box_24);
        } else {
            holder.objend2.setImageResource(R.drawable.ic_baseline_check_box_outline_blank_24);
        }

        if (scores.get(position).EndPowerShot3){
            holder.objend3.setImageResource(R.drawable.ic_baseline_check_box_24);
        } else {
            holder.objend3.setImageResource(R.drawable.ic_baseline_check_box_outline_blank_24);
        }

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
        TextView autTopGoals;
        TextView autMidGoals;
        TextView autLowGoals;
        TextView autPower1;
        TextView autPower2;
        TextView autPower3;
        TextView stoppedOnLine;
        TextView wobbleGoalsDeposited;
        // ImageView personPhoto;
        ImageButton objHomeExpandButton;
        TableLayout objHomeTable;
        CardView objHomeCard;
        ImageButton objKudosButton;
        ImageView objaut1;
        ImageView objaut2;
        ImageView objaut3;
        ImageView lineStop;
        ImageView goalDepositAuto;
        TextView telTopGoals;
        TextView telMidGoals;
        TextView telLowGoals;
        TextView endTopGoals;
        TextView endMidGoals;
        TextView endLowGoals;
        TextView endWobbleGoals;
        TextView endPower1;
        TextView endPower2;
        TextView endPower3;
        TextView endStartLinePark;
        TextView endWobbleInDropZone;
        ImageView objend1;
        ImageView objend2;
        ImageView objend3;
        ImageView objendStartLinePark;
        ImageView objendWobbleInDropZone;

        ScoreViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            teamName = (TextView) itemView.findViewById(R.id.team_name);
            totalScore = (TextView) itemView.findViewById(R.id.total_score);
            autonomousScore = (TextView) itemView.findViewById(R.id.autonomous_score);
            tellyopScore = (TextView) itemView.findViewById(R.id.tellyop_score);
            endScore = (TextView) itemView.findViewById(R.id.end_score);
            objKudosButton = (ImageButton) itemView.findViewById(R.id.kudos);
            autTopGoals = (TextView) itemView.findViewById(R.id.aut_top_goals);
            autMidGoals = (TextView) itemView.findViewById(R.id.aut_mid_goals);
            autLowGoals = (TextView) itemView.findViewById(R.id.aut_low_goals);
            autPower1 = (TextView) itemView.findViewById(R.id.aut_power_shot_1);
            autPower2 = (TextView) itemView.findViewById(R.id.aut_power_shot_2);
            autPower3 = (TextView) itemView.findViewById(R.id.aut_power_shot_3);
            stoppedOnLine = (TextView) itemView.findViewById(R.id.stopped_on_line);
            wobbleGoalsDeposited = (TextView) itemView.findViewById(R.id.wobble_goal_deposited);
            objaut1 = (ImageView) itemView.findViewById(R.id.aut_power_shot_1_image);
            objaut2 = (ImageView) itemView.findViewById(R.id.aut_power_shot_2_image);
            objaut3 = (ImageView) itemView.findViewById(R.id.aut_power_shot_3_image);
            lineStop = (ImageView) itemView.findViewById(R.id.stopped_on_line_image);
            goalDepositAuto = (ImageView) itemView.findViewById(R.id.wobble_goal_deposited_image);
            telTopGoals = (TextView) itemView.findViewById(R.id.tel_top_goals);
            telMidGoals = (TextView) itemView.findViewById(R.id.tel_mid_goals);
            telLowGoals = (TextView) itemView.findViewById(R.id.tel_low_goals);
            endTopGoals = (TextView) itemView.findViewById(R.id.end_top_goals);
            endMidGoals = (TextView) itemView.findViewById(R.id.end_mid_goals);
            endLowGoals = (TextView) itemView.findViewById(R.id.end_low_goals);
            endWobbleGoals = (TextView) itemView.findViewById(R.id.wobble_goal_rings);
            //endPower1 = (TextView) itemView.findViewById(R.id.end_power_shot_1);
            //endPower2 = (TextView) itemView.findViewById(R.id.end_power_shot_2);
            //endPower3 = (TextView) itemView.findViewById(R.id.end_power_shot_3);
            endStartLinePark = (TextView) itemView.findViewById(R.id.parked_on_line);
            endWobbleInDropZone = (TextView) itemView.findViewById(R.id.wobble_goal_deposited_end);
            objend1 = (ImageView) itemView.findViewById(R.id.end_power_shot_1_image);
            objend2 = (ImageView) itemView.findViewById(R.id.end_power_shot_2_image);
            objend3 = (ImageView) itemView.findViewById(R.id.end_power_shot_3_image);
            objendStartLinePark = (ImageView) itemView.findViewById(R.id.parked_on_line_image);
            objendWobbleInDropZone = (ImageView) itemView.findViewById(R.id.wobble_goal_deposited_end_image);


            objKudosButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    objKudosButton.setImageResource(R.drawable.ic_baseline_thumb_up_blue_24);

                }
            });
            //personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
            doOnCreate(itemView);


            
        }



        private void doOnCreate(View view) {
            objHomeExpandButton = view.findViewById(R.id.home_expand_card);
            objHomeTable = view.findViewById(R.id.home_table_secondary);
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