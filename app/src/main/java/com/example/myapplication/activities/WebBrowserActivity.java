package com.example.myapplication.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class WebBrowserActivity extends AppCompatActivity {

    private EditText mUrlEditText;
    private WebView mWebView;

    private Button mSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_browser);

        mUrlEditText = (EditText) findViewById(R.id.url_edit);
        mWebView = (WebView) findViewById(R.id.web_view);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // 일반적
//        mWebView.setWebViewClient(new WebViewClient());

        // 기능 확장
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                Toast.makeText(WebBrowserActivity.this, "로딩 완료", Toast.LENGTH_SHORT).show();
            }
        });

        mSearchButton = (Button) findViewById(R.id.search_btn);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl(mUrlEditText.getText().toString());
            }
        });

        // 키보드 이벤트 검출
        mUrlEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 버튼을 코드로 누르기
                    mSearchButton.callOnClick();

                    // 키보드 내리기
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mUrlEditText.getWindowToken(), 0);
                }
                return true;
            }
        });

    }

    // 뒤로 가기 키 캐치
    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
