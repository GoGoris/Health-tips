package be.kdg.healthtips.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;

import be.kdg.healthtips.R;
import be.kdg.healthtips.auth.AuthManager;
import be.kdg.healthtips.task.InitAuthATask;

public class LoginActivity extends Activity {

    private Button btnStartAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Context context = this;

        new InitAuthATask(context).execute(AuthManager.getInstance(context));

/*        btnStartAuth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }
}
