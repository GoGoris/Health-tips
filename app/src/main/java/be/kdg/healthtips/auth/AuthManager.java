package be.kdg.healthtips.auth;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInstaller;
import android.os.Build;

import com.temboo.Library.Fitbit.OAuth.InitializeOAuth;
import com.temboo.core.TembooException;
import com.temboo.core.TembooSession;
import com.temboo.Library.Fitbit.OAuth.FinalizeOAuth;
import com.temboo.Library.Fitbit.OAuth.FinalizeOAuth.FinalizeOAuthInputSet;
import com.temboo.Library.Fitbit.OAuth.FinalizeOAuth.FinalizeOAuthResultSet;
import com.temboo.Library.Fitbit.OAuth.InitializeOAuth;
import com.temboo.Library.Fitbit.OAuth.InitializeOAuth.InitializeOAuthInputSet;
import com.temboo.Library.Fitbit.OAuth.InitializeOAuth.InitializeOAuthResultSet;


import be.kdg.healthtips.session.SessionManager;

/**
 * Created by Mathi on 24/01/2015.
 */
public class AuthManager {

    private static AuthManager manager;
    private Context context;
    private static final String CONSUMER_KEY = "0dc58a7d5b1349a187b74e6e82d989f5";
    private static final String CONSUMER_SECRET = "2d234d453df949a786971a9253a22f99";
    private boolean authorized = false;
    private String callbackId;
    private String oAuthToken;
    private String callBackUrl;
    private String fitBitAccesToken;
    private String fitBitAccesTokenSecret;

    private AuthManager(Context context)
    {
        this.context = context;
    }

    public static AuthManager getInstance(Context context)
    {
        if(manager == null)
        {
            manager = new AuthManager(context);
        }
        return manager;
    }

    public String initializeOAuth()
    {
        try {
            TembooSession session = SessionManager.getSession();
            InitializeOAuth initOAauth = new InitializeOAuth(session);

            InitializeOAuthInputSet input = initOAauth.newInputSet();
            input.set_ConsumerKey(CONSUMER_KEY);
            input.set_ConsumerSecret(CONSUMER_SECRET);

            InitializeOAuthResultSet results = initOAauth.execute(input);

            callbackId = results.get_CallbackID();
            oAuthToken = results.get_OAuthTokenSecret();
            callBackUrl = results.get_AuthorizationURL();
        }

        catch(TembooException e){
            e.printStackTrace();
            authorized = false;
            fitBitAccesToken = null;
            fitBitAccesTokenSecret = null;
            callbackId = null;
            callBackUrl = null;
            oAuthToken = null;
        }
        return callBackUrl;
    }

    public boolean finalizeOAuth()
    {
        try
        {
            TembooSession session = SessionManager.getSession();
            FinalizeOAuth finOAuth = new FinalizeOAuth(session);

            FinalizeOAuthInputSet input = finOAuth.newInputSet();

            input.set_CallbackID(callbackId);
            input.set_OAuthTokenSecret(oAuthToken);
            input.set_ConsumerSecret(CONSUMER_SECRET);
            input.set_ConsumerKey(CONSUMER_KEY);

            FinalizeOAuthResultSet results = finOAuth.execute(input);
            authorized = true;
            fitBitAccesToken = results.get_AccessToken();
            fitBitAccesTokenSecret = results.get_AccessTokenSecret();

            SharedPreferences sharedPreferences = context.getSharedPreferences("keys",Context.MODE_MULTI_PROCESS);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("FitbitAccessToken",fitBitAccesToken);
            editor.putString("FitbitAccessTokenSecret",fitBitAccesTokenSecret);
            editor.commit();
        }
        catch (TembooException e)
        {
            e.printStackTrace();
            authorized = false;
            fitBitAccesToken = null;
            fitBitAccesTokenSecret = null;
            callbackId = null;
            callBackUrl = null;
            oAuthToken = null;
        }

        return authorized;
    }

    public boolean isAuthorized()
    {
        return authorized;
    }

}
