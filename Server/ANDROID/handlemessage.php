<?php

    error_reporting(E_ALL ^ E_DEPRECATED)   
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
	/*
	$key_val = $_GET['data'];

	$key_val = json_decode($key_val);

	$key_val = object_to_array($key_val);*/

	$num = $_GET["user_stunum"];

	$mode = $_GET["mode"];

	$target = $_GET["message_id"];
	mysql_query("set names utf8");

	$link = mysqli_connect(
		'localhost',
		'root',
		'',
		'iteam');

	mysqli_query($link,"set names 'utf8'");
	mysqli_query($link,"set character_set_client=utf8");
	mysqli_query($link,"set character_set_results=utf8");

	if($mode=="get"){

		$sql = "SELECT * FROM usermessage WHERE user_stunum"."="."$num";

		$result = mysqli_query($link,$sql);
		
		mysqli_close($link);
		
		$out =  "{\"arr\":[";

		$ctr;

		$ctr=0;

		while($row = mysqli_fetch_assoc($result)){

			if($ctr){
				$out.=",";
			}
			$ctr++;
			$out.= ("{"."\"teamid\":"."\"".$row['team_id']."\"".",\"title\":"."\"".$row['message_title']."\"".",\"content\":"."\"".$row['message_content']."\"".",\"time\":"."\"".$row['message_time']."\"".",\"read\":"."\"".$row['message_unread']."\"".",\"messageid\":"."\"".$row['message_id']."\""."}");
		}
		
		$out.=("],\"success\":\"1\"}");
		
		echo $out;
		
	}
	else if($mode=="delete"){
		//undefined
	}
	else if($mode=="read"){
		//undefined
	}


?>