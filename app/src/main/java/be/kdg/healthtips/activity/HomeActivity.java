package be.kdg.healthtips.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import be.kdg.healthtips.R;
import be.kdg.healthtips.task.GetStepsATask;

public class HomeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;
        SharedPreferences sharedPreferences = getSharedPreferences("keys", Context.MODE_MULTI_PROCESS);
        
        String accessToken = sharedPreferences.getString("FitbitAccessToken", "");
        String accessTokenSecret = sharedPreferences.getString("FitbitAccessTokenSecret", "");

        if(accessToken.equals("")||accessTokenSecret.equals("")){
            Intent intent = new Intent(this, LoginActivity.class);
            this.startActivity(intent);
        }

        CharSequence text = "accesstoken: " + accessToken + "\naccesstoken secret: " + accessTokenSecret;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(this, text, duration);
        toast.show();

        setContentView(R.layout.activity_home_joni);

        Button btnSteps = (Button) findViewById(R.id.btnSteps);

        btnSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetStepsATask(context).execute(get6WeeksStartAndEndString());
            }
        });

        setTip();
        setMotivationMessage();
        setGoal();
    }

    private void setTip(){
        TextView motivationText = (TextView)findViewById(R.id.motivationText);
        motivationText.setText("veranderen van text werkt");
    }

    private void setMotivationMessage(){

    }

    private void setGoal(){

    }

    private String[] get6WeeksStartAndEndString() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_YEAR, -5);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] startAndEnd = new String[2];
        startAndEnd[0] = sdf.format(cal.getTime());
        startAndEnd[1] = sdf.format(new Date());
        return startAndEnd;
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
