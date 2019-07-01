package com.example.orderkuy.ServerSide;

public class URL {
    public static final String root = "http://192.168.43.142/project/";
    public static final String rootImage = "http://192.168.43.142/project/assets/image/";
    public static final String login = root+"login.php";
    public static final String register = root+"register.php";
    public static final String get_produk = root+"getdata.php";
    public static final String get_keranjang = root+"get_keranjang.php";
    public static final String get_struk = root+"get_struk.php";
    public static final String save_keranjang = root+"save_keranjang.php";
    public static final String hapus_keranjang = root+"hapus_keranjang.php";
    public static final String insert_keranjang = root+"insert_keranjang.php";
    public static final String cari_produk_byScanner = root + "cariProduk_byScanner.php";

    public static final int TIMEOUT_ACCESS = 10000;
}