package com.example.orderkuy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.orderkuy.ServerSide.UserSession;
import com.example.orderkuy.ServerSide.VolleyHandler;
import com.example.orderkuy.activity.CartActivity;
import com.example.orderkuy.adapter.CartAdapter;
import com.example.orderkuy.adapter.CheckoutAdapter;
import com.example.orderkuy.model.Keranjang_Model;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Checkout extends AppCompatActivity {

    private String id_keranjang;
    private String foto;
    private String id_produk;
    private String id_user;
    private String foto_keranjang;
    private String nama_produk;
    private String harga;
    private String stok_produk;
    private String quantity;

    private  String id_menu;
    private String nama_menu;
    private  String gambar;
    private String deskripsi_order;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    private JsonArrayRequest Request;
    private RequestQueue requestQueue;
    private List<Keranjang_Model> listStruk = new ArrayList<>();
    private CheckoutAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);




        listStruk.clear();
        addStrukItemList();
        recyclerView = (RecyclerView) findViewById(R.id.rv_checkout );
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(Checkout.this);
        myAdapter = new CheckoutAdapter(listStruk);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);


    }

    private void addStrukItemList(){
//        swipeRefreshLayout.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, com.example.orderkuy.ServerSide.URL.get_keranjang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 200){
                        JSONArray keranjang = jsonObject.getJSONArray("data");
                        listStruk.clear();
                        for (int i=0; i<keranjang.length(); i++){
                            JSONObject object=keranjang.getJSONObject(i);
                            id_keranjang=object.getString("id_keranjang");
                            foto= com.example.orderkuy.ServerSide.URL.rootImage+object.getString("gambar");
                            nama_produk=object.getString("nama_menu");
                            harga=object.getString("harga");
                            stok_produk=object.getString("stok_produk");
                            quantity=object.getString("quantity");
                            listStruk.add(new Keranjang_Model(id_keranjang,foto,quantity,nama_produk,harga));
                            layoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(layoutManager);
                            myAdapter= new CheckoutAdapter(listStruk);
                            recyclerView.setAdapter(myAdapter);
                            myAdapter.notifyDataSetChanged();


                        }
                    } else {
                        MDToast.makeText(getApplicationContext(),jsonObject.getString("message"), Toast.LENGTH_LONG,MDToast.TYPE_WARNING).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                swipeRefreshLayout.setRefreshing(false);
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error != null){
                    VolleyHandler.handleVolleyError(Checkout.this,error);
//                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }) {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                UserSession userSession = new UserSession(getApplicationContext());
                String kd_user = userSession.getIdUser();

                Map<String, String> params = new HashMap<>();
                params.put("id_user",kd_user);

                return params;
            }
        };

        Volley.newRequestQueue(Checkout.this).add(stringRequest);


    }
}
