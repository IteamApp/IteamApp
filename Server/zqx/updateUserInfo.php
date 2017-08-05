<?php error_reporting(E_ALL ^ E_DEPRECATED);
 


/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
if (isset($_GET['id']) &&isset($_GET['major']) &&isset($_GET['phone']) ){
 
    $id = $_GET['id'];
    $major = $_GET['major'];
    $phone=$_GET['phone'];
   
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
     
    // connecting to db
    $db = new DB_CONNECT();

    mysql_query("set names utf8");
     
    // get all products from products table
    $result = mysql_query("UPDATE teamuser SET user_major='{$major}' ,phone='{$phone}' where user_stunum=$id") or die(mysql_error());
     
    
    $response["success"] = 1;
    
}

else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
}
 
    // echo no users JSON
    echo json_encode($response);
?>