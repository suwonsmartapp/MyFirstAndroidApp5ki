package com.example.myapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.models.User;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class RealmExamActivity extends AppCompatActivity implements RealmChangeListener<Realm> {

    private EditText mEmail;
    private EditText mPassword;
    private EditText mNewPassword;
    private TextView mResultText;

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaml_exam);

        mEmail = (EditText) findViewById(R.id.email_edit);
        mPassword = (EditText) findViewById(R.id.password_edit);
        mNewPassword = (EditText) findViewById(R.id.new_password_edit);
        mResultText = (TextView) findViewById(R.id.result_text);

        mRealm = Realm.getDefaultInstance();
        mRealm.addChangeListener(this);

        showResult();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mRealm.removeAllChangeListeners();
        mRealm.close();
    }

    public void SignIn(View view) {
        if (mRealm.where(User.class)
                .equalTo("email", mEmail.getText().toString())
                .equalTo("password", mPassword.getText().toString())
                .count() > 0) {
            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show();
        }
    }

    public void SignUp(View view) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm.where(User.class)
                        .equalTo("email", mEmail.getText().toString())
                        .count() == 0) {

                    User user = realm.createObject(User.class);
                    user.setEmail(mEmail.getText().toString());
                    user.setPassword(mPassword.getText().toString());
                }
            }
        });
    }

    public void updatePassword(View view) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User user = realm.where(User.class)
                        .equalTo("email", mEmail.getText().toString())
                        .findFirst();

                user.setPassword(mNewPassword.getText().toString());
            }
        });
    }

    public void deleteAccount(View view) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(User.class)
                        .equalTo("email", mEmail.getText().toString())
                        .findAll()
                        .deleteAllFromRealm();
            }
        });
    }

    private void showResult() {
        RealmResults<User> results = mRealm.where(User.class).findAll();
        mResultText.setText(results.toString());
    }

    @Override
    public void onChange(Realm element) {
        // DB가 갱싱 될 때 마다 호출
        showResult();
    }
}
