<?php error_reporting(E_ALL ^ E_DEPRECATED);
 


/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
if (isset($_GET['id']) &&isset($_GET['team_id'])  &&isset($_GET['tag']) &&isset($_GET['time'])){
 
    $id = $_GET['id'];
    $tag = $_GET['tag'];
    $time = $_GET['time'];
    $team_id = $_GET['team_id'];

    
   
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
     
    // connecting to db
    $db = new DB_CONNECT();

    mysql_query("set names utf8");
     
    // get all products from products table
    if ($tag=="2"){
        $update = mysql_query("UPDATE  freetime SET team_name='', tag='1',team_id='' where tag='2' AND userid=$id AND time=$time") or die(mysql_error());
    }
    else{
      $team_name='';
      mysql_query("set names utf8");
      $getname = mysql_query("SELECT * From  team WHERE team_id=$team_id") or die(mysql_error());
      while ($rowname = mysql_fetch_array($getname)) {
        $team_name=$rowname["team_name"];

      }
    $update = mysql_query("UPDATE  freetime SET team_name='{$team_name}', tag='2', team_id='{$team_id}' where tag='1' AND userid=$id AND time=$time") or die(mysql_error());


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