package com.example.orderkuy.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.orderkuy.Detail_order;
import com.example.orderkuy.activity.DetailProduct;
import com.example.orderkuy.R;
import com.example.orderkuy.model.Product;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aws on 28/01/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {



    public static final String KEY_ID_MENU = "Product.ID_MENU";
    public static final String KEY_JUDUL = "Product.JUDUL";
    public static final String KEY_HARGA = "Product.HARGA";
    public static final String KEY_DESKRIPSI = "Product.DESKRIPSI";
    public static final String KEY_IMG = "Product.IMG";
    public static final String KEY_STOK_PRODUK = "Product.STOK_PRODUK";
    public static final String KEY_ID_KERANJANG = "Keranjang_Model.ID_KERANJANG";



    private Context mContext ;
    private ArrayList<Product> mData ;
    private RequestOptions options ;


    public RecyclerViewAdapter(Context mContext, ArrayList<Product> list) {
        this.mContext = mContext;
        this.mData = list;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.loading_shape)
                .error(R.drawable.loading_shape );

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.product_list_item,parent,false);
        // click listener here
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.tvname.setText(mData.get(position).getNama_menu());
//        holder.tv_deskripsi.setText(mData.get(position).getDeskripsi());
        holder.tv_harga.setText(String.valueOf(mData.get(position).getHarga()));
        holder.tvStokProduk.setText(mData.get(position).getStok_produk());

        // load image from the internet using Glide
        Glide.with(mContext)
                .load(mData.get(position).getGambar())
                .apply(options)
                .into(holder.AnimeThumbnail);

        holder.AnimeThumbnail.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(holder.tvname.getContext());
//                SharedPreferences.Editor edit = sharedPreferences.edit();
//                edit.putString(KEY_JUDUL, mData.get(position).getNama());
//                edit.putString(KEY_HARGA, String.valueOf(mData.get(position).getHarga()));
//                edit.putString(KEY_DESKRIPSI, mData.get(position).getDeskripsi());
//                edit.putString(KEY_IMG, mData.get(position).getFoto());
//                edit.putString(KEY_STOK_PRODUK, mData.get(position).getJumlah_item());
//                edit.apply();
//
//
//                v.getContext().startActivity(new Intent(v.getContext(), DetailProduct.class));
                Intent intent = new Intent(mContext,Detail_order.class);
                intent.putExtra("id_menu",mData.get(position).getId_menu());
                intent.putExtra("nama_menu",mData.get(position).getNama_menu());
                intent.putExtra("harga",mData.get(position).getHarga());
                intent.putExtra("deskripsi_order",mData.get(position).getDeskripsi());
                intent.putExtra("gambar",mData.get(position).getGambar());
                intent.putExtra("stok_produk",mData.get(position).getStok_produk());

                mContext.startActivity(intent);



            }
        });





    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_name)
        TextView tvname;

        @BindView(R.id.harga)
        TextView tv_harga;

        @BindView(R.id.thumbnail)
        ImageView  AnimeThumbnail;

        @BindView(R.id.stok_produk) TextView tvStokProduk;


        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView); // Instane ButterKnife
        }
    }


}