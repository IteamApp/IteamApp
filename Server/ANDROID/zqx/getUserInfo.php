<?php error_reporting(E_ALL ^ E_DEPRECATED);
 


/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
if (isset($_GET['id']) ){
 
    $id = $_GET['id'];
   
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
     
    // connecting to db
    $db = new DB_CONNECT();

    mysql_query("set names utf8");
     
    // get all products from products table
    $result = mysql_query("SELECT *FROM teamuser where user_stunum=$id") or die(mysql_error());
     
    // check for empty result
    if (mysql_num_rows($result) > 0) {

        while ($row = mysql_fetch_array($result)) {
    

            $response["user_name"] = $row["user_name"];
            $response["user_head"] = $row["user_head"];
            $response["user_sex"] = $row["user_sex"];
            $response["user_major"] = $row["user_major"];
            $response["user_stunum"] = $row["user_stunum"];
            $response["phone"] = $row["phone"];

                // push single product into final response array
        }

        // success
        $response["success"] = 1;
    } 
    else {
        // no products found
        $response["success"] = 0;
        $response["message"] = "No article found";
    }
}

else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
}
 
    // echo no users JSON
    echo json_encode($response);
?>