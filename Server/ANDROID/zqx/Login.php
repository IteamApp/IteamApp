<?php error_reporting(E_ALL ^ E_DEPRECATED);
 

$response = array();
if (isset($_GET['username'])&&isset($_GET['pwd']) ){
 
    $username = $_GET['username'];

    $pwd = $_GET['pwd'];

    // echo $username;
    // echo $pwd;
   
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
     
    // connecting to db
    $db = new DB_CONNECT();

    //mysql_query("set names utf8");
     
    // get all products from products table
    $result = mysql_query("SELECT *FROM teamuser where user_stunum=$username and user_code='{$pwd}'") or die(mysql_error());

     
    // check for empty result
    if (mysql_num_rows($result) > 0) {
       
        $response["success"] = 1;
        $response["username"] = $username;
        $response["message"] = "Login success";

        if(strlen($username)==7){
             $getid = mysql_query("SELECT *FROM teamuser where user_stunum=$username") or die(mysql_error());
            $id='';
            // check for empty result
            if (mysql_num_rows($getid) > 0) {
                while ($row = mysql_fetch_array($getid)) {
                    $id=$row["team_id"];
                }
            }
             $response["team_id"] = $id;
    
    } 
}
    else {
        // no products found
        $response["success"] = 0;
        $response["message"] = "Login fail";
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