<?php error_reporting(E_ALL ^ E_DEPRECATED);
 


/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
if (isset($_GET['id']) ){
 
    $id = $_GET['id'];

    if($id!=''){
     
        // include db connect class
        require_once __DIR__ . '/db_connect.php';
         
        // connecting to db
        $db = new DB_CONNECT();

        mysql_query("set names utf8");
         
        // get all products from products table
        $result = mysql_query("SELECT *FROM teampassage where team_faculty=$id") or die(mysql_error());
         
        // check for empty result
        if (mysql_num_rows($result) > 0) {
            // looping through all results
            // products node
            $response["article"] = array();
         
            while ($row = mysql_fetch_array($result)) {
                // temp user array
                $product = array();
                $product["id"] = $row["passage_id"];
                $product["passage_time"] = $row["passage_time"];
                $product["passage_title"] = $row["passage_title"];
                $product["passage_content"] = $row["passage_content"];
                $product["passage_picture"] = $row["passage_picture"];
                $product["team_logo"] = $row["team_logo"];
                $product["team_name"] = $row["team_name"];
                 $product["team_id"] = $row["team_id"];
                // push single product into final response array
                array_push($response["article"], $product);
            }
            // success
            $response["success"] = 1;
         
            // echoing JSON response
            echo json_encode($response);
        } 
        else {
            // no products found
            $response["success"] = 0;
            $response["message"] = "No article found";
        }
    }

    else{
                // include db connect class
        require_once __DIR__ . '/db_connect.php';
         
        // connecting to db
        $db = new DB_CONNECT();

        mysql_query("set names utf8");
         
        // get all products from products table
        $result = mysql_query("SELECT *FROM teampassage") or die(mysql_error());
         
        // check for empty result
        if (mysql_num_rows($result) > 0) {
            // looping through all results
            // products node
            $response["article"] = array();
         
            while ($row = mysql_fetch_array($result)) {
                // temp user array
                $product = array();
                $product["id"] = $row["passage_id"];
                $product["passage_time"] = $row["passage_time"];
                $product["passage_title"] = $row["passage_title"];
                $product["passage_content"] = $row["passage_content"];
                $product["passage_picture"] = $row["passage_picture"];
                $product["team_logo"] = $row["team_logo"];
                $product["team_name"] = $row["team_name"];
                 $product["team_id"] = $row["team_id"];
                // push single product into final response array
                array_push($response["article"], $product);
            }
            // success
            $response["success"] = 1;
         
            // echoing JSON response
            echo json_encode($response);
        } 
        else {
            // no products found
            $response["success"] = 0;
            $response["message"] = "No article found";
        }
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