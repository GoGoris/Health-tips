package be.kdg.healthtips.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.YLabels;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import be.kdg.healthtips.R;
import be.kdg.healthtips.adapter.TipAdapter;
import be.kdg.healthtips.listener.TipClickListener;
import be.kdg.healthtips.model.Tip;
import be.kdg.healthtips.task.GetStepsATask;

public class StepsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        try {
            final Context context = this;
            LineChart chart = (LineChart) findViewById(R.id.stepChart);
            chart.setDescription("Last 6 weeks");
            chart.setDrawYValues(true); // waardes bij de punten
            chart.setBackgroundColor(Color.GRAY);
            chart.setPinchZoom(true);
            chart.setDragEnabled(true);
            chart.setScaleEnabled(true);
            chart.setDrawGridBackground(false);
            chart.setDrawVerticalGrid(false);
            chart.setDrawHorizontalGrid(false);
            chart.setHighlightEnabled(true);


            LineData data = new GetStepsATask(context).execute(get6WeeksStartAndEndString()).get();

            XLabels xl = chart.getXLabels();
            xl.setTextColor(Color.WHITE);

            YLabels yl = chart.getYLabels();
            yl.setTextColor(Color.WHITE);

            chart.animateX(2500);

            chart.setData(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String[] get6WeeksStartAndEndString() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_YEAR, -5);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] startAndEnd = new String[2];
        startAndEnd[0] = sdf.format(cal.getTime());
        startAndEnd[1] = sdf.format(new Date());
        return startAndEnd;
    }

}
