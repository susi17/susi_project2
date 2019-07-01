<?php
  include_once "koneksi.php";

	  class usr{}
	
	  $username = $_POST["username"];
	  $password = $_POST["password"];
	
	  if ((empty($username)) || (empty($password))) { 
	  	$response = new usr();
	  	$response->success = 0;
	  	$response->message = "Masukan Email dan Kata Sandi yang Benar"; 
	  	die(json_encode($response));
	  }
	
	  $query = mysqli_query($con, "SELECT * FROM users WHERE username='$username' AND password='$password'");
	
	  $row = mysqli_fetch_array($query);
	
	  if (!empty($row)){
	  	$response = new usr();
	  	$response->success = 1;
	  	$response->message = "Selamat datang ".$row['username'];
	  	$response->id_user = $row['id_user'];
	  	$response->username = $row['username'];
	  	die(json_encode($response));
		
	  } else { 
	  	$response = new usr();
	  	$response->success = 0;
	  	$response->message = "Username atau password salah";
	  	die(json_encode($response));
	  }
	
	  mysqli_close($con);
?>