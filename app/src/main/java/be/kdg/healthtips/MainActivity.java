package be.kdg.healthtips;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import io.oauth.*;


public class MainActivity extends Activity implements OAuthCallback { // implement the OAuthCallback interface to get the right information

	Button fitbit;
	TextView fitbitText;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            
        final OAuth o = new OAuth(this);
        o.initialize("h-U4Xcc5kGPj7b2LPUrsW4aRjL8"); // Initialize the oauth key

        fitbit = (Button) findViewById(R.id.fitbit);
        fitbitText = (TextView) findViewById(R.id.fitbitText);

        fitbit.setOnClickListener(new View.OnClickListener() { // Listen the on click event
            @Override
            public void onClick(View v) {
                o.popup("fitbit", MainActivity.this); // Launch the pop up with the right provider & callback
            }
        });
        
    }

    /*
    **	Get the information
    **
    */
	public void onFinished(OAuthData data) {
		final TextView textview = fitbitText;
		if ( ! data.status.equals("success")) {
			textview.setTextColor(Color.parseColor("#FF0000"));
			textview.setText("error, " + data.error);
		}
		
		// You can access the tokens through data.token and data.secret
		
		textview.setText("loading...");
		textview.setTextColor(Color.parseColor("#00FF00"));
		
		// Let's skip the NetworkOnMainThreadException for the purpose of this sample.
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

		// To make an authenticated request, you can implement OAuthRequest with your prefered way.
		// Here, we use an URLConnection (HttpURLConnection) but you can use any library.
        //TODO: change to Fitbit login logic
        String _url = "https://www.fitbit.com/oauth/authorize";
		data.http(_url, new OAuthRequest() {
			private URL url;
			private URLConnection con;

			@Override
			public void onSetURL(String _url) {
				try {
					url = new URL(_url);
					con = url.openConnection();
				} catch (Exception e) { e.printStackTrace(); }
			}

			@Override
			public void onSetHeader(String header, String value) {
				con.addRequestProperty(header, value);
			}

			@Override
			public void onReady() {
				try {
					BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream()));
					StringBuilder total = new StringBuilder();
					String line;
					while ((line = r.readLine()) != null) {
					    total.append(line);
					}
					JSONObject result = new JSONObject(total.toString());
					textview.setText("hello, " + result.getString("name"));
				} catch (Exception e) { e.printStackTrace(); }
			}

			@Override
			public void onError(String message) {
				textview.setText("error: " + message);
			}
		});
    }
}
