package com.example.myapplication.models;

import java.util.Locale;

/**
 * Created by junsuk on 2017. 2. 8..
 */

public class Account {
    private String id;
    private String password;
    private int balace;

    public Account(String id, String password, int balace) {
        this.id = id;
        this.password = password;
        this.balace = balace;
    }

    public int getBalace() {
        return balace;
    }

    public void setBalace(int balace) {
        this.balace = balace;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(Locale.KOREA, "조회한 ID : %s\n조회한 계좌의 잔액 : %d", id, balace);
    }
}
