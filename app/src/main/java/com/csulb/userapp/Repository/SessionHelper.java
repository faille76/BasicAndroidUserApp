package com.csulb.userapp.Repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.csulb.userapp.Entity.User;

public class SessionHelper {

    /**
     * The user.
     */
    private User user;

    /**
     * Contexte
     */
    private Context context;

    /**
     * Shared preferences to keep session token.
     */
    private SharedPreferences sharedPreferences;

    /**
     * Instance of session.
     */
    private static SessionHelper sInstance;

    /**
     * Singleton with an user.
     * Create or update the instance.
     *
     * @param user the user.
     * @return the SessionHelper.
     */
    public static synchronized SessionHelper getInstance(Context context, User user) {
        if (sInstance == null) {
            sInstance = new SessionHelper(context, user);
        } else {
            sInstance.setUser(user);
        }
        return sInstance;
    }

    /**
     * Singleton without user.
     *
     * @return the SessionHelper
     */
    public static synchronized SessionHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SessionHelper(context, null);
        }
        return sInstance;
    }

    /**
     * Construction with an user.
     *
     * @param user the user.
     */
    private SessionHelper(Context context, User user) {
        this.context = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        this.setUser(user);
    }


    /**
     * Set the user.
     *
     * @param user the user.
     */
    public void setUser(User user) {
        if (user == null) {
            String session_email_token = this.sharedPreferences.getString("session_email_token","");
            if (session_email_token != null && !session_email_token.equals("")) {
                UserFileRepository userRepository = UserFileRepository.getInstance(this.context);
                this.user = userRepository.getUserByEmail(session_email_token);
            }
        } else {
            this.sharedPreferences.edit().putString("session_email_token", user.email).apply();
            this.user = user;
        }
    }

    /**
     * Destroy session with token.
     */
    public void destroySession() {
        this.sharedPreferences.edit().remove("session_email_token").apply();
        this.user = null;
    }

    /**
     * Get the user.
     *
     * @return the user.
     */
    public User getUser() {
        return user;
    }
}
