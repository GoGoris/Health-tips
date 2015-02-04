package be.kdg.healthtips.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by school on 3/2/2015.
 */
public class AutoStart extends BroadcastReceiver
{
    DayAlarm dailyAlarm = new DayAlarm();
    WeekAlarm weeklyAlarm = new WeekAlarm();
    TwoWeekAlarm biWeeklyAlarm = new TwoWeekAlarm();
    MonthAlarm monthlyAlarm = new MonthAlarm();

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            dailyAlarm.SetAlarm(context);
            weeklyAlarm.SetAlarm(context);
            biWeeklyAlarm.SetAlarm(context);
            monthlyAlarm.SetAlarm(context);
        }
    }
}
