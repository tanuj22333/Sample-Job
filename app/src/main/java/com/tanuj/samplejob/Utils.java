package com.tanuj.samplejob;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.tanuj.samplejob.activities.FirstActivity;
import com.tanuj.samplejob.activities.SecondActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Utils {

    public static void showNotification(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(android.R.drawable.btn_star);
        builder.setContentTitle(context.getString(R.string.planned_work));
        builder.setContentText(context.getString(R.string.do_planned_work));
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));

        Intent intent = new Intent(context, FirstActivity.class);
        intent.putExtra(FirstActivity.COMING_FROM_NOTIFICATION, true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 113, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        builder.setFullScreenIntent(pendingIntent, true);

        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        PreferenceProvider preferenceProvider = PreferenceProvider.getInstance();
        int notificationId = preferenceProvider.getNotificationId();
        manager.notify(notificationId, builder.build());
        preferenceProvider.saveNextNotificationId(notificationId+1);

    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int msgId) {
        Toast.makeText(context, context.getString(msgId), Toast.LENGTH_SHORT).show();
    }
}
