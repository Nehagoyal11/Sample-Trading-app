package com.example.wazirx2;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;

public class APP extends Application {
    public static final String CHANNEL_1_ID="channel1";



    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();

    }

    private void createNotificationChannels() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel1=new NotificationChannel(
                    CHANNEL_1_ID,
                    "channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("this is channel 1");
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);


        }
  }
}


