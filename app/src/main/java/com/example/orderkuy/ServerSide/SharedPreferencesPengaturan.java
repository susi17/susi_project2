package com.example.orderkuy.ServerSide;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class SharedPreferencesPengaturan {

//    private final static UserSession instance;

    static {
//        instance = new UserSession();
    }

//    public static UserSession getInstance() {
//        return instance;
//    }

    private final SharedPreferences appPreference;

    public SharedPreferencesPengaturan(Context context) {
        appPreference = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void simpanPengaturan(String key, String value) {
        SharedPreferences.Editor editor = appPreference.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void clearPengaturan() {
        SharedPreferences.Editor editor = appPreference.edit();
        editor.clear();
        editor.apply();
        editor.commit();
    }

    public String getPengaturan(String key) {
        String value = appPreference.getString(key, "");
        if (TextUtils.isEmpty(value)) {
            return "";
        }
        return value;
    }

    public void setSession(boolean stat) {
        SharedPreferences.Editor editor = appPreference.edit();
        editor.putBoolean("IS_LOGIN", stat);
        editor.apply();
    }

    public boolean getIsSession() {
        boolean isLogin = appPreference.getBoolean("IS_LOGIN", false);
        return isLogin;
    }
    public void setNamaPrinter(String namaPrinter){
        simpanPengaturan("namaPrinter",namaPrinter);
    }
    public String getNamaPrinter(){
        return  getPengaturan("namaPrinter");
    }
   public void setNamaToko(String namaToko){
        simpanPengaturan("namaToko",namaToko);
   }
   public String getNamaToko(){
        return  getPengaturan("namaToko");
   }
   public  void setNamaPemilikToko(String namaPemilikToko){
        simpanPengaturan("namaPemilikToko",namaPemilikToko);
   }
   public String getNamaPemilikToko(){
        return getPengaturan("namaPemilikToko");
   }
   public void setAlamatToko(String alamatToko){
        simpanPengaturan("alamatToko",alamatToko);
   }
   public String getAlamatToko(){
        return getPengaturan("alamatToko");
   }



}