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
    $result = mysql_query("SELECT *FROM freetime where userid=$id") or die(mysql_error());
     
    // check for empty result
    if (mysql_num_rows($result) > 0) {
        $response["freetime"] = array();
        // looping through all results
        // products node
     
        while ($row = mysql_fetch_array($result)) {
            // temp user array
            $aresponse = array();
            $aresponse["userid"] = $row["userid"];

            $aresponse["team_name"] = $row["team_name"];
            
            $aresponse["time"] = $row["time"];
            $aresponse["tag"] = $row["tag"];

            array_push($response["freetime"], $aresponse);

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