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
    MonthAlarm monthlyAlarm = new MonthAlarm();

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            //
            try {
                JSONObject slaapData = new GetDataATask(context).execute(new Date(),new Date(),"sleep/minutesAsleep").get();

               // int minutenGeslapen = slaapData.getJSONArray();

                System.out.println("test");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            //

            dailyAlarm.SetAlarm(context);
            weeklyAlarm.SetAlarm(context);
            monthlyAlarm.SetAlarm(context);
        }
    }
}
