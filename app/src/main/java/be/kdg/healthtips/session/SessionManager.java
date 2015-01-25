package be.kdg.healthtips.session;

import com.temboo.core.TembooException;
import com.temboo.core.TembooSession;

/**
 * Created by Mathi on 24/01/2015.
 */
public class SessionManager {

    private static TembooSession session;
    private static final String TEMBOO_KEY = "b77317ddffe24054b22b829755498178";
    private static final String TEMBOO_USER = "mathiasmariman";
    private static final String TEMBOO_APP = "myFirstApp";


    public static TembooSession getSession() throws TembooException {
        if(session == null)
        {
            session = new TembooSession(TEMBOO_USER, TEMBOO_APP , TEMBOO_KEY);
        }
        return session;
    }
}
