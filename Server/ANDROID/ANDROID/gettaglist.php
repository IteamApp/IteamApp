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

	$out = "{"."\""."arr"."\"".":[";

	$sql="";

	if($mode=="team"){
		$sql = "SELECT * FROM teamtag";

	}
	else if($mode=="passage"){
		$sql = "SELECT * FROM passagetag";
	}

	$result=mysqli_query($link,$sql);

	$ctr=0;

	while($row = mysqli_fetch_assoc($result)){

		if($ctr){
			$out.=",";
		}
		$ctr++;

		$out.="{"."\""."tag_name"."\"".":"."\"".$row['tag_name']."\"".","."\""."tag_id"."\"".":"."\"".$row['tag_id']."\""."}";

	}

	$out.="],"."\""."success"."\"".":"."1"."}";

	echo $out;

?>