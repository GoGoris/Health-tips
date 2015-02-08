package be.kdg.healthtips.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import be.kdg.healthtips.R;
import be.kdg.healthtips.adapter.TipAdapter;
import be.kdg.healthtips.model.Tip;
import be.kdg.healthtips.notifications.TipGetter;

public class TipDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_detail);


        TextView titel = (TextView) findViewById(R.id.detailtitel);
        TextView beschrijving = (TextView) findViewById(R.id.detailbeschrijving);

        Bundle extras = getIntent().getExtras();



        int tipnr = extras.getInt("tipnr",0);

        if(tipnr == 0){
            titel.setText(extras.getString("titel","Tip niet gevonden"));
            beschrijving.setText(extras.getString("beschrijving", "Geen data"));
        }else{
            TipGetter tipGetter = new TipGetter();
            Tip tip = tipGetter.getTipByNr(tipnr,this);

            titel.setText(tip.getTitel());
            beschrijving.setText(tip.getBeschrijving());
        }





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tip_detail, menu);
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
}
