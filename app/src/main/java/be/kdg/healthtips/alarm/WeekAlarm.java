package be.kdg.healthtips.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import be.kdg.healthtips.activity.HomeActivity;
import be.kdg.healthtips.notifications.NotificationThrower;
import be.kdg.healthtips.notifications.TipManager;
import be.kdg.healthtips.task.GetDailyGoalATask;
import be.kdg.healthtips.task.GetPeriodStepsATask;
import be.kdg.healthtips.task.GetWeeklyGoalATask;

/**
 * Created by school on 4/2/2015.
 */
public class WeekAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "week Alarm", Toast.LENGTH_LONG).show();
        checkWeekSteps(context);
    }

    public void checkWeekSteps(Context context){
        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.WEEK_OF_YEAR,-2);

            JSONObject stepsLastTwoWeeks = new GetPeriodStepsATask(context).execute(cal.getTime(),new Date()).get();
            JSONObject dailyGoals = new GetDailyGoalATask(context).execute().get();
            JSONObject weeklyGoals = new GetWeeklyGoalATask(context).execute().get();

            int dailyStepGoal = dailyGoals.getJSONObject("goals").getInt("steps");
            int weeklyStepGoal = weeklyGoals.getJSONObject("goals").getInt("steps");

            JSONArray allSteps = stepsLastTwoWeeks.getJSONArray("activities-log-steps");

            int currentWeekTotalSteps = 0;
            int lastWeekTotalSteps = 0;

            int nrOfTimesMetDayGoal = 0;

            for (int i = 0; i < 6; i++) {
                lastWeekTotalSteps += allSteps.getJSONObject(i).getInt("value");
            }

            for (int i = 6; i < 13; i++){
                currentWeekTotalSteps += allSteps.getJSONObject(i).getInt("value");
                if(allSteps.getJSONObject(i).getInt("value") >= dailyStepGoal){
                    nrOfTimesMetDayGoal++;
                }
            }


            if(nrOfTimesMetDayGoal == 7){
                NotificationThrower.throwNotification(context, NotificationThrower.IconType.T_STEPS, "Gefeliciteerd", "Je hebt deze week elke dag je daily goal behaald! gefeliciteerd!", HomeActivity.class, 0);
            }

            if(nrOfTimesMetDayGoal < 4){
                TipManager.throwRandomStepTip("Je hebt deze week je daily goal maar " + nrOfTimesMetDayGoal + " keer behaald", context);
            }

            if(currentWeekTotalSteps > weeklyStepGoal){
                NotificationThrower.throwNotification(context, NotificationThrower.IconType.T_STEPS,"Gefeliciteerd","Je hebt deze week " + Math.round((double)currentWeekTotalSteps / (double)weeklyStepGoal * 100) + "% van je weekly goal behaald",HomeActivity.class,0);
            }

            if(currentWeekTotalSteps > lastWeekTotalSteps * 1.20){
                NotificationThrower.throwNotification(context, NotificationThrower.IconType.T_STEPS,"Gefeliciteerd","Je hebt deze week " + Math.round(((double)currentWeekTotalSteps / (double)lastWeekTotalSteps * 100 - 100)) + "% meer steps gehaald dan vorige week",HomeActivity.class,0);
            }

            if(currentWeekTotalSteps < lastWeekTotalSteps * 0.8){
                TipManager.throwRandomStepTip("Je hebt deze week " + Math.round((100 - ((double)currentWeekTotalSteps / (double)lastWeekTotalSteps * 100))) + "% minder steps gehaald dan vorige week",context);
            }

            if(currentWeekTotalSteps < weeklyStepGoal){
                if(currentWeekTotalSteps < weeklyStepGoal * 0.8){
                    TipManager.throwRandomRunningTip("Je hebt deze week slechts " + Math.round(((double)currentWeekTotalSteps / (double)weeklyStepGoal * 100)) + "% van je weekly goal behaald",context);
                }else {
                    TipManager.throwRandomStepTip("Je hebt deze week slechts " + Math.round(((double)currentWeekTotalSteps / (double)weeklyStepGoal * 100)) + "% van je weekly goal behaald", context);
                }
            }

            System.out.println("stop");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void SetAlarm(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, WeekAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.DAY_OF_WEEK,calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
        calendar.set(Calendar.HOUR_OF_DAY,18);
        calendar.set(Calendar.MINUTE,0);

        am.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY * 7, pi);
    }
}
