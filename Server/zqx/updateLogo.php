<?php error_reporting(E_ALL ^ E_DEPRECATED);
 


/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
if (isset($_POST['id']) &&isset($_POST['picture'] )&&isset($_POST['type']))
{
    $type=$_POST['type'];

    if($type=="2"){


 
        $id = $_POST['id'];
        $time=date("Y-m-d H:i");
        $pictureb64 =  $_POST['picture'];
        $timemap=time();
        $img = base64_decode($pictureb64);
        $path="image/teamlogo/".$timemap.".png";
        $a = file_put_contents($path, $img);
        $picture=$path;


       
        // include db connect class
        require_once __DIR__ . '/db_connect.php';
         
        // connecting to db
        $db = new DB_CONNECT();

        mysql_query("set names utf8");

        $update = mysql_query("UPDATE team SET team_logo='{$picture}' where  team_id='{$id}'") or die(mysql_error());
        
       
        $response["success"] = 1;
        $response["message"] = "success";
    }

    else{
        $id = $_POST['id'];
        $time=date("Y-m-d H:i");
        $pictureb64 =  $_POST['picture'];
        $timemap=time();
        $img = base64_decode($pictureb64);
        $path="image/userlogo/".$timemap.".png";
        $a = file_put_contents($path, $img);
        $picture=$path;


       
        // include db connect class
        require_once __DIR__ . '/db_connect.php';
         
        // connecting to db
        $db = new DB_CONNECT();

        mysql_query("set names utf8");

        $update = mysql_query("UPDATE teamuser SET user_head='{$picture}' where  user_stunum='{$id}'") or die(mysql_error());
        
       
        $response["success"] = 1;
        $response["message"] = "success";

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