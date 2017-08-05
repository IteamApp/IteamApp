<?php error_reporting(E_ALL ^ E_DEPRECATED);
 


/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
if (isset($_GET['id']) &&isset($_GET['name']) &&isset($_GET['brief'])){
 
    $id = $_GET['id'];
    $name = $_GET['name'];
    $brief=$_GET['brief'];
   
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
     
    // connecting to db
    $db = new DB_CONNECT();

    mysql_query("set names utf8");
     
    // get all products from products table
    $result = mysql_query("UPDATE team SET team_name='{name}',team_brief='{$brief}'where team_id=$id") or die(mysql_error());
     
    
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