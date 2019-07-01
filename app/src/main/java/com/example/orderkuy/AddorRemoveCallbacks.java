package com.example.orderkuy;

import android.content.Loader;
import android.database.Cursor;

/**
 * Created by chintu gandhwani on 1/22/2018.
 */

public interface AddorRemoveCallbacks {

    void onLoadFinished(Loader<Cursor> loader, Cursor data);

    void onLoaderReset(Loader<Cursor> loader);

    public void onAddProduct();
    public void onRemoveProduct();
}
