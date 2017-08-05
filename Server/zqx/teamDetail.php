<?php error_reporting(E_ALL ^ E_DEPRECATED);
 


/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
if (isset($_GET['id']) ){
 
    $id = $_GET['id'];
   
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
     
    // connecting to db
    $db = new DB_CONNECT();

    mysql_query("set names utf8");
     
    // get all products from products table
    $result = mysql_query("SELECT *FROM team where team_id=$id") or die(mysql_error());
     
    // check for empty result
    if (mysql_num_rows($result) > 0) {
        // looping through all results
        // products node
     
        while ($row = mysql_fetch_array($result)) {
            // temp user array
            $response = array();
            $response["team_faculty"] = $row["team_faculty"];
            $team_faculty = mysql_query("SELECT *FROM facultylist where faculty_id='{$response["team_faculty"]}'") or die(mysql_error());

             while ($row2 = mysql_fetch_array($team_faculty)) {
                $response["team_faculty"] = $row2["faculty_name"];
            }




            $response["team_brief"] = $row["team_brief"];
            
            $response["team_name"] = $row["team_name"];
            $response["team_logo"] = $row["team_logo"];

            // push single product into final response array

        }
        // success
        $response["success"] = 1;
    } 
    else {
        // no products found
        $response["success"] = 0;
        $response["message"] = "No article found";
    }
}

else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
}
 
    // echo no users JSON
    echo json_encode($response);
?>