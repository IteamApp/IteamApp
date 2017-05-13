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

	/*$key_val = $_GET['data'];

	$key_val = json_decode($key_val);

	$key_val = object_to_array($key_val);*/

    $mode = $_GET['mode'];

    if($mode == "team"){

        $tid = $_GET['addition'];

        $sql = "SELECT user_stunum FROM userteamrelation WHERE team_id"."="."$tid AND (user_team_relation"."="."3 OR user_team_relation"."="."4)";

        $result = mysqli_query($link,$sql);

        $out="{"."\""."arr"."\"".":"."[";

        $ctr=0;

        while($row = mysqli_fetch_assoc($result)){
            
            $uid = $row['user_stunum'];

            $subsql = "SELECT * FROM teamuser WHERE user_stunum"."="."$uid";

            $subresult = mysqli_query($link,$subsql);

            $subrow = mysqli_fetch_assoc($subresult);

            if($ctr){
                $outcontent.=",";
            }
            $ctr++;

            $out.=("{"."\""."user_name"."\"".":"."\"".$subrow['user_name']."\"".",".
                       "\""."user_stunum"."\"".":"."\"".$subrow['user_stunum']."\"".",".
                       "\""."user_sex"."\"".":"."\"".$subrow['user_sex']."\"".",".
                       "\""."user_brief"."\"".":"."\"".$subrow['user_brief']."\"".",".
                       "\""."user_signature"."\"".":"."\"".$subrow['user_signature']."\"".",".
                       "\""."user_faculty"."\"".":"."\"".$subrow['user_faculty']."\"".",".
                       "\""."user_head"."\"".":"."\"".$subrow['user_head']."\""."}");
        }

        $out.="],"."\""."success"."\"".":"."\""."1"."\""."}";
        echo $out;

    }   
    else if($mode=="detail"){

        $uid =$_GET['addition'];

        $sql = "SELECT * FROM teamuser WHERE user_stunum"."="."$uid";

        $result=mysqli_query($link,$sql);

        $subrow=mysqli_fetch_assoc($result);

        $out = "{"."\""."user_name"."\"".":"."\"".$subrow['user_name']."\"".",".
                       "\""."user_stunum"."\"".":"."\"".$subrow['user_stunum']."\"".",".
                       "\""."user_sex"."\"".":"."\"".$subrow['user_sex']."\"".",".
                       "\""."user_brief"."\"".":"."\"".$subrow['user_brief']."\"".",".
                       "\""."user_signature"."\"".":"."\"".$subrow['user_signature']."\"".",".
                       "\""."user_faculty"."\"".":"."\"".$subrow['user_faculty']."\"".",".
                       "\""."user_head"."\"".":"."\"".$subrow['user_head']."\""."}";

        echo $out;
    }   

?>