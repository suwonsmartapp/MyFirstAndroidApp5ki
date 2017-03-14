package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.login.ResultModel;
import com.example.myapplication.login.RetrofitUtil;
import com.example.myapplication.login.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private UserApi mUserApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        mUserApi = new RetrofitUtil().getUserApi();

        mEmailSignInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Call<ResultModel> call = mUserApi.login(mEmailView.getText().toString(),
                mPasswordView.getText().toString());

        // 비동기 네트워크 처리
        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                // 정상 결과
                ResultModel result = response.body();
                if (result.getResult().equals("ok")) {
                    // 성공
                    startActivity(new Intent(LoginActivity.this, MemoActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                // 네트워크 문제
                Toast.makeText(LoginActivity.this, "네트워크 연결 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

