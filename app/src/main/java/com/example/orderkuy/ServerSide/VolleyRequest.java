package com.example.orderkuy.ServerSide;
import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.Stetho;

/**
 * Created by rahmad on 07/02/17.
 */

public class VolleyRequest extends MultiDexApplication {

    public static final String TAG = VolleyRequest.class.getSimpleName();
    private static VolleyRequest mInstance;
    private RequestQueue mRequestQueue;

    public static synchronized VolleyRequest getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Stetho.initializeWithDefaults(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }


    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void cancelPendingRequests() {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
    }

}