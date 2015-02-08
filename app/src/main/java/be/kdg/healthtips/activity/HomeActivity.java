package be.kdg.healthtips.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import be.kdg.healthtips.R;
import be.kdg.healthtips.auth.FitbitTokenManager;
import be.kdg.healthtips.notifications.SpecificNotificationThrower;
import be.kdg.healthtips.task.GetPeriodStepsATask;
import be.kdg.healthtips.task.GetWeeklyGoalATask;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;
        FitbitTokenManager tokenManager = FitbitTokenManager.getInstance(context);

        if (tokenManager.getFitBitAccesToken().isEmpty() || tokenManager.getFitBitAccesTokenSecret().isEmpty()) {
            Intent intent = new Intent(this, LoginActivity.class);
            this.startActivity(intent);
        }

        /*
        CharSequence text = "accesstoken: " + tokenManager.getFitBitAccesToken() + "\naccesstoken secret: " + tokenManager.getFitBitAccesTokenSecret();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
        */

        setContentView(R.layout.activity_home_joni);

        ImageButton btnSteps = (ImageButton) findViewById(R.id.lopenButton);
        btnSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StepsActivity.class);
                context.startActivity(intent);
            }
        });

        ImageButton btnSlapen = (ImageButton) findViewById(R.id.slapenButton);
        btnSlapen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SlapenActivity.class);
                context.startActivity(intent);
            }
        });

        ImageButton btnEten = (ImageButton) findViewById(R.id.etenButton);
        btnEten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EtenActivity.class);
                context.startActivity(intent);
            }
        });


        setTip();
        setMotivationMessage();
        setGoal();

        /*
        DayAlarm dailyAlarm = new DayAlarm();
        WeekAlarm weeklyAlarm = new WeekAlarm();

        dailyAlarm.SetAlarmIn2Minutes(context);
        weeklyAlarm.SetAlarmIn2Minutes(context);
*/
        SpecificNotificationThrower.throwBadFoodHabit(this);
        SpecificNotificationThrower.throwYouHaveEatenBeforeSporting(this);
    }

    private void setTip() {
        TextView tipTitle = (TextView) findViewById(R.id.tipTitle);
        tipTitle.setText("placeholder tip title");
    }

    private void setMotivationMessage() {
        TextView motivationText = (TextView) findViewById(R.id.motivationText);
        motivationText.setText("placeholder motivatie text");
    }

    private void setGoal() {
        Integer progressToReturn = 0;
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, cal.getActualMinimum(Calendar.DAY_OF_WEEK));

            JSONObject stepsThisWeek = new GetPeriodStepsATask(this).execute(cal.getTime(), new Date()).get();
            JSONObject weeklyGoals = new GetWeeklyGoalATask(this).execute().get();
            int weeklyStepGoal = weeklyGoals.getJSONObject("goals").getInt("steps");

            JSONArray allSteps = stepsThisWeek.getJSONArray("activities-log-steps");
            int currentWeekTotalSteps = 0;
            for (int i = 0; i < allSteps.length(); i++) {
                currentWeekTotalSteps += allSteps.getJSONObject(i).getInt("value");
            }

            progressToReturn = (int) Math.round((double) currentWeekTotalSteps / (double) weeklyStepGoal * 100);
            if (progressToReturn > 100) {
                progressToReturn = 100;
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(progressToReturn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
}
