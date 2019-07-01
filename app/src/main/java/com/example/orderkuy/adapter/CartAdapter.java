package com.example.orderkuy.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.orderkuy.R;
import com.example.orderkuy.ServerSide.URL;
import com.example.orderkuy.ServerSide.UserSession;
import com.example.orderkuy.ServerSide.VolleyHandler;
import com.example.orderkuy.activity.CartActivity;
import com.example.orderkuy.model.Keranjang_Model;
import com.example.orderkuy.model.Product;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private Context mContext ;
    private TextView quantityTextView;
    private List<Keranjang_Model> carttArrayList;
    private RequestOptions requestOptions;
    int quantity=1, juml=0;
    int jum=0;


    public CartAdapter(List<Keranjang_Model> listKeranjang) {
        this.carttArrayList = listKeranjang;
        requestOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.loading_shape)
                .error(R.drawable.loading_shape );
    }


    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cart, parent, false);
        // click listener here
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.MyViewHolder viewHolder, final int position) {

        viewHolder.display(quantity);


        viewHolder.tvname.setText(carttArrayList.get(position).getNama_produk());
        viewHolder.tv_harga.setText("Rp."+carttArrayList.get(position).getHarga());


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

        //====================================================================


        String jumlahBeli=carttArrayList.get(position).getQuantity();
        final String id_keranjang= String.valueOf(carttArrayList.get(position).getId_keranjang());
        viewHolder.tv_qty.setText(jumlahBeli);


        // load image from the internet using Glide
        Glide.with(viewHolder.iv_img.getContext())
                .load(carttArrayList.get(position).getFoto())
                .apply(requestOptions)
                .into(viewHolder.iv_img);


        //=====================================================================
        //Membuat Auto Update Ketika Dihapus
        viewHolder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (!carttArrayList.isEmpty()){
                   viewHolder.hapusKeranjang(id_keranjang);
                   carttArrayList.remove(position);
                   jum=0;
                   notifyItemRemoved(position);
                   notifyItemChanged(position,carttArrayList.size());
                   for (int i=0;i<carttArrayList.size();i++){
                       int harga=Integer.parseInt(String.valueOf(carttArrayList.get(i).getHarga()));
                       int beli=Integer.parseInt(carttArrayList.get(i).getQuantity());
                       jum=jum+(harga*beli);
                   }

                   Intent intent = new Intent("broadcast");
                   intent.putExtra("jumlah_total",String.valueOf(jum));
                   LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
               }
            }
        });
        //=========================================================================


        viewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = Integer.parseInt(viewHolder.tv_qty.getText().toString());
                quantity = quantity +1;
                viewHolder.display(quantity);
                carttArrayList.get(position).setQuantity(viewHolder.tv_qty.getText().toString());
                jum = 0;
                for (int i=0;i<carttArrayList.size();i++){
                    int harga=Integer.parseInt(String.valueOf(carttArrayList.get(i).getHarga()));
                    int beli=Integer.parseInt(carttArrayList.get(i).getQuantity());
                    jum=jum+(harga*beli);
                }

                Intent intent = new Intent("broadcast");
                intent.putExtra("jumlah_total",String.valueOf(jum));
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            }
        });

        viewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.tv_qty.getText().toString().equals("1")){
                    MDToast.makeText(v.getContext(),"Pesanan minimal 1",Toast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();
                    return;
                }
                quantity = Integer.parseInt(viewHolder.tv_qty.getText().toString());

                quantity = quantity -1;
                viewHolder.display(quantity);
                carttArrayList.get(position).setQuantity(viewHolder.tv_qty.getText().toString());
                jum = 0;
                for (int i=0;i<carttArrayList.size();i++){
                    int harga=Integer.parseInt(String.valueOf(carttArrayList.get(i).getHarga()));
                    int beli=Integer.parseInt(carttArrayList.get(i).getQuantity());
                    jum=jum+(harga*beli);
                }

                Intent intent = new Intent("broadcast");
                intent.putExtra("jumlah_total",String.valueOf(jum));
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            }
        });

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
            swipeRefreshLayout = itemView.findViewById(R.id.refresh);
            tvname = itemView.findViewById(R.id.product_name);
            tv_harga = itemView.findViewById(R.id.harga);
            tv_qty = itemView.findViewById(R.id.quantity_textview);
            iv_img = itemView.findViewById(R.id.thumbnail);
            tv_delete = itemView.findViewById(R.id.hapus);
            tv_price = itemView.findViewById(R.id.total);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);

        }

        private void display(int number) {
            tv_qty = itemView.findViewById(R.id.quantity_textview);
            tv_qty.setText("" + number);
        }

        public void hapusKeranjang(final String id_keranjang) {
            final CartActivity cartActivity=new CartActivity();
            StringRequest stringRequest=new StringRequest(Request.Method.POST, URL.hapus_keranjang, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Response", response);
                    try {
                        JSONArray keranjang=new JSONArray(response);
                        for(int i=0;i<keranjang.length();i++){
                            JSONObject objectKeranjang=keranjang.getJSONObject(i);
                            String    id_keranjang=objectKeranjang.getString("id_keranjang");
                            String gambar = URL.rootImage + objectKeranjang.getString("gambar");
                            String judulProduk=objectKeranjang.getString("nama_menu");
                            String hargaProduk=objectKeranjang.getString("harga");
//                        String stokProduk=objectKeranjang.getString("stok_produk");
                            String jumlahBeli=objectKeranjang.getString("quantity");
                            carttArrayList.clear();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error!=null){
                        VolleyHandler.handleVolleyError(itemView.getContext(),error);
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<>();
                    params.put("id_keranjang",id_keranjang);
//                    params.put("id_menu",id_produk);
//                    params.put("nama_menu",nm_produk);
//                    params.put("gambar",foto);

                    return params;
                }
            };
            Volley.newRequestQueue(itemView.getContext()).add(stringRequest);
        }

    }
}


