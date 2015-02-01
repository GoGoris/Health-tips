package be.kdg.healthtips.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.temboo.Library.Fitbit.Statistics.GetTimeSeriesByDateRange;
import com.temboo.core.TembooException;
import com.temboo.core.TembooSession;

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

    public int getSteps(String startDate, String endDate) {
        int output = -1;
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

            System.out.println(result.get_Response());




        } catch (TembooException e) {
            e.printStackTrace();
        }
        return output;
    }


}
