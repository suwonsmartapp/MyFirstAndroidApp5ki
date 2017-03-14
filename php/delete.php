<?php 
    mysql_connect("localhost", "student", "student");
    mysql_select_db("student");
    mysql_query("set names utf8");

    $email = $_GET[email];
    
    $sql = "DELETE FROM User WHERE email='$email'";

    $result;

    if (mysql_query($sql)) {
        // echo "ok";
        $result = array('result' => 'ok');
    } else {
        // echo "error";
        $result = array('result' => 'error');
    }

    header('Content-type: application/json');
    echo json_encode($result);
    
    mysql_close();
?>