package com.project.pp.parentparadise.utl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.project.pp.parentparadise.ppmain.Member;

public class SessionManager {
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    private Editor editor;
    private Context _context;

    // Shared pref mode
    private int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "PP";
    private static final String KEY_IS_LOGGED_IN = "isLogin";
    private static final String LOGGED_IN_MEMBER_NO = "loginMemberNo";
    private static final String LOGGED_IN_ACCOUNT_STR = "account";
    private static final String LOGGED_IN_PASSWORD = "password";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void clear(){
        editor.clear();
        editor.commit();
    }

    public void setLogin(boolean isLogin) {

        editor.putBoolean(KEY_IS_LOGGED_IN, isLogin);

        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public void setLogin(boolean isLogin, String mail, String password, int mbNo) {

        editor.putBoolean(KEY_IS_LOGGED_IN, isLogin);
        editor.putString(LOGGED_IN_ACCOUNT_STR, mail);

        // logged-in ok, save user info;
        if (isLogin) {
            editor.putInt(LOGGED_IN_MEMBER_NO, mbNo);
            editor.putString(LOGGED_IN_PASSWORD, password);
        } else {
            editor.putInt(LOGGED_IN_MEMBER_NO, -1);
            editor.putString(LOGGED_IN_PASSWORD, "");

        }

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public Member getLoginMemberInfo() {
        Member member = new Member(pref.getInt(LOGGED_IN_MEMBER_NO, -1));
        return member;
    }
}
