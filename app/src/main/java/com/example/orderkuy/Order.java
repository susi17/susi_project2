package com.example.orderkuy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.orderkuy.ServerSide.URL;
import com.example.orderkuy.activity.CartActivity;
import com.example.orderkuy.adapter.OrderAdapter;

import com.example.orderkuy.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Order extends AppCompatActivity  {

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter adapter;
    private String URL_JSON = URL.get_produk;  //passing data dari MYSQL
    private JsonArrayRequest Request ;
    private RequestQueue requestQueue ;
    private ArrayList<Product> listProduct = new ArrayList<>();
    private static int cart_count=0;


    @BindView(R.id.rv) RecyclerView myrv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ButterKnife.bind(this); //Instance ButteKnife

        myrv.setHasFixedSize(true);
        myrv.setLayoutManager(mLayoutManager);
        adapter = new OrderAdapter(this,null);
        myrv.setAdapter(adapter);

        jsoncall();

    }


    private void jsoncall() {

        Request = new JsonArrayRequest(URL_JSON, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;
//                JSONObject jsonObject = new JSONObject();


                for (int i = 0 ; i<response.length();i++) {


                    //Toast.makeText(getApplicationContext(),String.valueOf(i),Toast.LENGTH_SHORT).show();

                    try {

                        jsonObject = response.getJSONObject(i);
                        Product product = new Product();

                        Intent intent = getIntent();
                        product.setNama_menu(intent.getStringExtra("nama_menu"));
                        product.setHarga(Integer.parseInt((intent.getStringExtra("harga"))));
                        product.setGambar(URL.rootImage+intent.getStringExtra("gambar"));
//                        product.setDeskripsi(intent.getStringExtra("deskripsi"));
                        product.setStok_produk(intent.getStringExtra("stok_produk"));
                        listProduct.add(product);
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                setRvadapter(listProduct);
            }

            private void setRvadapter(ArrayList<Product> lst) {

                OrderAdapter myAdapter = new OrderAdapter(Order.this,lst) ;
                myrv.setHasFixedSize(true);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(Order.this,2);
                myrv.setLayoutManager(gridLayoutManager);
                myrv.setAdapter(myAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(Request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        menu.findItem(R.id.miCompose);
//        LayerDrawable icon = (LayerDrawable) item.getIcon();
//
//        // Update LayerDrawable's BadgeDrawable
//        Utils.setBadgeCount(this, icon, mNotificationsCount);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.miCompose :
                startActivity(new Intent(Order.this, CartActivity.class));
                break;
        }
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
       startActivity(new Intent(Order.this, MainActivity.class));
    }

//    @Override
//    public void onAddProduct() {
//        cart_count++;
//        invalidateOptionsMenu();
//        Snackbar.make((RelativeLayout)findViewById(R.id.parentlayout), "Added to cart !!", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
//
//
//    }
//
//    @Override
//    public void onRemoveProduct() {
//        cart_count--;
//        invalidateOptionsMenu();
//        Snackbar.make((RelativeLayout)findViewById(R.id.parentlayout), "Removed from cart !!", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
//
//
//    }
}

