package be.kdg.healthtips.task;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.temboo.Library.Fitbit.Statistics.GetTimeSeriesByDateRange;
import com.temboo.core.TembooException;
import com.temboo.core.TembooSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.kdg.healthtips.activity.LoginActivity;
import be.kdg.healthtips.activity.StepsActivity;
import be.kdg.healthtips.auth.FitbitTokenManager;
import be.kdg.healthtips.auth.FitbitTokenManager;
import be.kdg.healthtips.session.TembooSessionManager;

/**
 * This Asynctask fetches the steps from the FitBit-API (using temboo)
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class GetStepsATask extends AsyncTask<String, Void, LineData> {
        private FitbitTokenManager tokenManager;
        private Context context;

        @TargetApi(Build.VERSION_CODES.CUPCAKE)
        public GetStepsATask(Context context)
        {
            super();
            this.tokenManager = FitbitTokenManager.getInstance(context);
            this.context = context;
        }

    /**
     * @param dates The start and end date to fetch data for
     * @return The amount of steps between the given dates
     */
    @Override
    protected LineData doInBackground(String... dates) {
        LineData data = null;

        try {
            TembooSession session = TembooSessionManager.getSession();
            GetTimeSeriesByDateRange getSteps = new GetTimeSeriesByDateRange(session);

            GetTimeSeriesByDateRange.GetTimeSeriesByDateRangeInputSet input = getSteps.newInputSet();
            input.set_EndDate(dates[1]);
            input.set_AccessToken(tokenManager.getFitBitAccesToken());
            input.set_StartDate(dates[0]);
            input.set_AccessTokenSecret(tokenManager.getFitBitAccesTokenSecret());
            input.set_ConsumerSecret(FitbitTokenManager.getConsumerSecret());
            input.set_ResourcePath("activities/log/steps");
            input.set_ConsumerKey(FitbitTokenManager.getConsumerKey());

            GetTimeSeriesByDateRange.GetTimeSeriesByDateRangeResultSet result = getSteps.execute(input);

            data = generateLineDataFromJson(result.get_Response());

        } catch (TembooException e) {
            if(e.getMessage().contains("status code of 401")) {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }
            e.printStackTrace();
        }
        return data;
    }

    private LineData generateLineDataFromJson(String JsonString) {
        LineData data = null;
        try {
            JSONObject obj = new JSONObject(JsonString);
            JSONArray arr = obj.getJSONArray("activities-log-steps");

            ArrayList<Entry> entries = new ArrayList<>();
            int count = 1; //Counter for days
            int currentWeek = 1;
            int sum = 0; //Sum of steps in the week
            for (int i = 0; i < arr.length(); i++) {
                if (count % 8 == 0) {
                    Entry entry = new Entry(sum, currentWeek - 1);
                    entries.add(entry);
                    sum = 0;
                    count = 1;
                    currentWeek++;
                }
            JSONObject o = arr.getJSONObject(i);
            int value = o.getInt("value");
            sum += value;
            count++;
        }
        //Add last day --not added in the loop--
        Entry entry = new Entry(sum, currentWeek - 1);
        entries.add(entry);

        LineDataSet set = new LineDataSet(entries, "Last 6 weeks");
        ArrayList<LineDataSet> sets = new ArrayList<>();
        sets.add(set);
        ArrayList<String> XLabels = new ArrayList<>();
        XLabels.add("1");
        XLabels.add("2");
        XLabels.add("3");
        XLabels.add("4");
        XLabels.add("5");
        XLabels.add("6");
        data = new LineData(XLabels, sets);
    }
        catch (JSONException e) {
        e.printStackTrace();
    }

        return data;
    }
}
