package com.example.orderkuy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.orderkuy.ServerSide.URL;
import com.example.orderkuy.ServerSide.VolleyHandler;
import com.example.orderkuy.activity.DetailProduct;
import com.google.zxing.Result;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class SimpleScannerActivity extends BaseScannerActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_simple_scanner);
//        setupToolbar();

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);


    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(this, "Contents = " + rawResult.getText() +
                ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();

       loadCariProduk(rawResult.getText());
       mScannerView.stopCamera();
    }

    private void loadCariProduk(final String cari){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.cari_produk_byScanner, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getInt("status") == 200) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject produkObject = jsonArray.getJSONObject(i);
                            String id_produk = produkObject.getString("id_menu");
                            String nama = produkObject.getString("nama_menu");
                            String harga = produkObject.getString("harga");
                            String deskripsi = produkObject.getString("deskripsi");
                            String foto = URL.rootImage+produkObject.getString("gambar");
                            String stok_produk = produkObject.getString("stok_produk");
                            Intent intent = new Intent(getApplicationContext(), Detail_order.class);

                            intent.putExtra("id_menu", id_produk);
                            intent.putExtra("nama_menu", nama);
                            intent.putExtra("harga", harga);
                            intent.putExtra("deskripsi", deskripsi);
                            intent.putExtra("gambar", foto);
                            intent.putExtra("stok_produk", stok_produk);

                            startActivity(intent);
                            finish();
                        }
                    } else {
                        MDToast mdToast = MDToast.makeText(getApplicationContext(), jsonObject.getString("message"), MDToast.TYPE_INFO);
                        mdToast.show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null){
                    VolleyHandler.handleVolleyError(getApplicationContext(),error);
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id_menu", cari);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
