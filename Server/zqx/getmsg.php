<?php error_reporting(E_ALL ^ E_DEPRECATED);
 


/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
if (isset($_GET['id'])&& isset($_GET['team_id'])){
 
    $id = $_GET['id'];
    $type=$_GET['team_id'];
   
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
     
    // connecting to db
    $db = new DB_CONNECT();

    mysql_query("set names utf8");
     
    // get all products from products table
    $result = mysql_query("SELECT *FROM usermessage where user_stunum=$id and team_id=$type") or die(mysql_error());
     
    // check for empty result
    if (mysql_num_rows($result) > 0) {
        $response["msg"] = array();

        while ($row = mysql_fetch_array($result)) {
            
            // temp user array
            $product = array();
            $product["message_title"] = $row["message_title"];
            $product["message_time"] = $row["message_time"];
            $result2 = mysql_query("SELECT *FROM team where team_id=$type") or die(mysql_error()); if (mysql_num_rows($result2) > 0) {
                    while ($row2 = mysql_fetch_array($result2)) {
                         $product["team_logo"] = $row2["team_logo"];
                    }
                }
     
            // push single product into final response array
            array_push($response["msg"], $product);
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