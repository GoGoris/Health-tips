package be.kdg.healthtips.alarm;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import org.json.JSONObject;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import be.kdg.healthtips.activity.HomeActivity;
import be.kdg.healthtips.notifications.NotificationThrower;
import be.kdg.healthtips.task.GetDataATask;

/**
 * Created by school on 3/2/2015.
 */
public class AutoStart extends BroadcastReceiver
{
    DayAlarm dailyAlarm = new DayAlarm();
    WeekAlarm weeklyAlarm = new WeekAlarm();

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            /*
            dailyAlarm.SetAlarmIn2Minutes(context);
            weeklyAlarm.SetAlarmIn2Minutes(context);
            */
        }
    }
}
