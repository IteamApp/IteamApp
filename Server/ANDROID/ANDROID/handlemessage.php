<?php

	error_reporting(E_ALL ^ E_NOTICE); 

	$key_val = $_POST['data'];

	$num = $key_val["user_stunum"];

	$mode = $key_val["mode"];

	$target = $key_val["message_id"];

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