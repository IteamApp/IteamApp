<?php error_reporting(E_ALL ^ E_DEPRECATED);
 


/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
if (isset($_GET['id'])){
 
    $id = $_GET['id'];
   
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
     
    // connecting to db
    $db = new DB_CONNECT();

    mysql_query("set names utf8");

     
    // get all products from products table
    $result = mysql_query("SELECT *FROM userteamrelation where team_id=$id and user_team_relation!='3' ") or die(mysql_error());
     
    // check for empty result
    if (mysql_num_rows($result) > 0) {
        $response["team"] = array();
       
        while ($row = mysql_fetch_array($result)) {
            
            // temp user array
            $product = array();
            $product["userid"] = $row["user_stunum"];
            $userid=$row["user_stunum"];
            $result2 = mysql_query("SELECT *FROM teamuser where user_stunum=$userid") or die(mysql_error());
             if (mysql_num_rows($result2) > 0) {
                    while ($row2 = mysql_fetch_array($result2)) {
                         $product["user_name"] = $row2["user_name"];
                         $product["user_head"] = $row2["user_head"];
                    }
                }

            $result3 = mysql_query("SELECT * FROM usermessage where team_id=$id and user_stunum=$userid ORDER BY message_time desc limit 1") or die(mysql_error());
             if (mysql_num_rows($result3) > 0) {
                    while ($row3 = mysql_fetch_array($result3)) {
                         $product["message"] = $row3["message_content"];  
                    }
                }
     
            // push single product into final response array
            array_push($response["team"], $product);
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