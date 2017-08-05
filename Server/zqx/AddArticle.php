<?php error_reporting(E_ALL ^ E_DEPRECATED);
 
header("Content-type: text/html; charset=utf-8");
/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
if (isset($_POST['id']) &&isset($_POST['title'])&&isset($_POST['content'])&&isset($_POST['picture']))
{


 
    $id = $_POST['id'];
    $title = $_POST['title'];
    $content = $_POST['content'];
    $time=date("Y-m-d H:i");
    $pictureb64 =  $_POST['picture'];
    $timemap=time();
    $img = base64_decode($pictureb64);
    $path="image/passage/".$timemap.".png";
    $a = file_put_contents($path, $img);
    $picture=$path;
    echo $picture;
    $logo='';
    $team='';
    
    $faculty='';


   
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
     
    // connecting to db
    $db = new DB_CONNECT();

    mysql_query("set names utf8");
    $result = mysql_query("SELECT *FROM team where team_id=$id") or die(mysql_error());
    
    // check for empty result
    if (mysql_num_rows($result) > 0) {
        while ($row = mysql_fetch_array($result)) {
            $team=$row["team_name"];
            $logo=$row["team_logo"];
            $faculty=$row["team_faculty"];
        }
    }
    mysql_query("set names utf8");

    $insert = mysql_query("INSERT INTO  teampassage(team_id,passage_title,passage_content,passage_time,passage_readnum,passage_picture,team_logo,team_name,passage_tag,team_faculty) VALUES($id,'{$title}','{$content}','{$time}','0','{$picture}','{$logo}','{$team}','0',$faculty)") or die(mysql_error());
    $response["success"] = 1;
    $response["message"] = "success";

}

else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
}
 
    // echo no users JSON
    echo json_encode($response);
?>