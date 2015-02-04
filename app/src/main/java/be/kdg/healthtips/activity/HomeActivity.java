package be.kdg.healthtips.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import be.kdg.healthtips.notifications.NotificationThrower;
import be.kdg.healthtips.notifications.TipManager;
import be.kdg.healthtips.task.GetDailyGoalATask;
import be.kdg.healthtips.task.GetDaySleepATask;
import be.kdg.healthtips.task.GetPeriodStepsATask;
import be.kdg.healthtips.task.GetWeeklyGoalATask;

public class HomeActivity extends ActionBarActivity {

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

        setTip();
        setMotivationMessage();
        setGoal();
    }

    private void setTip(){
        TextView tipTitle = (TextView)findViewById(R.id.tipTitle);
        tipTitle.setText("placeholder tip title");
    }

    private void setMotivationMessage(){
        TextView motivationText = (TextView)findViewById(R.id.motivationText);
        motivationText.setText("placeholder motivatie text");
    }

    private void setGoal(){
        Integer progressToReturn = 0;
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK,cal.getActualMinimum(Calendar.DAY_OF_WEEK));

            JSONObject stepsThisWeek  = new GetPeriodStepsATask(this).execute(cal.getTime(), new Date()).get();
            JSONObject weeklyGoals = new GetWeeklyGoalATask(this).execute().get();
            int weeklyStepGoal = weeklyGoals.getJSONObject("goals").getInt("steps");

            JSONArray allSteps = stepsThisWeek.getJSONArray("activities-log-steps");

            int currentWeekTotalSteps = 0;
            for (int i = 0; i < allSteps.length(); i++) {
                currentWeekTotalSteps += allSteps.getJSONObject(i).getInt("value");
            }

            progressToReturn = (int)Math.round((double)currentWeekTotalSteps / (double)weeklyStepGoal * 100);
            if(progressToReturn > 100){
                progressToReturn = 100;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setProgress(progressToReturn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
