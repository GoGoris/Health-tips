package be.kdg.healthtips.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import be.kdg.healthtips.R;
import be.kdg.healthtips.adapter.TipAdapter;
import be.kdg.healthtips.model.Tip;

public class TipActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);
        final ListView listView = (ListView) findViewById(R.id.lvTips);

        Bundle bundle = getIntent().getExtras();
        String categorie = bundle.getString("categorie", "");
        List<Tip> tips = getTipsByCategorie(categorie);
        TipAdapter adapter = new TipAdapter(this, android.R.layout.simple_list_item_1, tips);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tip tip = (Tip) parent.getItemAtPosition(position);
                Intent intent = new Intent(view.getContext(), TipDetailActivity.class);
                intent.putExtra("titel", tip.getTitel());
                intent.putExtra("beschrijving", tip.getBeschrijving());
                startActivity(intent);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tip, menu);
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

    public List<Tip> getTipsByCategorie(String arrayName) {
        ArrayList<Tip> tips = new ArrayList<>();
        try {

            InputStream is = getAssets().open("tips.json");

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
                    tips.add(tip);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tips;

    }

}
