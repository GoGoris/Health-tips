package be.kdg.healthtips.activity;

import android.app.Activity;
import android.os.Bundle;

public class SingleTipActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        int tipNr = b.getInt("tip");

        System.out.println(tipNr);

        //tip ophalen en displayen
    }
}
