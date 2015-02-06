package be.kdg.healthtips.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import be.kdg.healthtips.model.Tip;

/**
 * Created by Mathi on 6/02/2015.
 */
public class TipAdapter extends ArrayAdapter<Tip> {



    public TipAdapter(Context context, int resource, String categorie) {
        super(context, resource);
        addTipsByCategorie(categorie, context);


    }

    public void addTipsByCategorie(String arrayName, Context context) {
        try {
            InputStream is = context.getAssets().open("tips.json");

            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray(arrayName);

            for(int i = 0; i < array.length(); i++)
            {
                try {
                    JSONObject obj =  array.getJSONObject(i);
                    int nummer = obj.getInt("tipnr");
                    String titel = obj.getString("titel");
                    String besch = obj.getString("beschrijving");
                    Tip tip = new Tip(nummer, titel, besch);
                    add(tip);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
