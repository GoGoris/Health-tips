package be.kdg.healthtips.notifications;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Random;

import be.kdg.healthtips.model.Tip;

/**
 * Created by school on 8/2/2015.
 */
public class TipGetter {
    public int getRandomTipNr(String arrayName, Context context) {
        try {
            InputStream is = context.getAssets().open("tips.json");

            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray(arrayName);

            Random r = new Random();
            int index = r.nextInt(array.length());

            JSONObject tip = array.getJSONObject(index);
            return tip.getInt("tipnr");
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Tip getTipByNr(int tipNr,Context context){
        Tip tipToReturn = null;
        try {
            InputStream is = context.getAssets().open("tips.json");

            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");
            JSONObject firstObject = new JSONObject(json);
            Iterator<?> keys = firstObject.keys();

            while( keys.hasNext() ){
                String key = (String)keys.next();
                if( firstObject.get(key) instanceof JSONArray ){
                    JSONArray subjectArray = (JSONArray)firstObject.get(key);
                    for (int j = 0; j < subjectArray.length(); j++) {
                        JSONObject tipObject = subjectArray.getJSONObject(j);
                        if(tipObject.getInt("tipnr") == tipNr){
                            tipToReturn = new Tip(tipObject.getInt("tipnr"),tipObject.getString("titel"),tipObject.getString("beschrijving"));
                        }
                    }
                }
            }


            return tipToReturn;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tipToReturn;
    }
}
