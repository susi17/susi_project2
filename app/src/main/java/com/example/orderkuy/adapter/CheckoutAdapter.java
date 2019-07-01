package com.example.orderkuy.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.example.orderkuy.R;
import com.example.orderkuy.model.Keranjang_Model;

import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.MyViewHolder> {

    private Context mContext ;
    private TextView quantityTextView;
    private List<Keranjang_Model> carttArrayList;
    private RequestOptions requestOptions;
    int quantity=1, juml=0;
    int jum=0;


    public CheckoutAdapter(List<Keranjang_Model> listKeranjang) {
        this.carttArrayList = listKeranjang;
        requestOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.loading_shape)
                .error(R.drawable.loading_shape );
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_checkout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.tvname.setText(carttArrayList.get(i).getNama_produk());
        myViewHolder.tv_harga.setText(carttArrayList.get(i).getHarga());
        myViewHolder.tv_qty.setText(carttArrayList.get(i).getQuantity());
//===================================================================
        //membuat total_harga dari keranjang
        if (carttArrayList.isEmpty()){
            jum = 0;
        } else {
            jum = 0;
            for (int j=0; j<carttArrayList.size();j++){
                int harga=Integer.parseInt(String.valueOf(carttArrayList.get(j).getHarga()));
                int beli=Integer.parseInt(carttArrayList.get(j).getQuantity());
                jum=jum+(harga*beli);
            }
        }

        //Broadcast data ke activity
        Intent intent = new Intent("broadcast");
        intent.putExtra("jumlah_total",String.valueOf(jum));
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

    }


    @Override
    public int getItemCount() {
        return carttArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        SwipeRefreshLayout swipeRefreshLayout;
        TextView tvname,tv_harga,tv_qty, tv_price;
        ImageView iv_img, tv_delete;
        ImageButton plus,minus;
        private  String id_keranjang;
        private String foto;
        private  String id_produk;
        private  String deskripsi_order;
        private String nm_produk;
        int jumlah=0;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            swipeRefreshLayout = itemView.findViewById(R.id.refresh);
            tvname = itemView.findViewById(R.id.item);
            tv_harga = itemView.findViewById(R.id.harga_item);
            tv_qty = itemView.findViewById(R.id.qty_item);
//            iv_img = itemView.findViewById(R.id.thumbnail);
//            tv_delete = itemView.findViewById(R.id.hapus);
            tv_price = itemView.findViewById(R.id.total);
//            plus = itemView.findViewById(R.id.plus);
//            minus = itemView.findViewById(R.id.minus);

        }
    }

}
