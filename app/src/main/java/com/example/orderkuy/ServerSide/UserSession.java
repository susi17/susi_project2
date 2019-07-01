package com.example.orderkuy.ServerSide;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class UserSession {

//    private final static UserSession instance;

    static {
//        instance = new UserSession();
    }

//    public static UserSession getInstance() {
//        return instance;
//    }

    private final SharedPreferences appPreference;

    public UserSession(Context context) {
        appPreference = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setSession(String key, String value) {
        SharedPreferences.Editor editor = appPreference.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void clearSession() {
        SharedPreferences.Editor editor = appPreference.edit();
        editor.clear();
        editor.apply();
        editor.commit();
    }

    public String getSession(String key) {
        String value = appPreference.getString(key, "");
        if (TextUtils.isEmpty(value)) {
            return "";
        }
        return value;
    }

    public void setIsLogin(boolean stat) {
        SharedPreferences.Editor editor = appPreference.edit();
        editor.putBoolean("IS_LOGIN", stat);
        editor.apply();
    }

    public boolean getIsLogin() {
        boolean isLogin = appPreference.getBoolean("IS_LOGIN", false);
        return isLogin;
    }

    public void setIdUser(String id_user) {
        setSession("id_user",id_user);
    }
    public String getIdUser(){
        return getSession("id_user");
    }
    public  void setUsernameUser(String username_user){
        setSession("username_user",username_user);
    }
    public  String getUsernameUser(){
        return getSession("username_user");
    }


}