<?php error_reporting(E_ALL ^ E_DEPRECATED);

require __DIR__ . '/autoload.php';

use JPush\Client as JPush;
$app_key='cb45b494cbb55098a90c9ab4';
$master_secret='aad8c31e360d78379ec5f327';
// array for JSON response
$responsearray = array();

if (isset($_GET['text']) &&isset($_GET['team_id']) &&isset($_GET['people']) ){

	$text=$_GET['text'];
	$team_id=$_GET['team_id'];
	$people=array();
	$people=$_GET['people'];


	$client = new JPush($app_key, $master_secret);

	$push_payload = $client->push()
	    ->setPlatform('all')
	    ->addTag($people)
	    ->setNotificationAlert($_GET['text']);
	try {
	    $response = $push_payload->send();
	    // print_r($response);
	} catch (\JPush\Exceptions\APIConnectionException $e) {
	    // try something here
	    print $e;
	} catch (\JPush\Exceptions\APIRequestException $e) {
	    // try something here
	    print $e;
	}

		// include db connect class
	require_once __DIR__ . '/db_connect.php';
	 
	// connecting to db
	$db = new DB_CONNECT();

	mysql_query("set names utf8");
	 
	// get all products from products table
	$time=date("Y-m-d H:i");
	

	foreach ($people as $person){ 
      $insert = mysql_query("INSERT INTO  usermessage(user_stunum,team_id,message_title,message_content,message_time,message_unread) VALUES($person,'{$team_id}','{$text}','{$text}','{$time}','1')") or die(mysql_error());
    } 






	$responsearray["success"] = 1;
    $responsearray["message"] = "success";
}
else{
	$responsearray["success"] = 0;
    $responsearray["message"] = "Required field(s) is missing";

}

 
    // echo no users JSON
    echo json_encode($responsearray);
?>
