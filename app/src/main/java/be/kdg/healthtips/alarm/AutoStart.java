package be.kdg.healthtips.alarm;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import be.kdg.healthtips.R;
import be.kdg.healthtips.activity.HomeActivity;
import be.kdg.healthtips.notifications.NotificationThrower;

/**
 * Created by school on 3/2/2015.
 */
public class AutoStart extends BroadcastReceiver
{
    DayAlarm dailyAlarm = new DayAlarm();
    WeekAlarm weeklyAlarm = new WeekAlarm();
    TwoWeekAlarm biWeeklyAlarm = new TwoWeekAlarm();
    MonthAlarm monthlyAlarm = new MonthAlarm();

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            NotificationThrower.throwNotification(context, NotificationThrower.IconType.F_STEPS,"title","text",HomeActivity.class);

            dailyAlarm.SetAlarm(context);
            weeklyAlarm.SetAlarm(context);
            biWeeklyAlarm.SetAlarm(context);
            monthlyAlarm.SetAlarm(context);
        }
    }
}
