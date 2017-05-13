<?php

	error_reporting(E_ALL ^ E_NOTICE); 

	$key_val = $_POST['data'];

	$num = $key_val["user_stunum"];

	$password = $key_val["user_code"];

	$link = mysqli_connect(
		'localhost',
		'root',
		'',
		'iteam');

	mysqli_query($link,"set names 'utf8'");
	mysqli_query($link,"set character_set_client=utf8");
	mysqli_query($link,"set character_set_results=utf8");

	$sql = "SELECT user_code FROM teamuser WHERE user_stunum"."="."$num";

	$result = mysqli_query($link,$sql);

	mysqli_close($link);

	if($result){

		$row = mysqli_fetch_assoc($result);

		if($password==$row['user_code']){
 	
 			echo 1;
 		
 		}
 		else{

 			echo 0;

 		}
	
	}
	else{

		echo 0;

	}
	mysqli_close($link);
	
?>