package be.kdg.healthtips.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.util.concurrent.ExecutionException;

import be.kdg.healthtips.R;
import be.kdg.healthtips.auth.AuthManager;
import be.kdg.healthtips.task.InitAuthATask;

public class LoginActivity extends ActionBarActivity {

    private Button btnStartAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Context context = this;

        btnStartAuth = (Button) findViewById(R.id.btnStartAuth);

        btnStartAuth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            new InitAuthATask(context).execute(AuthManager.getInstance(context));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
