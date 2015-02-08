package be.kdg.healthtips.notifications;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Random;

import be.kdg.healthtips.R;

public class NotificationThrower {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void throwNotification(Context context, IconType iconType, String title, String text, Class activity, int tipNr) {
        Notification.Builder mBuilder =
                new Notification.Builder(context)
                        .setSmallIcon(iconType.getValue())
                        .setContentTitle(title)
                        .setContentText(text);
// Creates an explicit intent for an Activity in your app

        Intent resultIntent = new Intent(context, activity);

        resultIntent.putExtra("tipnr", tipNr);


        Random r = new Random();

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
                        r.nextInt(100000),
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.

        int randomnr = r.nextInt(10000);
        mBuilder.setAutoCancel(true);
        mNotificationManager.notify(randomnr, mBuilder.build());
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void throwSpecificTip(Context context, IconType iconType, String title, String text, Class activity, String tipTitle, String tipText) {
        Notification.Builder mBuilder =
                new Notification.Builder(context)
                        .setSmallIcon(iconType.getValue())
                        .setContentTitle(title)
                        .setContentText(text);
// Creates an explicit intent for an Activity in your app

        Intent resultIntent = new Intent(context, activity);

        resultIntent.putExtra("titel", tipTitle);
        resultIntent.putExtra("beschrijving", tipText);


        Random r = new Random();

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
                        r.nextInt(100000),
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.

        int randomnr = r.nextInt(10000);
        mBuilder.setAutoCancel(true);
        mNotificationManager.notify(randomnr, mBuilder.build());
    }

    public enum IconType {
        T_FOOD(R.drawable.foodicon), T_SLEEP(R.drawable.sleepicon), T_STEPS(R.drawable.stepsicon), T_CALORIES(R.drawable.weighticon), T_WEIGHT(R.drawable.weighticon), T_RUNNING(R.drawable.runningicon), F_FOOD(R.drawable.foodiconf), F_WEIGHT(R.drawable.weighticonf), F_STEPS(R.drawable.stepsiconf);

        private final int icon;

        IconType(int icon) {
            this.icon = icon;
        }

        public int getValue() {
            return icon;
        }
    }
}
