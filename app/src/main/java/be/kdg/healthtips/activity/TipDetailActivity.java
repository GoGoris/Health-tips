package be.kdg.healthtips.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import be.kdg.healthtips.R;
import be.kdg.healthtips.model.Tip;
import be.kdg.healthtips.notifications.TipGetter;

public class TipDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_detail);

        TextView beschrijving = (TextView) findViewById(R.id.detailbeschrijving);

        Bundle extras = getIntent().getExtras();

        int tipnr = extras.getInt("tipnr", 0);

        if (tipnr == 0) {
            String tipTitle = extras.getString("titel", "Tip niet gevonden");
            String tipDescription = extras.getString("beschrijving", "Geen data");
            setTitle(tipTitle);
            beschrijving.setText(tipDescription);
        } else {
            TipGetter tipGetter = new TipGetter();
            Tip tip = tipGetter.getTipByNr(tipnr, this);

            setTitle(tip.getTitel());
            beschrijving.setText(tip.getBeschrijving());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tip_detail, menu);
        return true;
    }
}
