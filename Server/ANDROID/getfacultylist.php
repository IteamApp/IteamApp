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

	$link = mysqli_connect(
		'localhost',
		'root',
		'',
		'iteam');

	mysqli_query($link,"set names 'utf8'");
	mysqli_query($link,"set character_set_client=utf8");
	mysqli_query($link,"set character_set_results=utf8");

	$sql = "SELECT * FROM facultylist";

	$result = mysqli_query($link,$sql);

	mysqli_close($link);

	$out = "{"."\""."arr"."\"".":[";

	$ctr=0;

	while($row = mysqli_fetch_assoc($result)){

		if($ctr){
			$out.=",";
		}
		$ctr++;

		$out.="{"."\""."faculty_name"."\"".":"."\"".$row['faculty_name']."\"".","."\""."faculty_teamnum"."\"".":"."\"".$row['faculty_teamnum']."\"".","."\""."faculty_id"."\"".":"."\"".$row['faculty_id']."\""."}";

	}
	$out.="],"."\""."success"."\"".":"."1"."}";

	echo $out;

?>