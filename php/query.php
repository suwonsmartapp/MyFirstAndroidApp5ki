<?php 
    // DB 접속
    mysql_connect("localhost", "student", "student");
    mysql_select_db("student");
    mysql_query("set names utf8");
    
    $sql = "SELECT * FROM User";

    $result = mysql_query($sql);
    
    $user = array();
    while ($row = mysql_fetch_object($result)) {
        $user[] = array(
            'nickname' => $row->nickname
            ,'email' => $row->email
            ,'password' => $row->password
            );
    }
    
    // json 으로 출력
    header('Content-type: application/json');
    echo json_encode($user);

    mysql_free_result($result);
    mysql_close();
​
?>