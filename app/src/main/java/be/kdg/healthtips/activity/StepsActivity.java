package be.kdg.healthtips.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.YLabels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import be.kdg.healthtips.R;
import be.kdg.healthtips.adapter.TipAdapter;
import be.kdg.healthtips.listener.TipClickListener;
import be.kdg.healthtips.task.GetDataATask;

public class StepsActivity extends Activity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        this.context = this;
        try {

            BarChart chart = (BarChart) findViewById(R.id.stepChart);
            chart.setBackground(getResources().getDrawable(R.drawable.graph_background));
            chart.setDrawBarShadow(false);
            chart.setDrawYValues(true);
            chart.setDrawLegend(false);
            chart.setDrawXLabels(false);
            chart.setDescription("");
            chart.set3DEnabled(false);
            chart.setDrawGridBackground(false);
            chart.setDrawHorizontalGrid(true);
            chart.setDrawVerticalGrid(false);
            chart.setDrawBorder(false);
            chart.animateY(2500);
            JSONObject jsonData = new GetDataATask(context).execute(getParameterArray()).get();
            BarData data = generateLineDataFromJson(jsonData);
            chart.setData(data);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        ListView lvLopen = (ListView) findViewById(R.id.lvLopen);
        TipAdapter adapter = new TipAdapter(this, android.R.layout.simple_list_item_1, "running");
        lvLopen.setAdapter(adapter);
        lvLopen.setOnItemClickListener(new TipClickListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_steps, menu);
        return true;
    }

    private Object[] getParameterArray() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_YEAR, -5);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        Object[] startAndEnd = new Object[3];
        startAndEnd[0] = cal.getTime();
        startAndEnd[1] = new Date();
        startAndEnd[2] = "activities/log/steps";
        return startAndEnd;
    }

    private BarData generateLineDataFromJson(JSONObject obj) {
        BarData data = null;
        try {
            JSONArray arr = obj.getJSONArray("activities-log-steps");

            ArrayList<BarEntry> entries = new ArrayList<>();
            int count = 1; //Counter for days
            int currentWeek = 1;
            int sum = 0; //Sum of steps in the week
            for (int i = 0; i < arr.length(); i++) {
                if (count % 8 == 0) {
                    BarEntry entry = new BarEntry(sum, currentWeek - 1);
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
            BarEntry entry = new BarEntry(sum, currentWeek - 1);
            entries.add(entry);

            BarDataSet set = new BarDataSet(entries, "Last 6 weeks");
            set.setColors(new int[]{R.color.primary_dark, R.color.primary_dark, R.color.primary_dark, R.color.primary_dark, R.color.primary_dark, R.color.graph_current}, context);
            ArrayList<BarDataSet> sets = new ArrayList<>();
            sets.add(set);
            ArrayList<String> XLabels = new ArrayList<>();
            XLabels.add("1");
            XLabels.add("2");
            XLabels.add("3");
            XLabels.add("4");
            XLabels.add("5");
            XLabels.add("6");
            data = new BarData(XLabels, sets);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }

}
