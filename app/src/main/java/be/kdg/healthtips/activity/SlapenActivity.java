package be.kdg.healthtips.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import be.kdg.healthtips.R;
import be.kdg.healthtips.adapter.TipAdapter;
import be.kdg.healthtips.listener.TipClickListener;
import be.kdg.healthtips.task.GetDataATask;

public class SlapenActivity extends Activity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slapen);

        this.context = this;

        ListView lvSlapen = (ListView) findViewById(R.id.lvSlapen);
        TipAdapter adapter = new TipAdapter(this, android.R.layout.simple_list_item_1, "sleep");
        lvSlapen.setAdapter(adapter);
        lvSlapen.setOnItemClickListener(new TipClickListener());

        try {
            JSONObject obj = new GetDataATask(context).execute(getParameterArray()).get();
            BarData barData = getBarDataFromJson(obj);

            BarChart chart = (BarChart) findViewById(R.id.bcSlaap);
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
            chart.setData(barData);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_slapen, menu);
        return true;
    }

    private Object[] getParameterArray() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_YEAR, -5);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        Object[] startAndEnd = new Object[3];
        startAndEnd[0] = cal.getTime();
        startAndEnd[1] = new Date();
        startAndEnd[2] = "sleep/minutesAsleep";
        return startAndEnd;
    }

    private BarData getBarDataFromJson(JSONObject jsonData) {
        BarData barData = null;

        try {
            JSONArray arr = jsonData.getJSONArray("sleep-minutesAsleep");
            ArrayList<BarEntry> entries = new ArrayList<>();
            int count = 1; //Counter for days
            int currentWeek = 1;
            int sum = 0; //Sum of minutes asleep in the week
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

            BarDataSet dataSet = new BarDataSet(entries, "");
            dataSet.setColors(new int[]{R.color.primary_dark, R.color.primary_dark, R.color.primary_dark, R.color.primary_dark, R.color.primary_dark, R.color.graph_current}, context);
            ArrayList<BarDataSet> sets = new ArrayList<>();
            sets.add(dataSet);

            ArrayList<String> XLabels = new ArrayList<>();
            XLabels.add("1");
            XLabels.add("2");
            XLabels.add("3");
            XLabels.add("4");
            XLabels.add("5");
            XLabels.add("6");
            barData = new BarData(XLabels, sets);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return barData;
    }
}
