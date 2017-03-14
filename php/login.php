<?php 
    // DB 접속
    mysql_connect("localhost", "student", "student");
    mysql_select_db("student");
    mysql_query("set names utf8");
    
    $email = $_GET['email'];
    $password = $_GET['password'];

    $sql = "SELECT * FROM User WHERE email='$email' AND password='$password'";

    $result = mysql_query($sql);
    $num_rows = mysql_num_rows($result);

    $result;
    if ($num_rows == 1) {
        $result = array('result' => 'ok');
    } else {
        $result = array('result' => 'error');
    }
    
    // json 으로 출력
    header('Content-type: application/json');
    echo json_encode($result);

    mysql_free_result($result);
    mysql_close();
​
?>