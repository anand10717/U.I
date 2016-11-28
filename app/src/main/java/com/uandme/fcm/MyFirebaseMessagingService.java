package com.uandme.fcm;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.uandme.MainActivity;
import com.uandme.R;
import com.uandme.dbhandle.DBHandler;
import com.uandme.dbhandle.Messages;
import com.uandme.introduction.Intro;
import com.uandme.singleton.AppLocalData;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.uandme.R.drawable.pika;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    public MyFirebaseMessagingService() {
        }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

      //  Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        String frdname =AppLocalData.getInstance(getApplicationContext()).getData(AppLocalData.FRD_NAME);

        if(frdname != null && remoteMessage.getData().get("sender").toString().equals(frdname)) {
            update(remoteMessage.getData());
            if (!isActive())
                notification(remoteMessage.getData().get("msg").toString());
        }
    }

    private void notification(String s) {



        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.welcome)
                        .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.pika))
                        .setContentTitle("Meaage From "+AppLocalData.getInstance(getApplicationContext()).getData(AppLocalData.FRD_NAME))
                        .setContentText(s);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }

    public boolean isActive(){
    return AppLocalData.getInstance(getApplicationContext()).alive ;
    }
/*

    private boolean getActivePackagesCompat() {

        final List<ActivityManager.RunningTaskInfo> taskInfo = ActivityManager.getRunningTasks(1);
        final ComponentName componentName = taskInfo.get(0).topActivity;
        final String[] activePackages = new String[1];
        Log.d("Is Alive",componentName.getPackageName());
        return componentName.getPackageName().equals("com.uandme");
    }
*/

 /*   private boolean getActivePackages() {
        Log.d("active #######","check");
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService( Context.ACTIVITY_SERVICE );
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for(ActivityManager.RunningAppProcessInfo appProcess : appProcesses){
            if(appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
                Log.i("Foreground App", appProcess.processName);
            }
        }
        return true;
    }
*/

    private void update(final Map<String, String> data) {
        Log.d(TAG,"updating...");
        final MainActivity mainActivity = MainActivity.getInstance();

        DBHandler dbHandler = new DBHandler(getApplicationContext());

        dbHandler.addMessages(new Messages((data.get("id")),data.get("timestamp"),data.get("sender"),data.get("msg"),""));

        final int count = dbHandler.getMessagesCount();
        if(mainActivity != null) {
            mainActivity.msgList.add(new Messages((data.get("id")), data.get("timestamp"), data.get("sender"), data.get("msg"), ""));
            mainActivity.runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    mainActivity.mAdapter.notifyDataSetChanged();
                    mainActivity.recyclerView.smoothScrollToPosition(mainActivity.msgList.size());
                }
            });
        }
    }

}




