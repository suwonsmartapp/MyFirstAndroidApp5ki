<?php 
    mysql_connect("localhost", "student", "student");
    mysql_select_db("student");
    mysql_query("set names utf8");

    $email = $_GET[email];
    $oldPass = $_GET[password];
    $newPass = $_GET[newpass];
    
    $sql = "UPDATE User SET password='$newPass' WHERE password='$oldPass' AND email='$email'";

    $result;

    if (mysql_query($sql)) {
        $result = array('result' => 'ok');
    } else {
        $result = array('result' => 'error');
    }

    header('Content-type: application/json');
    echo json_encode($result);
    
    mysql_close();
?>