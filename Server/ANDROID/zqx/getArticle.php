<?php error_reporting(E_ALL ^ E_DEPRECATED);
 


/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
if (isset($_GET['id'])&& isset($_GET['type'])){
 
    $id = $_GET['id'];
    $type=$_GET['type'];
   
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
     
    // connecting to db
    $db = new DB_CONNECT();

    mysql_query("set names utf8");
     
    // get all products from products table
    $result = mysql_query("SELECT *FROM userteamrelation where user_stunum=$id and user_team_relation=$type") or die(mysql_error());
     
    // check for empty result
    if (mysql_num_rows($result) > 0) {
        $response["article"] = array();
    
        while ($row = mysql_fetch_array($result)) {
            $team_id=$row["team_id"];


            $result2 = mysql_query("SELECT *FROM teampassage where team_id=$team_id") or die(mysql_error());
            if (mysql_num_rows($result2) > 0) {

                while ($row2 = mysql_fetch_array($result2)) {
                    
                    // temp user array
                    $product = array();
                    $product["id"] = $row2["passage_id"];
                    $product["team_id"] = $row2["team_id"];
                    $product["passage_time"] = $row2["passage_time"];
                    $product["passage_title"] = $row2["passage_title"];
                    $product["passage_content"] = $row2["passage_content"];
                    $product["passage_picture"] = $row2["passage_picture"];
                    $product["team_logo"] = $row2["team_logo"];
                    $product["team_name"] = $row2["team_name"];
                    // push single product into final response array
                    array_push($response["article"], $product);
                }
            }
        
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