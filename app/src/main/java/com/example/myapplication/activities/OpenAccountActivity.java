package com.example.myapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.managers.Bank;
import com.example.myapplication.models.Account;

public class OpenAccountActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_open_account);

        findViewById(R.id.open_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bank bank = Bank.newInstance();

        String id = ((EditText) findViewById(R.id.id_edit)).getText().toString();
        String password = ((EditText) findViewById(R.id.password_edit)).getText().toString();
        int balance = Integer.parseInt(((EditText) findViewById(R.id.balance_edit)).getText().toString());

        Account account = new Account(id, password, balance);
        bank.open(account);

        Toast.makeText(this, "개설 되었습니다", Toast.LENGTH_SHORT).show();
        finish();
    }
}
