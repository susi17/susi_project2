<?php 
include_once('koneksi.php'); //koneksi database

class usr{}

$cari = mysqli_query($con, "SELECT * FROM keranjang WHERE id_produk = '$_POST[id_produk]' and id_user = '$_POST[id_user]'");
if (mysqli_num_rows($cari)>0) {
	$data = mysqli_fetch_array($cari);
	$quantity = $data['quantity'];
	$jum = $quantity + $_POST['quantity'];

	$query = mysqli_query($con, "UPDATE keranjang SET quantity = '$jum' WHERE id_produk = '$_POST[id_produk]' and id_user = '$_POST[id_user]'");

	if ($query) {
		$response = new usr();
		$response ->success = 1;
		$response ->message = "Berhasil Masuk Keranjang"; 
		die(json_encode($response));
	} else {
		$response = new usr();
		$response ->success = 400;
		$response ->message = "Gagal Masukan ke Keranjang"; 
		die(json_encode($response));
	}
	# code...
} else {
	$query = mysqli_query($con, "INSERT INTO keranjang (id_produk, id_user, quantity, deskripsi_order)VALUES ('$_POST[id_produk]', '$_POST[id_user]', '$_POST[quantity]', '$_POST[deskripsi_order]')");

	if ($query) {
		$response = new usr();
		$response ->success = 1;
		$response ->message = "Berhasil Masuk Keranjang"; 
		die(json_encode($response));
	} else {
		$response = new usr();
		$response ->success = 400;
		$response ->message = "Gagal Masukan ke Keranjang"; 
		die(json_encode($response));
	}
}


	mysqli_close($con);
?>