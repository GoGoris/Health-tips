package be.kdg.healthtips.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import be.kdg.healthtips.R;
import be.kdg.healthtips.auth.AuthManager;
import be.kdg.healthtips.task.FinAuthATask;

public class FitBitAuthActivity extends Activity {

    @SuppressLint("SetJavaScriptEnabled")
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fit_bit_auth);

        Context context = this;

        String url = getIntent().getStringExtra("callbackUrl");

        WebView webview = (WebView) findViewById(R.id.wvFitBit);
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(url);
        new FinAuthATask(context).execute(AuthManager.getInstance(context));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fit_bit_auth, menu);
        return true;
    }
}
