package be.kdg.healthtips.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class SingleTipActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        int tipNr = b.getInt("tip");

        System.out.println(tipNr);

        //tip ophalen en displayen
    }
}
