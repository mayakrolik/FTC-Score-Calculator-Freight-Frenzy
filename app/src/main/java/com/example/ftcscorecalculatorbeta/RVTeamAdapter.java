package com.example.ftcscorecalculatorbeta;

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
import com.example.ftcscorecalculatorbeta.data.model.UserProfile;
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
import com.google.firebase.firestore.auth.User;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class RVTeamAdapter extends RecyclerView.Adapter<RVTeamAdapter.UserViewHolder> {

    List<UserProfile> users;

    public RVTeamAdapter(List<UserProfile> users) {
        this.users = users;
    }

    final static String TAG = "RVTeamAdapter";

    @NonNull
    @Override
    public RVTeamAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.team_member, parent, false);
        RVTeamAdapter.UserViewHolder holder = new RVTeamAdapter.UserViewHolder(view);
        Log.d(TAG, "onCreateViewHolder");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        SimpleDateFormat sfd = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

        holder.UserInitials.setText(String.valueOf(users.get(position).DisplayName));
        holder.UserEmail.setText(String.valueOf(users.get(position).EmailAddress));
        holder.LoginCount.setText(String.valueOf(users.get(position).LoginCount));
        holder.LastLogin.setText(sfd.format(users.get(position).LastLogin.toDate()));
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount " + String.valueOf(users.size()));
        return users.size();

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView UserInitials;
        TextView UserEmail;
        TextView LoginCount;
        TextView LastLogin;


        UserViewHolder(final View itemView) {
            super(itemView);
            UserInitials = (TextView) itemView.findViewById(R.id.member_name);
            UserEmail = (TextView) itemView.findViewById(R.id.user_email);
            LoginCount = (TextView) itemView.findViewById(R.id.login_count);
            LastLogin = (TextView) itemView.findViewById(R.id.last_login);

        }
    }

}
