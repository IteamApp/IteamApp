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

	//$raw_data= $GLOBALS['HTTP_RAW_POST_DATA'];

	//$post_object = json_decode($raw_data);

	$key_val = $_POST['data'];

	$name = $key_val["user_name"];

	$stunum = $key_val["user_stunum"];

	$usersex = $key_val["user_sex"];

	$userbrief = $key_val["user_brief"];

	$userfaculty = $key_val["user_faculty"];

	$usercode = $key_val["user_code"];

	$link = mysqli_connect(
		'localhost',
		'root',
		'',
		'iteam');

	$sql = "INSERT INTO teamuser values('$name', '$stunum', '$usersex', '$userbrief', '', '', '$userfaculty', '$userbrief');";

	mysqli_query($link,"set names 'utf8'");
	mysqli_query($link,"set character_set_client=utf8");
	mysqli_query($link,"set character_set_results=utf8");

	$result = mysqli_query($link,$sql);

	mysqli_close($link);

	if($result){
		echo 1;
	}
	else{
		echo 0;
	}

?>