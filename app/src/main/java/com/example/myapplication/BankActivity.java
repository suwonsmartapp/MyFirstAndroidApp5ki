package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.managers.Bank;

public class BankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bank);


    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.open_account_btn:
                // 계좌 개설
                startActivity(new Intent(this, OpenAccountActivity.class));
                break;
            case R.id.account_check_btn:
                startActivity(new Intent(this, AccountCheckActivity.class));
                break;
            case R.id.admin_mode_btn:
                showAdminDialog();
                break;
            case R.id.end_btn:
                finish();
                break;
        }
    }

    private void showAdminDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_login, null, false);
        final EditText idEditText = (EditText) view.findViewById(R.id.id_edit);
        final EditText passwordEditText = (EditText) view.findViewById(R.id.password_edit);
        builder.setView(view);
        builder.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String id = idEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                Bank bank = Bank.newInstance();
                if (bank.isAdmin(id, password)) {
                    // 관리자라면
                    startActivity(new Intent(BankActivity.this, AdminModeActivity.class));
                }
            }
        });
        builder.setNegativeButton("닫기", null);
        builder.show();
    }
}
