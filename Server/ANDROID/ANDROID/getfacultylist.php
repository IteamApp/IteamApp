<?php

	error_reporting(E_ALL ^ E_NOTICE); 

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