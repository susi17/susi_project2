package com.example.orderkuy.model;

import android.support.v7.widget.RecyclerView;

import java.util.Date;
import java.util.List;

public class Keranjang_Model {

    private String id_keranjang;
    private String foto;
    private String deskripsi_order;
    private String quantity;
    private String nama_produk;
    private String harga;

    public String getId_keranjang() {
        return id_keranjang;
    }

    public void setId_keranjang(String id_keranjang) {
        this.id_keranjang = id_keranjang;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDeskripsi_order() {
        return deskripsi_order;
    }

    public void setDeskripsi_order(String deskripsi_order) {
        this.deskripsi_order = deskripsi_order;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public Keranjang_Model(String id_keranjang,String foto, String quantity, String nama_produk, String harga) {
        this.id_keranjang = id_keranjang;
        this.foto = foto;
        this.quantity = quantity;
        this.nama_produk = nama_produk;
        this.harga = harga;
    }
}
