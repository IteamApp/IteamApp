<?php error_reporting(E_ALL ^ E_DEPRECATED);
 


/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
if (isset($_GET['id']) &&isset($_GET['team_id']) &&isset($_GET['type'])){
 
    $id = $_GET['id'];
    $team_id = $_GET['team_id'];
    $type=$_GET['type'];


 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
     
    // connecting to db
    $db = new DB_CONNECT();

    mysql_query("set names utf8");

    if($type=="1"){
         $update = mysql_query("UPDATE  userteamrelation SET user_team_relation='1' where user_team_relation='3' and user_stunum=$id and team_id='{$team_id}'") or die(mysql_error());
    }

    if($type=="2"){
         $delete = mysql_query("DELETE FROM userteamrelation where user_team_relation='3' and user_stunum=$id and team_id='{$team_id}'") or die(mysql_error());

    }

     if($type=="3"){
         $delete = mysql_query("DELETE FROM userteamrelation where user_team_relation='1' and user_stunum=$id and team_id='{$team_id}'") or die(mysql_error());

    }
     
  
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