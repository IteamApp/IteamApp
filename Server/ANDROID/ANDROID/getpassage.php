<?php

	error_reporting(E_ALL ^ E_NOTICE); 

	$key_val = $_POST['data'];

	$mode = $key_val["mode"];

	if($mode=="all"){

		$link = mysqli_connect(
		'localhost',
		'root',
		'',
		'iteam');

		mysqli_query($link,"set names 'utf8'");
		mysqli_query($link,"set character_set_client=utf8");
		mysqli_query($link,"set character_set_results=utf8");

		$sql = "SELECT * FROM teampassage LIMIT 0,20";

		$result = mysqli_query($link,$sql);

		mysqli_close($link);

		//{arr:[{team_loto:,team_name:,passage_time:,passage_content:,passage_id:,}],success:1};

		$out = "{"."\""."arr"."\"".":[";

		$ctr=0;

		while($row = mysqli_fetch_assoc($result)){

			if($ctr){
				$out.=",";
			}
			$ctr++;

			$out.=("{"."\""."team_logo"."\"".":"."\"".$row['team_logo']."\"".","."\""."team_name"."\"".":"."\"".$row['team_name']."\"".","."\""."passage_time"."\"".":"."\"".$row['passage_time']."\"".","."\""."passage_picture"."\"".":"."\"".$row['passage_picture']."\"".","."\""."passage_content"."\"".":"."\"".$row['passage_content']."\"".","."\""."passage_id"."\"".":"."\"".$row['passage_id']."\"".","."\""."passage_title"."\"".":"."\"".$row['passage_titlee']."\""."}");	

		}

		$out.="],"."\""."success"."\"".":"."1"."}";

		echo $out;

	}
	else if($mode=="follow"){
		$link = mysqli_connect(
		'localhost',
		'root',
		'',
		'iteam');

		mysqli_query($link,"set names 'utf8'");
		mysqli_query($link,"set character_set_client=utf8");
		mysqli_query($link,"set character_set_results=utf8");

		$id = $key_val['addition'];

		$sql = "SELECT team_id FROM userteamrelation WHERE user_stunum"."="."$id"." AND (user_team_relation = '1')";

		$result = mysqli_query($link,$sql);

		$arrp=0;

		$arr=array();

		while($row = mysqli_fetch_assoc($result)){
			$arr[$arrp++]=$row['team_id'];
		}

		$out = "{"."\""."arr"."\"".":[";

		$ctr=0;

		for($i=0;$i<$arrp;$i++){

			$sql="SELECT * FROM teampassage WHERE team_id"."=".$arr[$i];

			$result = mysqli_query($link,$sql);

			while($row = mysqli_fetch_assoc($result)){

				if($ctr){
					$out.=",";
				}
				$ctr++;

				$out.=("{"."\""."team_logo"."\"".":"."\"".$row['team_logo']."\"".","."\""."team_name"."\"".":"."\"".$row['team_name']."\"".","."\""."passage_time"."\"".":"."\"".$row['passage_time']."\"".","."\""."passage_picture"."\"".":"."\"".$row['passage_picture']."\"".","."\""."passage_content"."\"".":"."\"".$row['passage_content']."\"".","."\""."passage_id"."\"".":"."\"".$row['passage_id']."\"".","."\""."passage_title"."\"".":"."\"".$row['passage_titlee']."\""."}");	
			}
		}

		$out.="],"."\""."success"."\"".":"."1"."}";

		echo $out;

	}
	else if($mode=="tag"){
		$link = mysqli_connect(
		'localhost',
		'root',
		'',
		'iteam');

		mysqli_query($link,"set names 'utf8'");
		mysqli_query($link,"set character_set_client=utf8");
		mysqli_query($link,"set character_set_results=utf8");

		$id = $key_val['addition'];



		$sql = "SELECT * FROM teampassage WHERE passage_tag"."="."$id"." LIMIT 0,20";

		$result = mysqli_query($link,$sql);

		mysqli_close($link);

		//{arr:[{team_loto:,team_name:,passage_time:,passage_content:,passage_id:,}],success:1};

		$out = "{"."\""."arr"."\"".":[";

		$ctr=0;

		while($row = mysqli_fetch_assoc($result)){

			if($ctr){
				$out.=",";
			}
			$ctr++;

			$out.=("{"."\""."team_logo"."\"".":"."\"".$row['team_logo']."\"".","."\""."team_name"."\"".":"."\"".$row['team_name']."\"".","."\""."passage_time"."\"".":"."\"".$row['passage_time']."\"".","."\""."passage_picture"."\"".":"."\"".$row['passage_picture']."\"".","."\""."passage_content"."\"".":"."\"".$row['passage_content']."\"".","."\""."passage_id"."\"".":"."\"".$row['passage_id']."\"".","."\""."passage_title"."\"".":"."\"".$row['passage_titlee']."\""."}");	

		}

		$out.="],"."\""."success"."\"".":"."1"."}";

		echo $out;
	}
	else if($mode=="my"){

		$link = mysqli_connect(
		'localhost',
		'root',
		'',
		'iteam');

		mysqli_query($link,"set names 'utf8'");
		mysqli_query($link,"set character_set_client=utf8");
		mysqli_query($link,"set character_set_results=utf8");

		$id = $key_val['addition'];

		$sql = "SELECT team_id FROM userteamrelation WHERE user_stunum"."="."$id"." AND (user_team_relation = '3' or user_team_relation = '4')";

		$result = mysqli_query($link,$sql);

		$arrp=0;

		$arr=array();

		while($row = mysqli_fetch_assoc($result)){
			$arr[$arrp++]=$row['team_id'];
		}

		$out = "{"."\""."arr"."\"".":[";

		$ctr=0;

		for($i=0;$i<$arrp;$i++){

			$sql="SELECT * FROM teampassage WHERE team_id"."=".$arr[$i];

			$result = mysqli_query($link,$sql);

			while($row = mysqli_fetch_assoc($result)){

				if($ctr){
					$out.=",";
				}
				$ctr++;

				$out.=("{"."\""."team_logo"."\"".":"."\"".$row['team_logo']."\"".","."\""."team_name"."\"".":"."\"".$row['team_name']."\"".","."\""."passage_time"."\"".":"."\"".$row['passage_time']."\"".","."\""."passage_picture"."\"".":"."\"".$row['passage_picture']."\"".","."\""."passage_content"."\"".":"."\"".$row['passage_content']."\"".","."\""."passage_id"."\"".":"."\"".$row['passage_id']."\"".","."\""."passage_title"."\"".":"."\"".$row['passage_titlee']."\""."}");	
			}
		}

		$out.="],"."\""."success"."\"".":"."1"."}";

		echo $out;

	}
	else if($mode=="detail"){

		$link = mysqli_connect(
		'localhost',
		'root',
		'',
		'iteam');

		mysqli_query($link,"set names 'utf8'");
		mysqli_query($link,"set character_set_client=utf8");
		mysqli_query($link,"set character_set_results=utf8");

		$id = $key_val['addition'];

		$sql = "SELECT * FROM teampassage WHERE passage_id"."="."$id";

		$result = mysqli_query($link,$sql);

		$row = mysqli_fetch_assoc($result);

		$out="{"."\""."team_logo"."\"".":"."\"".$row['team_logo']."\"".","."\""."team_name"."\"".":"."\"".$row['team_name']."\"".","."\""."passage_time"."\"".":"."\"".$row['passage_time']."\"".","."\""."passage_picture"."\"".":"."\"".$row['passage_picture']."\"".","."\""."passage_content"."\"".":"."\"".$row['passage_content']."\"".","."\""."passage_id"."\"".":"."\"".$row['passage_id']."\"".","."\""."passage_title"."\"".":"."\"".$row['passage_titlee']."\""."}";

		echo $out;

	}
	
?>