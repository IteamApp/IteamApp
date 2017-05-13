<?php 

	error_reporting(E_ALL ^ E_NOTICE); 

	$key_val = $_POST['data'];

	$mode = $key_val['mode'];

	if($mode=='tag'){
		//undefined
	}
	else if($mode=='detail'){

		$id = $key_val['addition'];
		
		$link = mysqli_connect(
		'localhost',
		'root',
		'',
		'iteam');

		mysqli_query($link,"set names 'utf8'");
		mysqli_query($link,"set character_set_client=utf8");
		mysqli_query($link,"set character_set_results=utf8");

		$sql = "SELECT * FROM team WHERE team_id"."="."$id";

		$result = mysqli_query($link,$sql);

		mysqli_close($link);

 		$row = mysqli_fetch_assoc($result);

 		$out = "{".
 					"\""."team_name"."\"".":"."\"".$row['team_name']."\"".",".
 					"\""."team_brief"."\"".":"."\"".$row['team_brief']."\"".",".
 					"\""."team_tag"."\"".":"."["."\"".$row['team_tag1']."\"".",".
 												"\"".$row['team_tag2']."\"".",".
 												"\"".$row['team_tag3']."\"".",".
 												"\"".$row['team_tag4']."\""."]".",".
 					"\""."team_faculty"."\"".":"."\"".$row['team_faculty']."\"".",".
 					"\""."team_logo"."\"".":"."\"".$row['team_logo']."\"".",".
 					"\""."team_scale"."\"".":"."\"".$row['team_scale']."\"".",".
 					"\""."success"."\"".":"."1".
 				"}";

 		echo $out;

 	}
	else if($mode=='faculty'){
		
		$faculty = $key_val['addition'];

		$link = mysqli_connect(
		'localhost',
		'root',
		'',
		'iteam');

		mysqli_query($link,"set names 'utf8'");
		mysqli_query($link,"set character_set_client=utf8");
		mysqli_query($link,"set character_set_results=utf8");

		$sql = "SELECT * FROM team WHERE team_faculty"."="."$faculty";

		$result = mysqli_query($link,$sql);

		mysqli_close($link);

		$outstart="{"."\""."arr"."\"".":"."[";

		$outend="],"."\""."success"."\"".":"."\""."1"."\""."}";

		$outcontent = "";

		$ctr=0;

		while($row = mysqli_fetch_assoc($result)){

			if($ctr){
				$outcontent.=",";
			}
			$ctr++;
			$outcontent.=("{"."\""."team_name"."\"".":"."\"".$row['team_name']."\"".","."\""."team_logo"."\"".":"."\"".$row['team_logo']."\"".","."\""."team_id"."\"".":"."\"".$row['team_id']."\""."}");
		}

		$out = $outstart.$outcontent.$outend;

		echo $out;

	}

?>