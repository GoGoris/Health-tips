package be.kdg.healthtips.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import be.kdg.healthtips.R;
import be.kdg.healthtips.adapter.TipAdapter;
import be.kdg.healthtips.listener.TipClickListener;

public class EtenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eten);

        ListView lvEten = (ListView) findViewById(R.id.lvEten);
        TipAdapter adapter = new TipAdapter(this, android.R.layout.simple_list_item_1, "food");
        lvEten.setAdapter(adapter);
        lvEten.setOnItemClickListener(new TipClickListener());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_eten, menu);
        return true;
    }
}
