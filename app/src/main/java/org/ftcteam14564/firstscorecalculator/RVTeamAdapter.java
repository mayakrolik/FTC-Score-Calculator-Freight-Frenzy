package org.ftcteam14564.firstscorecalculator;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.ftcteam14564.firstscorecalculator.data.model.UserProfile;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.List;

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
        holder.UserEmail.setText(StringUtils.SafeUserEmailAddress(String.valueOf(users.get(position).EmailAddress)));
        holder.LoginCount.setText(String.valueOf(users.get(position).LoginCount));
        Timestamp tstamp = users.get(position).LastLogin;
        if (tstamp != null) {
            holder.LastLogin.setText(sfd.format(tstamp.toDate()));
        } else {
            holder.LastLogin.setText("Unknown");
        }
        String strProfilePicUrl = users.get(position).ProfilePhotoUrl;
        if (strProfilePicUrl != null) {
            //holder.ProfilePic.setImageURI(Uri.parse(strProfilePicUrl));
            Glide.with(holder.itemView.getContext())
                    .load(Uri.parse(strProfilePicUrl))
                    .into(holder.ProfilePic);
        } else {
            holder.ProfilePic.setImageResource(R.drawable.ic_baseline_person_24);
        }
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
        ImageView ProfilePic;


        UserViewHolder(final View itemView) {
            super(itemView);
            UserInitials = (TextView) itemView.findViewById(R.id.member_name);
            UserEmail = (TextView) itemView.findViewById(R.id.user_email);
            LoginCount = (TextView) itemView.findViewById(R.id.login_count);
            LastLogin = (TextView) itemView.findViewById(R.id.last_login);
            ProfilePic = (ImageView) itemView.findViewById(R.id.profilePicImageView);
        }
    }

}
