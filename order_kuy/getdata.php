<?php
   
    include_once('koneksi.php'); //koneksi database
 
    $query = "SELECT * FROM produk"; //select table in database
    $sql = mysqli_query($con, $query);
    $arraydata = array();
    while ($data = mysqli_fetch_array($sql)) {
        $arraydata[] = $data;
    }
 
    echo json_encode($arraydata);
 
?>