package com.capstone.aadityagandhi.bachaome.Utils;

/**
 * Created by aaditya.gandhi on 4/5/16.
 */
import com.capstone.aadityagandhi.bachaome.MapsActivity;
import com.capstone.aadityagandhi.bachaome.R;
import com.google.android.gms.gcm.GcmListenerService;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Policy;

public class GcmMessageHandler extends GcmListenerService {
    public static final int MESSAGE_NOTIFICATION_ID = 435345;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        JSONObject jsonObject = new JSONObject();
        String finalMessage = "";
        String title="DISTRESS!! ACT NOW!";
        String message = data.getString("message");
        try {
            jsonObject = new JSONObject(message);
            finalMessage = jsonObject.has("device_id")?jsonObject.get("device_id").toString()+ " is in trouble":"";
        } catch (JSONException e) {
            e.printStackTrace();
        }

        createNotification(title, finalMessage,jsonObject);
    }

    // Creates notification based on title and body received
    private void createNotification(String title, String body, JSONObject jsonObject) {
        Context context = getBaseContext();

        Intent resultIntent = new Intent(context, MapsActivity.class);
        resultIntent.putExtra("json",jsonObject.toString());
        resultIntent.putExtra("notification",true);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );



        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title)
                .setContentText(body)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
    }

}