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

	$mode = $_GET['mode'];

	if($mode=='tag'){
		//undefined
	}
	else if($mode=='detail'){

		$id = $_GET['addition'];
		
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
 					"\""."team_time"."\"".":"."\"".$row['team_time']."\"".",".
 					"\""."success"."\"".":"."1".
 				"}";

 		echo $out;

 	}
	else if($mode=='faculty'){
		
		$faculty = $_GET['addition'];

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
	else if($mode=="my"){
		
		$stu_id = $_GET['addition'];
		
		$link = mysqli_connect(
		'localhost',
		'root',
		'',
		'iteam');

		mysqli_query($link,"set names 'utf8'");
		mysqli_query($link,"set character_set_client=utf8");
		mysqli_query($link,"set character_set_results=utf8");
		
		$sql = "SELECT team_id FROM userteamrelation WHERE user_stunum"."="."$stu_id AND (user_team_relation"."="."4 OR user_team_relation"."="."3)";

		$result=mysqli_query($link,$sql);

		$out="{"."\""."arr"."\"".":"."[";

		$ctr=0;

		while($row = mysqli_fetch_assoc($result)){
			
			$tid = $row['team_id'];

			$subsql = "SELECT * FROM team WHERE team_id"."="."$tid";

			$subresult = mysqli_query($link,$subsql);

			$subrow = mysqli_fetch_assoc($subresult);

			if($ctr){
				$outcontent.=",";
			}
			$ctr++;

			$out.=("{"."\""."team_name"."\"".":"."\"".$subrow['team_name']."\"".","."\""."team_logo"."\"".":"."\"".$subrow['team_logo']."\"".","."\""."team_id"."\"".":"."\"".$subrow['team_id']."\""."}");
		}

		$out.="],"."\""."success"."\"".":"."\""."1"."\""."}";
		echo $out;
	}
	else if($mode=="follow"){
		$stu_id = $_GET['addition'];
		
		$link = mysqli_connect(
		'localhost',
		'root',
		'',
		'iteam');

		mysqli_query($link,"set names 'utf8'");
		mysqli_query($link,"set character_set_client=utf8");
		mysqli_query($link,"set character_set_results=utf8");
		
		$sql = "SELECT team_id FROM userteamrelation WHERE user_stunum"."="."$stu_id AND (user_team_relation"."="."1";

		$result=mysqli_query($link,$sql);

		$out="{"."\""."arr"."\"".":"."[";

		$ctr=0;

		while($row = mysqli_fetch_assoc($result)){
			
			$tid = $row['team_id'];

			$subsql = "SELECT * FROM team WHERE team_id"."="."$tid";

			$subresult = mysqli_query($link,$subsql);

			$subrow = mysqli_fetch_assoc($subresult);

			if($ctr){
				$outcontent.=",";
			}
			$ctr++;

			$out.=("{"."\""."team_name"."\"".":"."\"".$subrow['team_name']."\"".","."\""."team_logo"."\"".":"."\"".$subrow['team_logo']."\"".","."\""."team_id"."\"".":"."\"".$subrow['team_id']."\""."}");
		}

		$out.="],"."\""."success"."\"".":"."\""."1"."\""."}";
		echo $out;
	}
?>