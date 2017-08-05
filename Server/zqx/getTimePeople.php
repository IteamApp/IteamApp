<?php error_reporting(E_ALL ^ E_DEPRECATED);
 


/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
if (isset($_GET['id'],$_GET['time']) ){
 
    $id = $_GET['id'];
    $time=$_GET['time'];
   
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
     
    // connecting to db
    $db = new DB_CONNECT();

    mysql_query("set names utf8");
     
    // get all products from products table
    $result = mysql_query("SELECT *FROM userteamrelation where team_id=$id AND user_team_relation=1") or die(mysql_error());
     
    // check for empty result
    if (mysql_num_rows($result) > 0) {
        $response["people"] = array();

     
        while ($row = mysql_fetch_array($result)) {
            // temp user array
            $aresponse = array();
            $userid = $row["user_stunum"];

            $getuser = mysql_query("SELECT *FROM freetime where userid=$userid AND time=$time") or die(mysql_error());
            while ($rowusers = mysql_fetch_array($getuser)) {
                    $getname = mysql_query("SELECT *FROM teamuser where user_stunum=$userid") or die(mysql_error());
                    while ($rowname = mysql_fetch_array($getname)) {
                        $aresponse["username"]=$rowname["user_name"];
                        $aresponse["userlogo"]=$rowname["user_head"];
                        $aresponse["user_stunum"]=$rowname["user_stunum"];
                        
                    }
                    $aresponse["tag"]=$rowusers["tag"];
                 array_push($response["people"], $aresponse);
            }
            
           

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