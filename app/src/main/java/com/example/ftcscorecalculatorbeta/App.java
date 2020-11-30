package com.example.ftcscorecalculatorbeta;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import org.jetbrains.annotations.NotNull;

public class App extends Application {

    // We are publishing the app here:   https://ftc-calculator.web.app/ftc-calc-app.apk

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

    private void createNotificationChannels() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            NotificationChannel channel = getNotificationChannel("Kudos_Channel",  "Team Kudos Channel", "The default channel for new kudos notifications.");
            notificationManager.createNotificationChannel(channel);
            channel = getNotificationChannel("Score_Channel",  "Team Score Channel", "The default channel for new score notifications.");
            notificationManager.createNotificationChannel(channel);
            channel = getNotificationChannel("Team_Member_Channel",  "New Team Member Channel", "The default channel for new team member notifications.");
            notificationManager.createNotificationChannel(channel);
        }
    }

    @NotNull
    private NotificationChannel getNotificationChannel(String channel_id, CharSequence name, String description) {
        //String CHANNEL_ID = "Kudos_Channel";
        //CharSequence name = "Team Kudos Channel";
        //String description = "The default channel for new kudos notifications.";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(channel_id, name, importance);
        channel.setDescription(description);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        return channel;
    }


}
