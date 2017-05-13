<?php 

	error_reporting(E_ALL ^ E_NOTICE); 

	$key_val = $_POST['data'];

	$mode = $key_val['mode'];

	$link = mysqli_connect(
		'localhost',
		'root',
		'',
		'iteam');

	mysqli_query($link,"set names 'utf8'");
	mysqli_query($link,"set character_set_client=utf8");
	mysqli_query($link,"set character_set_results=utf8");

	function sql($uid,$tid){

		$link = mysqli_connect(
		'localhost',
		'root',
		'',
		'iteam');

		mysqli_query($link,"set names 'utf8'");
		mysqli_query($link,"set character_set_client=utf8");
		mysqli_query($link,"set character_set_results=utf8");

		$sql = "SELECT user_team_relation FROM userteamrelation WHERE user_stunum "."="."$uid"." AND "."team_id"."="."$tid";

		$result = mysqli_query($link,$sql);

		if($result){

			$row = mysqli_fetch_assoc($result);

			return $row['user_team_relation'];

		}
		else{
			return 0;
		}
	}

	if($mode == "sql"){

		$uid = $key_val['user_id'];

		$tid = $key_val['team_id'];

		$mes = "";

		echo sql($uid,$tid);

	}
	else if($mode == "apply"){

		$uid = $key_val['user_id'];

		$tid = $key_val['team_id'];

		$mes="";

		$i = sql($uid,$tid);

		if($i==3||$i==4){
			return;
		}
		else if($i==0){
			$sql = "INSERT INTO userteamrelation VALUES ($uid,$tid,2,$mes)";
		}
		else{

			$sql = "UPDATE userteamrelation SET user_team_relation"."="."2 WHERE user_stunum"."="."$uid"." AND "."team_id"."="."$tid";

			mysqli_query($link,$sql);

		}
		return ;

	}
	else if($mode == "accept"){

		$uid = $key_val['user_id'];

		$tid = $key_val['team_id'];

		$sql = "UPDATE userteamrelation SET user_team_relation"."="."3 WHERE user_stunum"."="."$uid"." AND "."team_id"."="."$tid";

		mysqli_query($link,$sql);

		return ;

	}
	else if($mode == "promote"){

		$uid = $key_val['user_id'];

		$tid = $key_val['team_id'];

		$sql = "UPDATE userteamrelation SET user_team_relation"."="."4 WHERE user_stunum"."="."$uid"." AND "."team_id"."="."$tid";

		mysqli_query($link,$sql);

		return;

	}
	else if($mode == "kick"){

		$uid = $key_val['user_id'];

		$tid = $key_val['team_id'];

		$sql = "DELETE FROM userteamrelation WHERE user_stunum"."="."$uid"." AND "."team_id"."="."$tid";

		mysqli_query($link,$sql);

	}
	else if($mode == "follow"){

		$uid = $key_val['user_id'];

		$tid = $key_val['team_id'];

		$mes="''";

		if(sql($uid,$tid)==0){

			$sql = $sql = "INSERT INTO userteamrelation VALUES ($uid,$tid,1,$mes)";
			echo $sql;
			mysqli_query($link,$sql);
		}
		else return;

	}
	else if($mode == "unfollow"){

		$uid = $key_val['user_id'];

		$tid = $key_val['team_id'];

		if(sql($uid,$tid)==1){
			$sql =  "DELETE FROM userteamrelation WHERE user_stunum"."="."$uid"." AND "."team_id"."="."$tid";
			mysqli_query($link,$sql);
		}
		else return;
	}
?>