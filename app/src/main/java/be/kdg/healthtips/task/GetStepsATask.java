package be.kdg.healthtips.task;

import android.content.Context;
import android.os.AsyncTask;

import java.text.SimpleDateFormat;
import java.util.Date;

import be.kdg.healthtips.data.DataManager;

/**
 * Created by Mathi on 1/02/2015.
 */
public class GetStepsATask extends AsyncTask<DataManager, Void, Void> {

    private Context context;

    public GetStepsATask(Context context)
    {
        super();
        this.context = context;

    }


    @Override
    protected Void doInBackground(DataManager... params) {
        DataManager man = params[0];
        Date date = new Date();
        man.getSteps();

        return null;
    }
}
