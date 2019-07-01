package com.example.orderkuy.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.orderkuy.Detail_order;
import com.example.orderkuy.Order;
import com.example.orderkuy.R;
import com.example.orderkuy.ServerSide.URL;
import com.example.orderkuy.ServerSide.VolleyHandler;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class OrderFragment extends Fragment implements ZBarScannerView.ResultHandler  {

    private ZBarScannerView mScannerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ButterKnife.bind(getActivity());
        mScannerView = new ZBarScannerView(getActivity());

        return mScannerView;

    }



    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }


    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(getActivity(), "Contents = " + rawResult.getContents() +
                ", Format = " + rawResult.getBarcodeFormat().getName(), Toast.LENGTH_SHORT).show();

        loadCariProduk(rawResult.getContents());
        mScannerView.stopCamera();
    }

    private void loadCariProduk(final String cari){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.cari_produk_byScanner, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject();

                    if (jsonObject.getInt("status") == 200) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject produkObject = jsonArray.getJSONObject(i);
                            String id_produk = produkObject.getString("id_menu");
                            String nama = produkObject.getString("nama_menu");
                            String harga = produkObject.getString("harga");
                            String deskripsi = produkObject.getString("deskripsi");
                            String foto = produkObject.getString("gambar");
                            String stok_produk = produkObject.getString("stok_produk");
                            Intent intent = new Intent(getContext(), Order.class);

                            intent.putExtra("id_menu", id_produk);
                            intent.putExtra("nama_menu", nama);
                            intent.putExtra("harga", harga);
                            intent.putExtra("deskripsi", deskripsi);
                            intent.putExtra("gambar", foto);
                            intent.putExtra("stok_produk", stok_produk);

                            startActivity(intent);

                        }
                    } else {
                        MDToast mdToast = MDToast.makeText(getContext(), jsonObject.getString("message"), MDToast.TYPE_INFO);
                        mdToast.show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null){
                    VolleyHandler.handleVolleyError(getContext(),error);
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
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }
}
