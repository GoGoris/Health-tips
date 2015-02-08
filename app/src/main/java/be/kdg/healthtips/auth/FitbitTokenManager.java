package be.kdg.healthtips.auth;

import android.content.Context;
import android.content.SharedPreferences;

public class    FitbitTokenManager {
    private static final String CONSUMER_KEY = "0dc58a7d5b1349a187b74e6e82d989f5";
    private static final String CONSUMER_SECRET = "2d234d453df949a786971a9253a22f99";
    private String fitBitAccesToken;
    private String fitBitAccesTokenSecret;
    private static FitbitTokenManager instance;
    private SharedPreferences sharedPreferences;

    public FitbitTokenManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences("keys", Context.MODE_MULTI_PROCESS);
    }

    public static String getConsumerKey() {
        return CONSUMER_KEY;
    }

    public static String getConsumerSecret() {
        return CONSUMER_SECRET;
    }

    public void setFitBitAccesToken(String fitBitAccesToken) {
        this.fitBitAccesToken = fitBitAccesToken;

        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString("FitbitAccessToken", fitBitAccesToken);
        sharedPreferencesEditor.apply();
    }

    public void setFitBitAccesTokenSecret(String fitBitAccesTokenSecret) {
        this.fitBitAccesTokenSecret = fitBitAccesTokenSecret;

        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString("FitbitAccessTokenSecret", fitBitAccesTokenSecret);
        sharedPreferencesEditor.apply();
    }

    public String getFitBitAccesToken() {
        if (fitBitAccesToken == null || fitBitAccesToken.isEmpty())
            fitBitAccesToken = sharedPreferences.getString("FitbitAccessToken", "");
        return fitBitAccesToken;
    }

    public String getFitBitAccesTokenSecret() {
        if (fitBitAccesTokenSecret == null || fitBitAccesTokenSecret.isEmpty())
            fitBitAccesTokenSecret = sharedPreferences.getString("FitbitAccessTokenSecret", "");
        return fitBitAccesTokenSecret;
    }

    public static FitbitTokenManager getInstance(Context context) {
        if (instance == null) instance = new FitbitTokenManager(context);
        return instance;
    }
}
