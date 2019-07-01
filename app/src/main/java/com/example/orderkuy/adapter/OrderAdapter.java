package com.example.orderkuy.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.orderkuy.AddorRemoveCallbacks;
import com.example.orderkuy.Detail_order;
import com.example.orderkuy.R;
import com.example.orderkuy.activity.DetailProduct;
import com.example.orderkuy.model.Product;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder>  {



public static final String KEY_JUDUL = "Product.JUDUL";
public static final String KEY_HARGA = "Product.HARGA";
public static final String KEY_DESKRIPSI = "Product.DESKRIPSI";
public static final String KEY_IMG = "Product.IMG";
public static final String KEY_JUMLAH_ITEM = "Product.JUMLAH_ITEM";


public Cursor dataCursor;
public Context context;
    int id;
private ArrayList<Product> productArrayList;
private RequestOptions requestOptions;

public OrderAdapter(Context mContext,ArrayList<Product> list) {
        this.context = mContext;

        this.productArrayList = list;
        requestOptions = new RequestOptions()
        .centerCrop()
        .placeholder(R.drawable.loading_shape)
        .error(R.drawable.loading_shape );

        }

@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(context);
        view = mInflater.inflate(R.layout.list_order,parent,false);
        // click listener here
        return new MyViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.tvname.setText(productArrayList.get(position).getNama_menu());
        holder.tv_harga.setText(String.valueOf(productArrayList.get(position).getHarga()));
        holder.tvStokProduk.setText(productArrayList.get(position).getStok_produk());

        // load image from the internet using Glide
        Glide.with(context)
        .load(productArrayList.get(position).getGambar())
        .apply(requestOptions)
        .into(holder.AnimeThumbnail);

        holder.AnimeThumbnail.setOnClickListener( new View.OnClickListener() {
@Override
public void onClick(View v) {

    Intent intent = new Intent(context, Detail_order.class);
    intent.putExtra("KEY_JUDUL",productArrayList.get(position).getNama_menu());
    intent.putExtra("KEY_HARGA",productArrayList.get(position).getHarga());
    intent.putExtra("KEY_DESKRIPSI",productArrayList.get(position).getDeskripsi());
    intent.putExtra("KEY_IMG",productArrayList.get(position).getGambar());
    intent.putExtra("KEY_STOK_PRODUK",productArrayList.get(position).getStok_produk());
    intent.putExtra("KEY_QUANTITY",productArrayList.get(position).getQuantity());

    context.startActivity(intent);

        }
        });


        }


@Override
public int getItemCount() {
        return productArrayList.size();

//    return (dataCursor == null) ? 0 : dataCursor.getCount();
        }

class MyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.product_name)
    TextView tvname;
    @BindView(R.id.harga)
    TextView tv_harga;
    @BindView(R.id.thumbnail)
    ImageView AnimeThumbnail;
    @BindView(R.id.stok_produk) TextView tvStokProduk;



    MyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView); // Instane ButterKnife

    }
}
}