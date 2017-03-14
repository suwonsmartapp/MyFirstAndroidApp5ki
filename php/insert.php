<?php 
    // 새로운 컨텐츠 추가
    mysql_connect("localhost", "student", "student");
    mysql_select_db("student");
    mysql_query("set names utf8");
    
    $email = $_GET[email];
    $nickname = $_GET[nickname];
    $password = $_GET[password];

    $result;

    if ($email == "" || $nickname == "" || $password == "") {
        $result = array('result' => 'error', 
            'result_code' => '300', 
            'description' => '빈 값이 있음');
    } else {

        $sql = "INSERT INTO User (email, nickname, password) VALUES ('$email', '$nickname', '$password')";  
        
        if (!mysql_query($sql)) {
            $result = array('result' => 'error', 
                'result_code' => '200',
                'description' => '쿼리 실패');
        } else {
            $result = array('result' => 'ok', 
                'result_code' => '100',
                'description' => '쿼리 성공');
        }
    
    }

    header('Content-type: application/json');
    echo json_encode($result);

    mysql_close();

?>