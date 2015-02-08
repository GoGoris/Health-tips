package be.kdg.healthtips.notifications;

import android.content.Context;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Random;

import be.kdg.healthtips.activity.SingleTipActivity;
import be.kdg.healthtips.activity.TipDetailActivity;
import be.kdg.healthtips.adapter.TipAdapter;
import be.kdg.healthtips.model.Tip;

/**
 * Created by school on 4/2/2015.
 */
public class TipManager {
    public static void throwRandomStepTip(String reden, Context context){
        throwTipOfSubject("steps",reden,"Get more steps",context, NotificationThrower.IconType.T_STEPS);
    }

    public static void throwRandomRunningTip(String reden, Context context){
        throwTipOfSubject("running",reden,"Run more efficiently",context, NotificationThrower.IconType.T_RUNNING);
    }

    public static void throwRandomFallingASleepTip(String reden, Context context){
        throwTipOfSubject("sleep",reden,"Fall asleep faster",context, NotificationThrower.IconType.T_SLEEP);
    }

    public static void throwRandomSleepTip(String reden, Context context){
        throwTipOfSubject("sleep",reden,"Sleep better",context, NotificationThrower.IconType.T_SLEEP);
    }

    public static void throwRandomGewichtTip(String reden, Context context){
        Random r = new Random();
        if(r.nextBoolean()){
            throwRandomFoodTip(reden, context);
        }else{
            throwTipOfSubject("food",reden,"Loose weight",context, NotificationThrower.IconType.T_WEIGHT);
        }
    }

    public static void throwRandomFoodTip(String reden, Context context){
        throwTipOfSubject("food",reden,"Eat Healthy",context, NotificationThrower.IconType.T_FOOD);
    }

    private static void throwTipOfSubject(String subject, String reden, String titel, Context context, NotificationThrower.IconType typeIcon){
        //TipAdapter adapter = new TipAdapter(context, android.R.layout.simple_list_item_1, subject);
        TipGetter tipGetter = new TipGetter();
        int tipnr = tipGetter.getRandomTipNr(subject, context);
        NotificationThrower.throwNotification(context,typeIcon,titel,reden, TipDetailActivity.class,tipnr);
    }


}
