package be.kdg.healthtips.task;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;

import com.temboo.Library.Fitbit.Body.GetBodyWeightGoal;
import com.temboo.core.TembooException;
import com.temboo.core.TembooSession;

import org.json.JSONException;
import org.json.JSONObject;

import be.kdg.healthtips.activity.LoginActivity;
import be.kdg.healthtips.auth.FitbitTokenManager;
import be.kdg.healthtips.session.TembooSessionManager;

public class GetWeightGoalATask extends AsyncTask<Void, Void, JSONObject> {
    private FitbitTokenManager tokenManager;
    private Context context;

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public GetWeightGoalATask(Context context) {
        super();
        this.tokenManager = FitbitTokenManager.getInstance(context);
        this.context = context;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        JSONObject jsonToReturn = null;
        try {
            TembooSession session = TembooSessionManager.getSession();
            GetBodyWeightGoal getBodyWeightGoal = new GetBodyWeightGoal(session);

            GetBodyWeightGoal.GetBodyWeightGoalInputSet input = getBodyWeightGoal.newInputSet();

            input.set_AccessToken(tokenManager.getFitBitAccesToken());
            input.set_AccessTokenSecret(tokenManager.getFitBitAccesTokenSecret());
            input.set_ConsumerSecret(FitbitTokenManager.getConsumerSecret());
            input.set_ConsumerKey(FitbitTokenManager.getConsumerKey());

            GetBodyWeightGoal.GetBodyWeightGoalResultSet result = getBodyWeightGoal.execute(input);

            return new JSONObject(result.get_Response());
        } catch (TembooException e) {
            if (e.getMessage().contains("status code of 401")) {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }
            System.err.println("Temboo throwed an exception, can't get weight goal from Temboo API.");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
