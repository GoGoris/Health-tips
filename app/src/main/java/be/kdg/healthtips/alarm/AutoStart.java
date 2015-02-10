package be.kdg.healthtips.alarm;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

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
            dailyAlarm.SetAlarm(context);
            weeklyAlarm.setAlarm(context);
        }
    }
}
