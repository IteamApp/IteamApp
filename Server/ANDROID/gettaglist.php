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