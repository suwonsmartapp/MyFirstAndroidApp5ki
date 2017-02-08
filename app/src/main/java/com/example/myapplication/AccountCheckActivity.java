package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.managers.Bank;
import com.example.myapplication.models.Account;

public class AccountCheckActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mIdEditText;
    private EditText mPasswordEditText;
    private TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account_check);

        mIdEditText = (EditText) findViewById(R.id.id_edit);
        mPasswordEditText = (EditText) findViewById(R.id.password_edit);
        mResultTextView = (TextView) findViewById(R.id.result_text);


        findViewById(R.id.login_button).setOnClickListener(this);
        findViewById(R.id.close_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                Bank bank = Bank.newInstance();

                String id = mIdEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();

                Account account = bank.login(id, password);
                if (account != null) {
                    mResultTextView.setText(account.toString());
                }

                break;
            case R.id.close_btn:
                finish();
                break;
        }
    }
}
