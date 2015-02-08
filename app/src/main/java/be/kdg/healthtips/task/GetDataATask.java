package be.kdg.healthtips.task;

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

/**
 * Created by school on 4/2/2015.
 */
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
        JSONObject jsonToReturn = new JSONObject();
        try {
            TembooSession session = TembooSessionManager.getSession();
            GetTimeSeriesByDateRange getData = new GetTimeSeriesByDateRange(session);

            GetTimeSeriesByDateRange.GetTimeSeriesByDateRangeInputSet input = getData.newInputSet();

            input.set_AccessToken(tokenManager.getFitBitAccesToken());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            input.set_StartDate(sdf.format((Date) params[0]));
            input.set_EndDate(sdf.format((Date) params[1]));

            input.set_AccessTokenSecret(tokenManager.getFitBitAccesTokenSecret());
            input.set_ConsumerSecret(FitbitTokenManager.getConsumerSecret());

            input.set_ResourcePath(String.valueOf(params[2]));

            input.set_ConsumerKey(FitbitTokenManager.getConsumerKey());

            GetTimeSeriesByDateRange.GetTimeSeriesByDateRangeResultSet result = getData.execute(input);

            String data = result.get_Response();
            jsonToReturn = new JSONObject(data);
        } catch (TembooException e) {
            if (e.getMessage().contains("status code of 401")) {
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