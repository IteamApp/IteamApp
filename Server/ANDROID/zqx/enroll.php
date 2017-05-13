<?php error_reporting(E_ALL ^ E_DEPRECATED);
 


/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
if (isset($_GET['id']) &&isset($_GET['team'])&&isset($_GET['brief'])){
 
    $id = $_GET['id'];
    $team = $_GET['team'];
    $brief = $_GET['brief'];

   
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
     
    // connecting to db
    $db = new DB_CONNECT();

    mysql_query("set names utf8");
    $result = mysql_query("SELECT *FROM userteamrelation where team_id=$team and user_stunum =$id and user_team_relation='3' ") or die(mysql_error());
     
    // check for empty result
    if (mysql_num_rows($result) > 0) {
        $response["success"] = 0;
    }
    else if($brief!="1"){

        // get all products from products table
        $insert=mysql_query("insert into userteamrelation(user_stunum,team_id,user_team_relation,relation_discription) values($id,$team,'3','{$brief}')") or die(mysql_error());
        $response["success"] = 1;
    }
    else{
         $response["success"] = 1;
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