package com.example.ftcscorecalculatorbeta;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ftcscorecalculatorbeta.data.model.Kudo;
import com.example.ftcscorecalculatorbeta.data.model.Score;
import com.example.ftcscorecalculatorbeta.data.model.Team;
import com.example.ftcscorecalculatorbeta.ui.calculator.CalculatorViewModel;
import com.example.ftcscorecalculatorbeta.ui.home.HomeViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.util.Log;
import android.widget.Toast;

public class RVScoreAdapter extends RecyclerView.Adapter<RVScoreAdapter.ScoreViewHolder> {

    public ImageButton Kudos;
    private Score myTotalScore;

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

        holder.teamName.setText(String.valueOf(scores.get(position).getSafeUserDisplayInitials()) + " (#" + String.valueOf(scores.get(position).TeamNumber) + " " + String.valueOf(scores.get(position).TeamNickName) + ")");
        holder.totalScore.setText(String.valueOf(scores.get(position).TotalScore));
        holder.autonomousScore.setText(String.valueOf(scores.get(position).AutScore));
        holder.tellyopScore.setText(String.valueOf(scores.get(position).TelScore));
        holder.endScore.setText(String.valueOf(scores.get(position).EndScore));
        holder.autTopGoals.setText(String.valueOf(scores.get(position).AutLowGoals));
        holder.autMidGoals.setText(String.valueOf(scores.get(position).AutMiddleGoals));
        holder.autLowGoals.setText(String.valueOf(scores.get(position).AutTopGoals));
        holder.stoppedOnLine.setText(Boolean.toString(scores.get(position).AutStoppedOnLine));
        holder.wobbleGoalsDeposited.setText(Boolean.toString(scores.get(position).AutWobbleGoalDeposited));
        holder.telTopGoals.setText(String.valueOf(scores.get(position).TelHighGoals));
        holder.telMidGoals.setText(String.valueOf(scores.get(position).TelMiddleGoals));
        holder.telLowGoals.setText(String.valueOf(scores.get(position).TelLowGoals));
        holder.penMin.setText(String.valueOf(scores.get(position).PenaltyMinor));
        holder.penMaj.setText(String.valueOf(scores.get(position).PenaltyMajor));
        holder.endTopGoals.setText(String.valueOf(scores.get(position).EndTopGoals));
        holder.endMidGoals.setText(String.valueOf(scores.get(position).EndMiddleGoals));
        holder.endLowGoals.setText(String.valueOf(scores.get(position).EndLowGoals));
        holder.endWobbleInDropZone.setText(Boolean.toString(scores.get(position).EndWobbleInDropZone));
        holder.endWobbleGoals.setText(String.valueOf(scores.get(position).EndWobbleGoals));
        holder.objScoreId.setText(String.valueOf(scores.get(position).DocumentId));
        holder.objYoutubeSaveLink.setText(String.valueOf(scores.get(position).YouTubeVideoId));


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

        MainActivity activity = (MainActivity) holder.itemView.getContext();

        if (scores.get(position).Kudos != null){
            holder.objKudoAmount.setText(String.valueOf(scores.get(position).Kudos.size()));
            for (Kudo kudo : scores.get(position).Kudos){
                if (kudo.UserUid.equals(activity.currentUser.getUid())) {
                    holder.objKudosButton.setImageResource(R.drawable.ic_baseline_thumb_up_blue_24);
                    int intCurrentKudos = Integer.parseInt(holder.objKudoAmount.getText().toString());
                    intCurrentKudos++;
                    holder.objKudoAmount.setText(String.valueOf(intCurrentKudos));
                    holder.objKudosButton.setEnabled(false);
                    break;
                }

            }
        }

        /*if(scores.get(position).YouTubeVideoId.isEmpty()){
            holder.objYouTubeVideoLaunch.setVisibility(View.GONE);
        }*/

        //holder.personPhoto.setImageResource(scores.get(position).photoId);
        Log.d(TAG, "onBindViewHolder");

    }

    public void clear() {
        scores.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Score> list) {
        scores.addAll(list);
        notifyDataSetChanged();
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
        TextView penMin;
        TextView penMaj;
        TextView endTopGoals;
        TextView endMidGoals;
        TextView endLowGoals;
        TextView endWobbleGoals;
        TextView endStartLinePark;
        TextView endWobbleInDropZone;
        ImageView objend1;
        ImageView objend2;
        ImageView objend3;
        ImageView objendStartLinePark;
        ImageView objendWobbleInDropZone;
        TextView objScoreId;
        TextView objKudoAmount;
        ImageButton objYouTubeVideoLaunch;
        TextView objYoutubeSaveLink;

        ScoreViewHolder(final View itemView) {
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
            penMin = (TextView) itemView.findViewById(R.id.penalty_minor);
            penMaj = (TextView) itemView.findViewById(R.id.penalty_major);
            endTopGoals = (TextView) itemView.findViewById(R.id.end_top_goals);
            endMidGoals = (TextView) itemView.findViewById(R.id.end_mid_goals);
            endLowGoals = (TextView) itemView.findViewById(R.id.end_low_goals);
            endWobbleGoals = (TextView) itemView.findViewById(R.id.wobble_goal_rings);
            endStartLinePark = (TextView) itemView.findViewById(R.id.parked_on_line);
            endWobbleInDropZone = (TextView) itemView.findViewById(R.id.wobble_goal_deposited_end);
            objend1 = (ImageView) itemView.findViewById(R.id.end_power_shot_1_image);
            objend2 = (ImageView) itemView.findViewById(R.id.end_power_shot_2_image);
            objend3 = (ImageView) itemView.findViewById(R.id.end_power_shot_3_image);
            objendStartLinePark = (ImageView) itemView.findViewById(R.id.parked_on_line_image);
            objendWobbleInDropZone = (ImageView) itemView.findViewById(R.id.wobble_goal_deposited_end_image);
            objScoreId = (TextView) itemView.findViewById(R.id.score_id);
            objKudoAmount = (TextView) itemView.findViewById(R.id.kudo_count);
            objYouTubeVideoLaunch = (ImageButton) itemView.findViewById(R.id.view_youtube_button);
            objYoutubeSaveLink = (TextView) itemView.findViewById(R.id.save_youtube_link);

            objYouTubeVideoLaunch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String videoId = objYoutubeSaveLink.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+videoId));
                    intent.putExtra("VIDEO_ID", videoId);
                    if (isIntentAvailable(itemView.getContext(), intent))
                    {
                        itemView.getContext().startActivity(intent);
                    } else {
                        Toast.makeText(itemView.getContext(), "YouTube player is not available.", Toast.LENGTH_LONG).show();
                    }
                }
                });

            objKudosButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    objKudosButton.setImageResource(R.drawable.ic_baseline_thumb_up_blue_24);
                    objKudosButton.setEnabled(false);
                    final FirebaseFirestore db = FirebaseFirestore.getInstance();
                    final String strDocId = objScoreId.getText().toString();
                    Log.d(TAG, "About to load Score " + strDocId);

                    DocumentReference docRef = db.collection("Scores").document(strDocId);
                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Score score = documentSnapshot.toObject(Score.class);
                            // check if score already has kudos from current user


                            MainActivity activity = (MainActivity) itemView.getContext();
                            Kudo kudo = new Kudo();
                            kudo.TeamNickName = activity.getMyTeam().NickName;
                            kudo.TeamNumber = activity.getMyTeam().TeamNumber;
                            kudo.UserDisplayName = activity.currentUser.getDisplayName();
                            kudo.UserEmailAddress = activity.currentUser.getEmail();
                            kudo.UserUid = activity.currentUser.getUid();
                            kudo.CreatedTimestamp = Timestamp.now();

                            if (score.Kudos == null)
                            {
                                score.Kudos = new LinkedList<Kudo>();
                            }
                            score.Kudos.add(kudo);

                            // save the score
                            db.collection("Scores")
                                    .document(strDocId)
                                    .set(score)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void avoid) {
                                            Log.d(TAG, "Score Document updated.");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error updating score document", e);
                                        }
                                    });

                        }
                    });

                }
            });
            doOnCreate(itemView);

        }


        private static boolean isIntentAvailable(Context ctx, Intent intent) {
            final PackageManager mgr = ctx.getPackageManager();
            List<ResolveInfo> list =
                    mgr.queryIntentActivities(intent,
                            PackageManager.MATCH_DEFAULT_ONLY);
            return list.size() > 0;
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
                        if (objYouTubeVideoLaunch.getVisibility() == View.VISIBLE){
                            objYouTubeVideoLaunch.setVisibility(View.GONE);
                        }
                    } else {
                        TransitionManager.beginDelayedTransition(objHomeCard,
                                new AutoTransition());
                        objHomeTable.setVisibility(View.VISIBLE);
                        objHomeExpandButton.setImageResource(R.drawable.ic_baseline_expand_less_24);
                        if ((objYoutubeSaveLink.getText().toString().equals("null")) || (objYoutubeSaveLink.getText().toString().equals("")) || (objYoutubeSaveLink.getText().toString().length() < 7) ) {
                            objYouTubeVideoLaunch.setVisibility(View.GONE);
                        } else {
                            objYouTubeVideoLaunch.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        }
    }



}
