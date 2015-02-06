package be.kdg.healthtips.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import be.kdg.healthtips.model.Tip;

/**
 * Created by Mathi on 6/02/2015.
 */
public class TipAdapter extends ArrayAdapter<Tip> {

    public TipAdapter(Context context, int resource, List<Tip> objects) {
        super(context, resource, objects);

    }
}
