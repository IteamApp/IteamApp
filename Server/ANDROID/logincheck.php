<?php

	error_reporting(E_ALL ^ E_NOTICE); 

	function object_to_array($obj){
    	if(is_array($obj)){
        	return $obj;
    	}
    	$_arr = is_object($obj)? get_object_vars($obj) :$obj;
    	foreach ($_arr as $key => $val){
    		$val=(is_array($val)) || is_object($val) ? object_to_array($val) :$val;
    		$arr[$key] = $val;
    	}
    	return $arr;  
	}

	/*$key_val = $_GET['data'];

	$key_val = json_decode($key_val);

	$key_val = object_to_array($key_val);*/

	$num = $_GET["user_stunum"];

	$password = $_GET["user_code"];

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
	//mysqli_close($link);
	
?>