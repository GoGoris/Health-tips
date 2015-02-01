package be.kdg.healthtips.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import be.kdg.healthtips.activity.FitBitAuthActivity;
import be.kdg.healthtips.auth.AuthManager;

/**
 * Created by Mathi on 24/01/2015.
 */
public class InitAuthATask extends AsyncTask<AuthManager, Void, String> {
    private Context context;

    public InitAuthATask(Context context)
    {
        super();
        this.context = context;
    }

    @Override
    protected String doInBackground(AuthManager... params) {
        AuthManager man = params[0];
        return man.initializeOAuth();
    }

    @Override
    protected void onPostExecute(String s) {
        if (s != null && s.contains("https://www.fitbit.com/oauth/authorize?"))
        {
            Intent intent = new Intent(context, FitBitAuthActivity.class);
            intent.putExtra("callbackUrl", s);
            context.startActivity(intent);
        }
        else
        {
            // TODO ERROR !
            CharSequence text = "Error while getting callback url!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}
