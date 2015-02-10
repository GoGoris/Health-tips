package be.kdg.healthtips.task;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;

import com.temboo.Library.Fitbit.Statistics.GetTimeSeriesByDateRange;
import com.temboo.core.TembooException;
import com.temboo.core.TembooSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import be.kdg.healthtips.activity.LoginActivity;
import be.kdg.healthtips.auth.FitbitTokenManager;
import be.kdg.healthtips.session.TembooSessionManager;

public class GetDataATask extends AsyncTask<Object, Void, JSONObject> {
    private FitbitTokenManager tokenManager;
    private Context context;

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public GetDataATask(Context context) {
        super();
        this.tokenManager = FitbitTokenManager.getInstance(context);
        this.context = context;
    }

    @Override
    //1 begindatum 2 einddatum 3 resource
    protected JSONObject doInBackground(Object... params) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            TembooSession session = TembooSessionManager.getSession();
            GetTimeSeriesByDateRange getData = new GetTimeSeriesByDateRange(session);

            GetTimeSeriesByDateRange.GetTimeSeriesByDateRangeInputSet input = new GetTimeSeriesByDateRange(session).newInputSet();
            input.set_AccessToken(tokenManager.getFitBitAccesToken());
            input.set_StartDate(sdf.format((Date) params[0]));
            input.set_EndDate(sdf.format((Date) params[1]));
            input.set_AccessTokenSecret(tokenManager.getFitBitAccesTokenSecret());
            input.set_ConsumerSecret(FitbitTokenManager.getConsumerSecret());
            input.set_ResourcePath(String.valueOf(params[2]));
            input.set_ConsumerKey(FitbitTokenManager.getConsumerKey());

            GetTimeSeriesByDateRange.GetTimeSeriesByDateRangeResultSet result = getData.execute(input);
            return new JSONObject(result.get_Response());
        } catch (TembooException e) {
            if (e.getMessage().contains("status code of 401")) {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }
            System.err.println("Temboo throwed an exception, can't get data from Temboo API.");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}