package be.kdg.healthtips.session;

import com.temboo.core.TembooException;
import com.temboo.core.TembooSession;

/**
 * This class is responsible for the TembooSession.
 * It instantiate a {@link com.temboo.core.TembooSession} with the right accountinformation and makes sure that all classes use the same TembooSession
 */
public class TembooSessionManager {
    private static TembooSession session;
    private static final String TEMBOO_KEY = "806edeec6d6a4af5841abdbb9024b73d";
    private static final String TEMBOO_USER = "mathimarimanstudent";
    private static final String TEMBOO_APP = "myFirstApp";

    public static TembooSession getSession() throws TembooException {
        if(session == null) session = new TembooSession(TEMBOO_USER, TEMBOO_APP , TEMBOO_KEY);
        return session;
    }
}
