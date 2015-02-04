package be.kdg.healthtips.notifications;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import java.util.Random;

import be.kdg.healthtips.R;
import be.kdg.healthtips.activity.HomeActivity;

/**
 * Created by school on 4/2/2015.
 */
public class NotificationThrower {

    public enum IconType{
        //TODO: verschillende icons
        F_STEPS(R.drawable.ic_launcher), F_CALORIES(R.drawable.ic_launcher),F_WEIGHT(R.drawable.ic_launcher),T_FOOD(R.drawable.ic_launcher),T_SLEEP(R.drawable.ic_launcher),T_STEPS(R.drawable.ic_launcher),T_CALORIES(R.drawable.ic_launcher),T_WEIGHT(R.drawable.ic_launcher);
        private final int icon;
        IconType(int icon) { this.icon = icon; }
        public int getValue() { return icon; }
    };
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void throwNotification(Context context,IconType iconType, String title, String text, Class activity){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(iconType.getValue())
                        .setContentTitle(title)
                        .setContentText(text);
// Creates an explicit intent for an Activity in your app

        Intent resultIntent = new Intent(context, activity);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(activity);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        Random r = new Random();

        mBuilder.setAutoCancel(true);
        mNotificationManager.notify(r.nextInt(10000),mBuilder.build());
    }
}
