package com.example.orderkuy.model;

import android.content.Context;

import java.util.ArrayList;

public class Product {

    public int id_menu;
    public String nama_menu;
    public String deskripsi;
    public int harga;
    public String gambar;
    public String Quantity;
    public String stok_produk;

    public Product(int id_menu, String nama_menu, int harga, String gambar, String quantity, String stok_produk) {
        this.id_menu = id_menu;
        this.nama_menu = nama_menu;
        this.harga = harga;
        this.gambar = gambar;
        Quantity = quantity;
        this.stok_produk = stok_produk;
    }

    public Product() {
        this.id_menu = id_menu;
        this.nama_menu = nama_menu;
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.gambar = gambar;
        this.Quantity = Quantity;
        this.stok_produk = stok_produk;
    }

    public int getId_menu() {
        return id_menu;
    }

    public void setId_menu(int id_menu) {
        this.id_menu = id_menu;
    }

    public String getNama_menu() {
        return nama_menu;
    }

    public void setNama_menu(String nama_menu) {
        this.nama_menu = nama_menu;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getStok_produk() {
        return stok_produk;
    }

    public void setStok_produk(String stok_produk) {
        this.stok_produk = stok_produk;
    }
}
