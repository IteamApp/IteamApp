<?php error_reporting(E_ALL ^ E_DEPRECATED);
 
header("Content-type: text/html; charset=utf-8");

/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
if (isset($_POST['id']) &&isset($_POST['title'])&&isset($_POST['content'])&&isset($_POST['picture']))
{


 
    $id = $_POST['id'];
    $title =urldecode( $_POST['title']);
    $content = urldecode($_POST['content']);
    $time=date("Y-m-d H:i");
    $pictureb64 =  $_POST['picture'];
    $timemap=time();
    $img = base64_decode($pictureb64);
    $path="image/passage/".$timemap.".png";
    $a = file_put_contents($path, $img);
    $picture=$path;
 

   
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
     
    // connecting to db
    $db = new DB_CONNECT();

    mysql_query("set names utf8");

    $insert = mysql_query("UPDATE  teampassage SET passage_title='{$title}',passage_content='{$content}',passage_time='{$time}',passage_picture='{$picture}' where passage_id=$id") or die(mysql_error());
    $response["success"] = 1; 
    $response["message"] = "success";

}

else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
}
 
    // echo no users JSON
    echo json_encode($response);
?>