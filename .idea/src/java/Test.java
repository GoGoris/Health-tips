import android.app.Activity;
import android.content.Context;

import io.oauth.OAuth;

/**
 * Created by school on 20/1/2015.
 */
public class Test extends Activity {
    public OAuth oauth;

    public Test() {
        oauth = new OAuth(getApplicationContext());
    }
}
