<?php error_reporting(E_ALL ^ E_DEPRECATED);
 


/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
if (isset($_GET['team_id']) &&isset($_GET['start'])&&isset($_GET['end'])&&isset($_GET['type'])){
 
    $start = $_GET['start'];
    $end = $_GET['end'];
    $id=$_GET['team_id'];
    $type=$_GET['type'];
   

   if($type=="1"){
 
        // include db connect class
        require_once __DIR__ . '/db_connect.php';
         
        // connecting to db
        $db = new DB_CONNECT();

        mysql_query("set names utf8");
         
        // get all products from products table
        $result = mysql_query("SELECT *FROM enrolltime where team_id=$id") or die(mysql_error());
         
        // check for empty result
        if (mysql_num_rows($result) > 0) {
            
            $result2 = mysql_query("UPDATE enrolltime SET start='{$start}',end='{$end}' where team_id=$id") or die(mysql_error()); 
            
            // success
            $response["success"] = 1;
        }
        else {
            $result2 = mysql_query("INSERT INTO enrolltime (team_id,start,end) VALUES($id,'{$start}','{$end}')") or die(mysql_error()); 
            
            // success
            $response["success"] = 1;
        }
    }
    else{
        // include db connect class
        require_once __DIR__ . '/db_connect.php';
         
        // connecting to db
        $db = new DB_CONNECT();

        mysql_query("set names utf8");
         
        // get all products from products table
        $result = mysql_query("SELECT *FROM enrolltime where team_id=$id") or die(mysql_error());
         
        // check for empty result
        if (mysql_num_rows($result) > 0) {
             while ($row = mysql_fetch_array($result)) {
                 $response["start"] = $row["start"] ;
                 $response["end"] = $row["end"] ;
             }
           
            $response["success"] = 1;
        }
        else {
            
        
            $response["success"] = 0;
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