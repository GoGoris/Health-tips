package be.kdg.healthtips.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.JsonReader;

import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.LineData;
import com.temboo.Library.Fitbit.Statistics.GetTimeSeriesByDateRange;
import com.temboo.core.TembooException;
import com.temboo.core.TembooSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import be.kdg.healthtips.session.SessionManager;

/**
 * Created by Mathi on 1/02/2015.
 */
public class DataManager {

    private static DataManager dataManager;
    private static final String CONSUMER_KEY = "0dc58a7d5b1349a187b74e6e82d989f5";
    private static final String CONSUMER_SECRET = "2d234d453df949a786971a9253a22f99";
    SharedPreferences sharedPreferences;
    private Context context;

    private DataManager(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("keys", Context.MODE_MULTI_PROCESS);


    }

    public static DataManager getInstance(Context context) {
        if (dataManager == null) {
            dataManager = new DataManager(context);
        }
        return dataManager;
    }

    public ChartData getSteps() {
        LineData data = null;

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_YEAR, -5);
        cal.set(Calendar.DAY_OF_WEEK, 2);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = sdf.format(cal.getTime());
        String endDate = sdf.format(new Date());

        try {

            TembooSession session = SessionManager.getSession();
            GetTimeSeriesByDateRange getSteps = new GetTimeSeriesByDateRange(session);

            String accessToken = sharedPreferences.getString("FitbitAccessToken", "");
            String accessTokenSecret = sharedPreferences.getString("FitbitAccessTokenSecret", "");

            GetTimeSeriesByDateRange.GetTimeSeriesByDateRangeInputSet input = getSteps.newInputSet();
            input.set_EndDate(endDate);
            input.set_AccessToken(accessToken);
            input.set_StartDate(startDate);
            input.set_AccessTokenSecret(accessTokenSecret);
            input.set_ConsumerSecret(CONSUMER_SECRET);
            input.set_ResourcePath("activities/log/steps");
            input.set_ConsumerKey(CONSUMER_KEY);

            GetTimeSeriesByDateRange.GetTimeSeriesByDateRangeResultSet result = getSteps.execute(input);

            JSONObject obj = new JSONObject(result.get_Response());
            JSONArray arr = obj.getJSONArray("activities-log-steps");

            for(int i = 0; i < arr.length(); i++)
            {
                JSONObject o = arr.getJSONObject(i);
                String date = o.getString("dateTime");
                int value = o.getInt("value");
                //TODO INFO IN GRAFIEK
            }


        } catch (TembooException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }


}
