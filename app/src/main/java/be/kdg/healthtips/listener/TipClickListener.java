package be.kdg.healthtips.listener;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import be.kdg.healthtips.activity.TipDetailActivity;
import be.kdg.healthtips.model.Tip;

public class TipClickListener implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Tip tip = (Tip) parent.getItemAtPosition(position);
        Intent intent = new Intent(view.getContext(), TipDetailActivity.class);
        intent.putExtra("titel", tip.getTitel());
        intent.putExtra("beschrijving", tip.getBeschrijving());
        view.getContext().startActivity(intent);
    }
}
