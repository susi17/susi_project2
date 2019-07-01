package com.example.orderkuy.ServerSide;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;


/**
 * Created by rahmad on 07/02/17.
 */

public class VolleyHandler {

    public static void handleVolleyError(Context context, VolleyError error) {

        //Error message volley
        String messageNetworkError      = "Tidak terhubung dengan Server";
        String messageServerError       = "Server tidak dapat ditemukan , mohon coba beberapa saat lagi";
        String messageAuthFailureError  = "Tidak dapat terhubung ke Internet, Mohon cek Koneksi anda!!";
        String messageParseError        = "Parsing failed! Please try again after some time";
        String messageTimeoutError      = "Waktu Koneksi habis, cek koneksi internet anda";

        String message = "";

        if (error instanceof NetworkError) {
            message = messageNetworkError;
        } else if (error instanceof ServerError) {
            message = messageServerError;
        } else if (error instanceof AuthFailureError) {
            message = messageAuthFailureError;
        } else if (error instanceof ParseError) {
            message = messageParseError;
        } else if (error instanceof TimeoutError) {
            message = messageTimeoutError;
        }

        if (context != null && !TextUtils.isEmpty(message)) {
            Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
//            FancyToast.makeText(context,"" + message, FancyToast.LENGTH_SHORT, FancyToast.ERROR,false).show();
        }
    }

}