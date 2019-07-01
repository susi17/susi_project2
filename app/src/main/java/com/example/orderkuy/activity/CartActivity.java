package com.example.orderkuy.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.orderkuy.Checkout;
import com.example.orderkuy.Detail_order;
import com.example.orderkuy.MainActivity;
import com.example.orderkuy.R;
import com.example.orderkuy.ServerSide.URL;
import com.example.orderkuy.ServerSide.UserSession;
import com.example.orderkuy.ServerSide.VolleyHandler;
import com.example.orderkuy.adapter.CartAdapter;
import com.example.orderkuy.model.Keranjang_Model;
import com.example.orderkuy.model.Product;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public  class CartActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    private String id_keranjang;
    private String foto;
    private String id_produk;
    private String id_user;
    private String foto_keranjang;
    private String nama_produk;
    private String harga;
    private String stok_produk;
    private String quantity;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    private JsonArrayRequest Request;
    private RequestQueue requestQueue;
    private List<Keranjang_Model> listKeranjang = new ArrayList<>();
    private static int cart_count = 0;
    private TextView tx_total;
    private Button btnCheckout;
    private CartAdapter myAdapter;

    private TextView quantityTextView, priceTextView;
    private String jum, bia;
//    int quantity = 0, juml = 0;

    private  String id_menu;
    private String nama_menu;
    private  String gambar;
    private String deskripsi_order;


    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        tx_total = (TextView) findViewById(R.id.total);
        btnCheckout = (Button) findViewById(R.id.checkout);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);




        listKeranjang.clear();
        addCartItemList();
        recyclerView = (RecyclerView) findViewById(R.id.rv_cart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(CartActivity.this);
        myAdapter = new CartAdapter(listKeranjang);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);


        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                listKeranjang.clear();
                addCartItemList();
            }

        });


        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                checkout();
                MDToast.makeText(getApplicationContext(),"Hore! Silahkan tunggu pesanan anda!", Toast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();
//                Intent intent = new Intent(getApplicationContext(),Checkout.class);
//                startActivity(intent);
                finish();
            }
        });


        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("broadcast"));

    }

    private void checkout(){

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, URL.save_keranjang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

//                JSONObject jsonObject = null;
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Product product = new Product();

                    product.setId_menu(Integer.parseInt(jsonObject.getString("id_menu")));
//                        product.setNama(jsonObject.getString("id_user"));
                    product.setHarga(Integer.parseInt(jsonObject.getString("harga")));
//                        product.setDeskripsi(jsonObject.getString("deskripsi"));
//                        product.setStok_produk(jsonObject.getString("stok_produk"));
                    product.setQuantity(jsonObject.getString("quantity"));
//                    product.setGambar(URL.rootImage+jsonObject.getString("gambar"));



                    Intent intent = new Intent(CartActivity.this, Checkout.class);
                    startActivity(intent);





                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyHandler.handleVolleyError(getApplicationContext(),error);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                //sesuai field di database
                UserSession userSession = new UserSession(getApplicationContext());
                String id_user = userSession.getIdUser();
                params.put("id_menu",id_menu);
                params.put("id_user" , id_user);
//                params.put("nama_menu", nama_menu);
//                params.put("harga", harga);
////                params.put("deskripsi_order",deskripsi_order);
                params.put("quantity", String.valueOf(quantity));
//                params.put("gambar",gambar);


                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    //====================================================================
    //Menerima Broadcast "Jumlah_total" dari Adapter
    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String jumlah = intent.getStringExtra("jumlah_total");
            tx_total.setText("Rp. " + jumlah);
        }
    };
    //=====================================================================

    private void addCartItemList(){
        swipeRefreshLayout.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, com.example.orderkuy.ServerSide.URL.get_keranjang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 200){
                        JSONArray keranjang = jsonObject.getJSONArray("data");
                        listKeranjang.clear();
                        for (int i=0; i<keranjang.length(); i++){
                            JSONObject object=keranjang.getJSONObject(i);
                            id_keranjang=object.getString("id_keranjang");
                            foto= com.example.orderkuy.ServerSide.URL.rootImage+object.getString("gambar");
                            nama_produk=object.getString("nama_menu");
                            harga=object.getString("harga");
                            stok_produk=object.getString("stok_produk");
                            quantity=object.getString("quantity");
                            listKeranjang.add(new Keranjang_Model(id_keranjang,foto,quantity,nama_produk,harga));
                            layoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(layoutManager);
                            myAdapter= new CartAdapter(listKeranjang);
                            recyclerView.setAdapter(myAdapter);
                            myAdapter.notifyDataSetChanged();


                        }
                    } else {
                        MDToast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG,MDToast.TYPE_WARNING).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                swipeRefreshLayout.setRefreshing(false);
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error != null){
                    VolleyHandler.handleVolleyError(CartActivity.this,error);
                    swipeRefreshLayout.setRefreshing(false);
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

        Volley.newRequestQueue(CartActivity.this).add(stringRequest);


    }

    @Override
    public void onRefresh() {
        listKeranjang.clear();
        addCartItemList();
    }
}

