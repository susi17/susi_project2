package com.example.orderkuy.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.orderkuy.R;
import com.example.orderkuy.ServerSide.URL;
import com.example.orderkuy._sliders.FragmentSlider;
import com.example.orderkuy._sliders.SliderIndicator;
import com.example.orderkuy._sliders.SliderPagerAdapter;
import com.example.orderkuy._sliders.SliderView;
import com.example.orderkuy.adapter.RecyclerViewAdapter;
import com.example.orderkuy.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SliderPagerAdapter mAdapter;
    private SliderIndicator mIndicator;

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter adapter;
    private String URL_JSON = com.example.orderkuy.ServerSide.URL.get_produk;  //passing data dari MYSQL
    private JsonArrayRequest Request ;
    private RequestQueue requestQueue ;
    private ArrayList<Product> listProduct = new ArrayList<>();

    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.sliderView) SliderView sliderView;
    @BindView(R.id.pagesContainer) LinearLayout mLinearLayout;
    @BindView(R.id.rv) RecyclerView myrv;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment, container, false);

        ButterKnife.bind(this, view); //Instance ButteKnife

        setupSlider();

        myrv.setLayoutManager(mLayoutManager);
        myrv.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                listProduct.clear();
                jsoncall();
            }

        });



        return view;

    }



    private void jsoncall() {

        swipeRefreshLayout.setRefreshing(true);
        Request = new JsonArrayRequest(URL_JSON, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;


                for (int i = 0 ; i<response.length();i++) {


                    //Toast.makeText(getApplicationContext(),String.valueOf(i),Toast.LENGTH_SHORT).show();

                    try {

                        jsonObject = response.getJSONObject(i);
                        Product product = new Product();

                        product.setId_menu(jsonObject.getInt("id_menu"));
                        product.setNama_menu(jsonObject.getString("nama_menu"));
                        product.setHarga(jsonObject.getInt("harga"));
                        product.setGambar(com.example.orderkuy.ServerSide.URL.rootImage +jsonObject.getString("gambar"));
                        product.setDeskripsi(jsonObject.getString("deskripsi"));
                        product.setStok_produk(jsonObject.getString("stok_produk"));
                        //Toast.makeText(MainActivity.this,anime.toString(),Toast.LENGTH_SHORT).show();
                        listProduct.add(product);
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                }



//                Toast.makeText(this,getActivity()"Size of Liste "+String.valueOf(listProduct.size()),Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this,listProduct.get(1).toString(),Toast.LENGTH_SHORT).show();

                setRvadapter(listProduct);
            }


            private void setRvadapter(ArrayList<Product> lst) {

                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getActivity(),lst) ;
                myrv.setHasFixedSize(true);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
                myrv.setLayoutManager(gridLayoutManager);
                myrv.setAdapter(myAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                swipeRefreshLayout.setRefreshing(false);
            }
        });


        requestQueue = Volley.newRequestQueue(this.getActivity());
        requestQueue.add(Request);
    }

    private void setupSlider() {
        sliderView.setDurationScroll(800);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(FragmentSlider.newInstance("http://www.menucool.com/slider/prod/image-slider-1.jpg"));
        fragments.add(FragmentSlider.newInstance("http://www.menucool.com/slider/prod/image-slider-2.jpg"));
        fragments.add(FragmentSlider.newInstance("http://www.menucool.com/slider/prod/image-slider-3.jpg"));
        fragments.add(FragmentSlider.newInstance("http://www.menucool.com/slider/prod/image-slider-4.jpg"));

        mAdapter = new SliderPagerAdapter(getFragmentManager(), fragments);
        sliderView.setAdapter(mAdapter);
        mIndicator = new SliderIndicator(getActivity(), mLinearLayout, sliderView, R.drawable.indicator_circle);
        mIndicator.setPageCount(fragments.size());
        mIndicator.show();
    }

    @Override
    public void onRefresh() {
        listProduct.clear();
        jsoncall();
    }

}


