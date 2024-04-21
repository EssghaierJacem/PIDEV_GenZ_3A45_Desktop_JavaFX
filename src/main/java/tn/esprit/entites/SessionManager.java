package tn.esprit.entites;

import java.util.HashMap;
import java.util.UUID;

public class SessionManager {
    private static final HashMap<String, User> sessionStore = new HashMap<>();
    private static String currentSessionId;

    /**
     * Creates a new session for the given user and returns the session ID.
     *
     * @param user The user to create a session for.
     * @return The session ID for the newly created session.
     */
    public static String createSession(User user) {
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, user);
        currentSessionId = sessionId;
        return sessionId;
    }
    /**
     * Retrieves the user object associated with the given session ID.
     *
     * @param sessionId The session ID.
     * @return The user object associated with the session ID, or null if the session does not exist.
     */
    public static User getUserFromSession(String sessionId) {
        return sessionStore.get(sessionId);
    }

    public static void terminateSession(String sessionId) {
        sessionStore.remove(sessionId);
    }
    /**
     * Retrieves the current session ID.
     *
     * @return The current session ID, or null if no session is active.
     */
    public static String getCurrentSessionId() {
        return currentSessionId;
    }
}
