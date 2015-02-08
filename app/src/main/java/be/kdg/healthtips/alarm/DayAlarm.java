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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import be.kdg.healthtips.activity.HomeActivity;
import be.kdg.healthtips.notifications.NotificationThrower;
import be.kdg.healthtips.notifications.TipManager;
import be.kdg.healthtips.task.GetDaySleepATask;
import be.kdg.healthtips.task.GetWeightATask;
import be.kdg.healthtips.task.GetWeightGoalATask;

public class DayAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Day Alarm", Toast.LENGTH_LONG).show();
        checkDaySleep(context);
        checkDayWeight(context);
    }

    private void checkDaySleep(Context context) {
        try {
            JSONObject sleepData = new GetDaySleepATask(context).execute().get();
            JSONArray sleepArray = sleepData.getJSONArray("sleep");
            JSONObject mainSleep = null;
            for (int i = 0; i < sleepArray.length(); i++) {
                if (sleepArray.getJSONObject(i).getBoolean("isMainSleep")) {
                    mainSleep = sleepArray.getJSONObject(i);
                }
            }
            if (mainSleep == null) {
                System.err.println("Can't get data, maybe you have no temboo credit anymore for this month?");
            } else {
                int totalMinutesAsleep = mainSleep.getInt("minutesAsleep");
                int totalMinutesToFallAsleep = mainSleep.getInt("minutesToFallAsleep");
                int totalTimesWakenUp = mainSleep.getInt("awakeningsCount");
                int sleepEfficiency = mainSleep.getInt("efficiency");

                if (totalTimesWakenUp > 10) {
                    TipManager.throwRandomSleepTip("Je bent vorige nacht " + totalTimesWakenUp + " keer wakker geworden", context);
                }

                if (totalMinutesToFallAsleep > 20) {
                    TipManager.throwRandomFallingASleepTip("Het duurde vorige nacht " + totalMinutesToFallAsleep + " minuten om in slaap te vallen", context);
                }

                if (totalMinutesAsleep / 60 < 6) {
                    TipManager.throwRandomSleepTip("Je hebt vorige nacht kort geslapen", context);
                }

                if (sleepEfficiency < 90) {
                    TipManager.throwRandomSleepTip("U slaap efficiency vorige nacht was niet zo goed", context);
                }
            }
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void checkDayWeight(Context context) {
        try {
            JSONObject weight = new GetWeightATask(context).execute(new Date()).get();
            JSONObject weightGoal = new GetWeightGoalATask(context).execute().get();


            boolean weightChange = false;
            double huidigGewicht = 0;
            double vorigeGewicht;

            double gewichtGoal;

            double bmi = 0;
            double vorigeBmi;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = sdf.format(new Date());

            JSONArray allWeights = weight.getJSONArray("weight");
            for (int i = 0; i < allWeights.length(); i++) {
                if (currentDate.equals(allWeights.getJSONObject(i).getString("date"))) {
                    weightChange = true;
                    huidigGewicht = allWeights.getJSONObject(i).getDouble("weight");
                    bmi = allWeights.getJSONObject(i).getDouble("bmi");
                }
            }

            if (weightChange) {
                try {
                    gewichtGoal = weightGoal.getJSONObject("goal").getDouble("weight");


                    vorigeGewicht = allWeights.getJSONObject(allWeights.length() - 2).getDouble("weight");
                    vorigeBmi = allWeights.getJSONObject(allWeights.length() - 2).getDouble("bmi");

                    if (huidigGewicht < vorigeGewicht) {
                        NotificationThrower.throwNotification(context, NotificationThrower.IconType.F_WEIGHT, "Gefeliciteerd", "U bent afgevallen", HomeActivity.class, 0);
                    }

                    if (huidigGewicht <= gewichtGoal && vorigeGewicht > gewichtGoal) {
                        NotificationThrower.throwNotification(context, NotificationThrower.IconType.F_WEIGHT, "Gefeliciteerd", "U heeft uw gewicht goal behaald", HomeActivity.class, 0);
                    }

                    if (huidigGewicht <= gewichtGoal + 3 && vorigeGewicht > gewichtGoal && huidigGewicht > gewichtGoal) {
                        TipManager.throwRandomGewichtTip("U heeft uw goal bijna gehaald nog " + Math.abs(huidigGewicht - gewichtGoal) + " kg te gaan", context);
                    }

                    if (vorigeBmi >= 25 && bmi < 25) {
                        NotificationThrower.throwNotification(context, NotificationThrower.IconType.F_WEIGHT, "Gefeliciteerd", "U heeft een normale bmi behaald", HomeActivity.class, 0);
                    }

                    if (vorigeGewicht < gewichtGoal + 3 && huidigGewicht > gewichtGoal + 3) {
                        TipManager.throwRandomGewichtTip("U bent buiten uw gewenst gewicht beland", context);
                    }

                    if (huidigGewicht > vorigeGewicht) {
                        TipManager.throwRandomGewichtTip("Ai, je bent bijgekomen. Probeer af te vallen", context);
                    }

                    if (vorigeBmi <= 25 && bmi > 25) {
                        TipManager.throwRandomGewichtTip("Je BMI zit niet meer goed.", context);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
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
        calendar.set(Calendar.MINUTE, 0);

        am.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi);
    }

    public void SetAlarmIn2Minutes(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, DayAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());


        calendar.add(Calendar.SECOND, 10);

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
