<?php 
include_once "koneksi.php";
class usr{}

$query = mysqli_query($con, "SELECT * FROM produk WHERE id_produk='$_POST[id_produk]' ORDER BY nama ASC");

if (mysqli_num_rows($query) > 0) {
	$response = new usr();
	$response = array();
	$response['status'] = 200;
	$response["data"] =array();
	while ($row = mysqli_fetch_array($query)) {
		$r['id_produk'] = $row["id_produk"];
		$r['nama'] = $row["nama"];
		$r['harga'] = $row["harga"];
		$r['foto'] = $row["foto"];
		$r['deskripsi'] = $row["deskripsi"];
		$r['stok_produk'] = $row["stok_produk"];

		array_push($response["data"], $r);
		# code...
	}
	die (json_encode($response));
} else {
	$response = new usr();
		$response = array();
		$response['status'] = 400;
		$response ['message'] = "Produk Tidak di Temukan..."; 
		die(json_encode($response));
}

mysqli_close($con);


?>