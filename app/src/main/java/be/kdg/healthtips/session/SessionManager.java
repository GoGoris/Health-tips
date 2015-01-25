package be.kdg.healthtips.session;

import com.temboo.core.TembooException;
import com.temboo.core.TembooSession;

/**
 * Created by Mathi on 24/01/2015.
 */
public class SessionManager {

    private static TembooSession session;
    private static final String TEMBOO_KEY = "a9b42b0580914523ba9c18fae3895b21";
    private static final String TEMBOO_USER = "stevengoris";
    private static final String TEMBOO_APP = "myFirstApp";


    public static TembooSession getSession() throws TembooException {
        if(session == null)
        {
            session = new TembooSession(TEMBOO_USER, TEMBOO_APP , TEMBOO_KEY);
        }
        return session;
    }
}
