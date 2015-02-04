package be.kdg.healthtips.task;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;

import com.temboo.Library.Fitbit.Sleep.GetSleep;
import com.temboo.Library.Fitbit.Statistics.GetTimeSeriesByDateRange;
import com.temboo.core.TembooException;
import com.temboo.core.TembooSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import be.kdg.healthtips.activity.LoginActivity;
import be.kdg.healthtips.auth.FitbitTokenManager;
import be.kdg.healthtips.session.TembooSessionManager;

/**
 * Created by school on 4/2/2015.
 */
public class GetDaySleepATask extends AsyncTask<Void, Void, JSONObject> {
    private FitbitTokenManager tokenManager;
    private Context context;

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public GetDaySleepATask(Context context)
    {
        super();
        this.tokenManager = FitbitTokenManager.getInstance(context);
        this.context = context;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        JSONObject jsonToReturn = new JSONObject();
        try {
            TembooSession session = TembooSessionManager.getSession();
            GetSleep getSleep = new GetSleep(session);

            GetSleep.GetSleepInputSet input = getSleep.newInputSet();

            input.set_AccessToken(tokenManager.getFitBitAccesToken());
            Calendar c = Calendar.getInstance();


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            c.add(Calendar.DAY_OF_YEAR,-1);

            input.set_Date(sdf.format(new Date()));


            input.set_AccessTokenSecret(tokenManager.getFitBitAccesTokenSecret());
            input.set_ConsumerSecret(FitbitTokenManager.getConsumerSecret());
            input.set_ConsumerKey(FitbitTokenManager.getConsumerKey());


            GetSleep.GetSleepResultSet result = getSleep.execute(input);

            jsonToReturn = new JSONObject(result.get_Response());
        } catch (TembooException e) {
            if(e.getMessage().contains("status code of 401")) {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonToReturn;
    }
}
