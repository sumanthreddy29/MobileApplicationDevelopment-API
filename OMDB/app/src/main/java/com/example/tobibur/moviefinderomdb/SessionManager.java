package com.example.tobibur.moviefinderomdb;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "MovieFinder";

    // All Shared Preferences Keys
    private static final String IS_RESULT_IN = "IsResultIn";


    // Email address (make variable public to access from outside)
    public static final String KEY_RESULT = "result";

//    //pass
//    public static final String KEY_MOVIE_NAME = "movie_name";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createMovieSession(String username) {
        // Storing login value as TRUE
        editor.putBoolean(IS_RESULT_IN, true);

        // Storing username & pass in pref
        editor.putString(KEY_RESULT, username);
//        editor.putString(KEY_MOVIE_NAME, pass);

        editor.commit();
    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();

        // user name
        user.put(KEY_RESULT, pref.getString(KEY_RESULT, null));

//        // user name
//        user.put(KEY_MOVIE_NAME, pref.getString(KEY_MOVIE_NAME, null));
        // return user
        return user;
    }



    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_RESULT_IN, false);
    }
}
