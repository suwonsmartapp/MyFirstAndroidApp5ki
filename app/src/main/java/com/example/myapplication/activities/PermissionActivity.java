package com.example.myapplication.activities;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class PermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
    }

    public void checkPermission(View view) {

        new TedPermission(this)
                .setRationaleMessage("이 기능은 외부 저장소에 접근 권한이 필요합니다.")
                .setDeniedMessage("설정 메뉴에서 언제든지 권한을 변경할 수 있습니다")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        // 기능 수행
                        method();
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                    }
                }).check();
    }

    private void method() {

    }
}
