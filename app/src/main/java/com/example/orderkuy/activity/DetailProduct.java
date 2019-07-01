package com.example.orderkuy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.orderkuy.Order;
import com.example.orderkuy.R;
import com.example.orderkuy.ServerSide.URL;
import com.example.orderkuy.SimpleScannerActivity;
import com.example.orderkuy.adapter.OrderAdapter;
import com.example.orderkuy.adapter.RecyclerViewAdapter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class DetailProduct extends AppCompatActivity {

    public static final String KEY_JUDUL = "Product.JUDUL";
    public static final String KEY_HARGA = "Product.HARGA";
    public static final String KEY_DESKRIPSI = "Product.DESKRIPSI";
    public static final String KEY_IMG = "Product.IMG";
    public static final String KEY_STOK_PRODUK = "Product.STOK_PRODUK";

    private TextView Txt_judul,Txt_harga,Txt_deskripsi,stok;
    private ImageView Gbr_product;
    private Button Btn_Add;
    private FloatingActionButton Btn_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        Txt_judul = (TextView) findViewById(R.id.judul);
        Txt_harga = (TextView) findViewById(R.id.harga);
        Txt_deskripsi = (TextView) findViewById(R.id.deskripsi);
        Gbr_product = (ImageView) findViewById(R.id.detail_gambar);
        Btn_Add = (Button) findViewById(R.id.mulai_order);
        Btn_cart = (FloatingActionButton) findViewById(R.id.btnCart);
        stok = (TextView) findViewById(R.id.stok);

//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        final String value = sharedPreferences.getString(RecyclerViewAdapter.KEY_SHARED,null);
        final Intent intent = getIntent();
        final String judul_product = intent.getExtras().getString("KEY_JUDUL");
        final String harga_product = String.valueOf(intent.getExtras().getInt("KEY_HARGA"));
        final String deskripsi_product = intent.getExtras().getString("KEY_DESKRIPSI");
        final String gambar_product = intent.getExtras().getString("KEY_IMG");
        final String stok_produk = intent.getExtras().getString("KEY_STOK_PRODUK");

        Glide.with(getApplicationContext())
                .load(gambar_product)
                .into(Gbr_product);
        Txt_judul.setText(judul_product);
//        DecimalFormat kursIdr = (DecimalFormat) DecimalFormat.getCurrencyInstance();
//        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
//
//        formatRp.setCurrencySymbol("Rp.");
//        formatRp.setMonetaryDecimalSeparator(',');
//        kursIdr.setDecimalFormatSymbols(formatRp);
//        Txt_harga.setText(kursIdr.format(Double.valueOf(harga_product)));
        Txt_harga.setText("Rp. " + harga_product);

        Txt_deskripsi.setText("Deskripsi :  "+"\n"+deskripsi_product);
        stok.setText("Stok Item : " + stok_produk);

        Btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = sharedPreferences.edit();

                edit.putString(KEY_JUDUL, judul_product);
                edit.putString(KEY_HARGA, harga_product);
                edit.putString(KEY_DESKRIPSI, deskripsi_product);
                edit.putString(KEY_IMG, gambar_product);
                edit.putString(KEY_STOK_PRODUK, stok_produk);
                edit.apply();

                Intent beli = new Intent(DetailProduct.this, SimpleScannerActivity.class);
                startActivity(beli);
                finish();


            }
        });

        Btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailProduct.this,CartActivity.class));
            }
        });
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 1){
//            if (resultCode == RESULT_OK){
//                String id_produk = data.getStringExtra("id_menu");
//                String nama = data.getStringExtra("nama_menu");
//                String harga = data.getStringExtra("harga");
//                String foto = URL.rootImage+ data.getStringExtra("gambar");
//                String deskripsi = data.getStringExtra("deskripsi");
//                String stok_produk = data.getStringExtra("stok_produk");
//                Txt_judul.setText(nama);
//                Txt_harga.setText(harga);
//                Txt_deskripsi.setText(deskripsi);
//                stok.setText(stok_produk);
//                Glide.with(getApplicationContext())
//                        .load(foto)
//                        .into(Gbr_product);
//
//            }
//        }
//    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

}
