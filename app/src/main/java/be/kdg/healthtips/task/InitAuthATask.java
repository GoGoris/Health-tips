package be.kdg.healthtips.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import be.kdg.healthtips.activity.FitBitAuthActivity;
import be.kdg.healthtips.auth.AuthManager;

public class InitAuthATask extends AsyncTask<AuthManager, Void, String> {
    private Context context;

    public InitAuthATask(Context context) {
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
        if (s != null && s.contains("https://www.fitbit.com/oauth/authorize?")) {
            Intent authIntent = new Intent(context, FitBitAuthActivity.class);
            authIntent.putExtra("callbackUrl", s);
            context.startActivity(authIntent);
        } else {
            CharSequence text = "Error while getting callback url!";
            System.err.println(text);

            Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
