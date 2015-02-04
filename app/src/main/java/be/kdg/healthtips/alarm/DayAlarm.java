package be.kdg.healthtips.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import be.kdg.healthtips.activity.SingleTipActivity;
import be.kdg.healthtips.notifications.NotificationThrower;
import be.kdg.healthtips.notifications.TipManager;
import be.kdg.healthtips.task.GetDataATask;
import be.kdg.healthtips.task.GetDaySleepATask;

/**
 * Created by school on 4/2/2015.
 */
public class DayAlarm extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Day Alarm", Toast.LENGTH_LONG).show();
        checkDaySleep(context);
    }

    private void checkDaySleep(Context context){
        try {
            JSONObject slaapData = new GetDaySleepATask(context).execute().get();
            JSONArray sleepArray = slaapData.getJSONArray("sleep");
            JSONObject mainSleep = null;
            for (int i = 0; i < sleepArray.length(); i++) {
                if (sleepArray.getJSONObject(i).getBoolean("isMainSleep")) {
                    mainSleep = sleepArray.getJSONObject(i);
                }
            }

            int totalMinutesAsleep = mainSleep.getInt("minutesAsleep");
            int totalMinutesToFallAsleep = mainSleep.getInt("minutesToFallAsleep");
            int totalTimesWakenUp = mainSleep.getInt("awakeningsCount");
            int sleepEfficiency = mainSleep.getInt("efficiency");

            if (totalTimesWakenUp > 10) {
                TipManager.throwRandomSleepTip("Je bent vorige nacht " + totalTimesWakenUp + " keer wakker geworden", context);
            }

            if (totalMinutesToFallAsleep > 20) {
                TipManager.throwRandomFallingASleepTip("Het duurde vorige nacht " + totalMinutesToFallAsleep + " om in slaap te vallen", context);
            }

            if (totalMinutesAsleep / 60 < 6) {
                NotificationThrower.throwNotification(context, NotificationThrower.IconType.T_SLEEP, "Kort geslapen", "Voldoende nachtrust is belangrijk", SingleTipActivity.class, 555001);
            }

            if (sleepEfficiency < 90) {
                TipManager.throwRandomSleepTip("U slaap efficiency vorige nacht was niet zo goed", context);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void SetAlarm(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, DayAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE,0);

        am.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi);
    }

    public void CancelAlarm(Context context) {
        Intent intent = new Intent(context, DayAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
