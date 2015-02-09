package be.kdg.healthtips.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.temboo.core.TembooException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import be.kdg.healthtips.R;
import be.kdg.healthtips.auth.FitbitTokenManager;
import be.kdg.healthtips.model.Tip;
import be.kdg.healthtips.notifications.NotificationThrower;
import be.kdg.healthtips.notifications.SpecificNotificationThrower;
import be.kdg.healthtips.notifications.TipGetter;
import be.kdg.healthtips.notifications.TipManager;
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

        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //test triggers
                SpecificNotificationThrower.throwBadFoodHabit(context);
                SpecificNotificationThrower.throwYouHaveEatenBeforeSporting(context);
                TipManager.throwRandomFallingASleepTip("Het duurde vorige nacht " + 35 + " minuten om in slaap te vallen", context);
                NotificationThrower.throwNotification(context, NotificationThrower.IconType.F_WEIGHT, "Gefeliciteerd", "U heeft een normale bmi behaald", HomeActivity.class, 0);
            }
        });


        setTip();
        setGoal();
    }

    private void setTip() {
        final TipGetter tipGetter = new TipGetter();
        final Tip tip = tipGetter.getRandomTip(this);
        TextView tipTitle = (TextView) findViewById(R.id.tipTitle);
        tipTitle.setText(tip.getTitel());
        ImageButton tipImageButton = (ImageButton) findViewById(R.id.tipButton);

        LinearLayout tipVanDeDag = (LinearLayout) findViewById(R.id.tipLayout);
        tipVanDeDag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TipDetailActivity.class);
                intent.putExtra("titel", tip.getTitel());
                intent.putExtra("beschrijving", tip.getBeschrijving());
                v.getContext().startActivity(intent);
            }
        });

        if(tip.getOnderwerp().equals("food")){
            tipImageButton.setBackgroundResource(R.drawable.foodicon);
        }
        if(tip.getOnderwerp().equals("sleep")){
            tipImageButton.setBackgroundResource(R.drawable.sleepicon);
        }
        if(tip.getOnderwerp().equals("steps")){
            tipImageButton.setBackgroundResource(R.drawable.stepsicon);
        }
        if(tip.getOnderwerp().equals("running")){
            tipImageButton.setBackgroundResource(R.drawable.runningicon);
        }
        if(tip.getOnderwerp().equals("biking")){
            tipImageButton.setBackgroundResource(R.drawable.bikingicon);
        }
        if(tip.getOnderwerp().equals("gezondheid")){
            tipImageButton.setBackgroundResource(R.drawable.healticon);
        }


    }

    private void setGoal() {
        Integer progressToReturn = 0;
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, cal.getActualMinimum(Calendar.DAY_OF_WEEK));

            JSONObject weeklyGoals = new GetWeeklyGoalATask(this).execute().get();
            JSONObject stepsThisWeek = new GetPeriodStepsATask(this).execute(cal.getTime(), new Date()).get();
            if (weeklyGoals != null && stepsThisWeek != null) {
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
            } else
                System.err.println("Can't get data, maybe you have no temboo credit anymore for this month?");
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
