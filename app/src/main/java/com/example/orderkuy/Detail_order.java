package com.example.orderkuy;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.orderkuy.ServerSide.URL;
import com.example.orderkuy.ServerSide.UserSession;
import com.example.orderkuy.ServerSide.VolleyHandler;
import com.example.orderkuy.activity.CartActivity;
import com.example.orderkuy.activity.Login;
import com.example.orderkuy.model.Product;
import com.valdesekamdem.library.mdtoast.MDToast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;
import static java.lang.Integer.parseInt;

public class Detail_order extends AppCompatActivity {


    public static final String KEY_JUDUL = "Product.JUDUL";
    public static final String KEY_HARGA = "Product.HARGA";
    public static final String KEY_DESKRIPSI = "Product.DESKRIPSI";
    public static final String KEY_IMG = "Product.IMG";
    public static final String BIAYA = "BIAYA";
    public static final String KEY_JUMLAH_ITEM = "Product.JUMLAH_ITEM";

    private TextView Txt_judul,Txt_harga,Txt_deskripsi,stok,Total_harga_item;
    private ImageView Gbr_product;
    private Button Btn_Add;
    private FloatingActionButton Btn_cart;

    private TextView quantityTextView, costTextView;
    private String jum, bia;
    int quantity=1, juml=0;

    private  String id_menu;
    private String nama_menu;
    private  String gambar;
    private String deskripsi_order;
    private  String stok_produk;



    RequestQueue requestQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);




        Txt_judul = (TextView) findViewById(R.id.nama);
        Txt_harga = (TextView) findViewById(R.id.harga);
        Txt_deskripsi = (TextView) findViewById(R.id.deskripsi);
        Gbr_product = (ImageView) findViewById(R.id.detail_gambar);
        Btn_Add = (Button) findViewById(R.id.add_cart);
        Btn_cart = (FloatingActionButton) findViewById(R.id.btnCart);
        Total_harga_item = (TextView) findViewById(R.id.total_harga_item);
        stok = (TextView) findViewById(R.id.stok);


//        final Intent intent = getIntent();
//        final String id_menu = intent.getExtras().getString("KEY_ID_MENU");
//        final String nama_produk = intent.getExtras().getString("KEY_JUDUL");
//        String harga_produk = String.valueOf(intent.getExtras().getInt("KEY_HARGA"));
//        final String deskripsi_product = intent.getExtras().getString("KEY_DESKRIPSI");
//        final String gambar_product = intent.getExtras().getString("KEY_IMG");
//        final String stok_produk = intent.getExtras().getString("KEY_STOK_PRODUK");

        Intent intent = getIntent();
        id_menu = String.valueOf(intent.getExtras().getInt("id_menu"));
        nama_menu = intent.getStringExtra("nama_menu");
        String harga_produk = String.valueOf(intent.getExtras().getInt("harga"));
        gambar = intent.getStringExtra("gambar");
        deskripsi_order = intent.getStringExtra("deskripsi_order");
        stok_produk = intent.getStringExtra("stok_produk");
//        quantity = Integer.parseInt(String.valueOf(intent.getExtras().getInt("quantity")));


        Txt_judul.setText(nama_menu);
        Txt_harga.setText(harga_produk);
        Txt_deskripsi.setText(deskripsi_order);
        stok.setText(stok_produk);
        Glide.with(getApplicationContext())
                .load(gambar)
                .into(Gbr_product);

        harga_produk = harga_produk.replace("","");
        juml= parseInt(harga_produk);
        displayPrice(juml);

        DecimalFormat kursIdr = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp.");
        formatRp.setMonetaryDecimalSeparator(',');
        kursIdr.setDecimalFormatSymbols(formatRp);
        Txt_harga.setText(kursIdr.format(Long.valueOf(harga_produk)));
        Txt_deskripsi.setText("Deskripsi :  "+"\n"+ deskripsi_order);
        stok.setText("Stok Item : " + stok_produk);

        display(quantity);


        requestQueue = Volley.newRequestQueue(getApplicationContext());

        Btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addKeranjang();
                MDToast.makeText(getApplicationContext(),"Hore! Berhasil menambahkan ke Keranjang", Toast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();
                finish();

            }
        });

        Btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Detail_order.this, CartActivity.class));
            }
        });

    }



    private void addKeranjang(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.save_keranjang, new Response.Listener<String>() {
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
                        product.setGambar(URL.rootImage+jsonObject.getString("gambar"));



                        Intent intent = new Intent(Detail_order.this, MainActivity.class);
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
                params.put("gambar",gambar);


                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    public void increment(View view){//perintah tombol tambah
//        if(quantity==Integer.parseInt(kuota)){
//            Toast.makeText(this,"pesanan maximal "+kuota,Toast.LENGTH_SHORT).show();
//            return;
//        }
        quantity = quantity +1;
        display(quantity);
        displayPrice(juml*quantity);


    }
    public void decrement(View view){//perintah tombol kurang
        if (quantity==1){
            MDToast.makeText(this,"Pesanan minimal 1",Toast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();
            return;
        }
        quantity = quantity -1;
        display(quantity);
        displayPrice(juml * quantity);

    }

    private void display(int number) {
        quantityTextView = (TextView) findViewById(R.id.quantity_textview);
        quantityTextView.setText("" + number);
    }

    private void displayPrice(int number) {
        costTextView = (TextView) findViewById(R.id.total_harga_item);
        costTextView.setText("Total : "+NumberFormat.getCurrencyInstance().format(number));
    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

}
