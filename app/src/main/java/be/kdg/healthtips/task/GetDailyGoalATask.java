package be.kdg.healthtips.task;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;

import com.temboo.Library.Fitbit.Activities.GetActivityDailyGoals;
import com.temboo.core.TembooException;
import com.temboo.core.TembooSession;

import org.json.JSONException;
import org.json.JSONObject;

import be.kdg.healthtips.activity.LoginActivity;
import be.kdg.healthtips.auth.FitbitTokenManager;
import be.kdg.healthtips.session.TembooSessionManager;

/**
 * Created by school on 4/2/2015.
 */
public class GetDailyGoalATask extends AsyncTask<Void,Void,JSONObject> {
    private FitbitTokenManager tokenManager;
    private Context context;

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public GetDailyGoalATask(Context context)
    {
        super();
        this.tokenManager = FitbitTokenManager.getInstance(context);
        this.context = context;
    }
    @Override
    protected JSONObject doInBackground(Void... params) {
        TembooSession session = null;
        JSONObject jsonToReturn = null;
        try {
            session = TembooSessionManager.getSession();

            GetActivityDailyGoals getActivityDailyGoalsChoreo = new GetActivityDailyGoals(session);

            GetActivityDailyGoals.GetActivityDailyGoalsInputSet input = getActivityDailyGoalsChoreo.newInputSet();
            input.set_AccessToken(tokenManager.getFitBitAccesToken());
            input.set_AccessTokenSecret(tokenManager.getFitBitAccesTokenSecret());
            input.set_ConsumerSecret(FitbitTokenManager.getConsumerSecret());
            input.set_ConsumerKey(FitbitTokenManager.getConsumerKey());

            GetActivityDailyGoals.GetActivityDailyGoalsResultSet result = getActivityDailyGoalsChoreo.execute(input);

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
