package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.utils.MyUtils;

public class CustomDesignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_design);
    }

    public void showToast(View view) {
        MyUtils.makeText(this, "나만의 토스트 ㅋㅋㅋ", Toast.LENGTH_SHORT).show();
    }

    public void showDialogActivity(View view) {
        Intent intent = new Intent(this, DialogThemeActivity.class);
        intent.putExtra("data", "ㅋㅋㅋㅋㅋㅋ");
        intent.putExtra("image", "ㅋㅋㅋㅋㅋㅋ");
        startActivity(intent);
    }

    public void showAlertDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,
                R.style.Theme_AppCompat_Dialog);
        View layout = getLayoutInflater().inflate(R.layout.activity_dialog_theme, null, false);
        ((TextView) layout.findViewById(R.id.text_view)).setText("ㅋㅋㅋㅋㅋㅋ");
        ((ImageView) layout.findViewById(R.id.image_view)).setImageResource(R.drawable.man);
        builder.setView(layout);
        builder.show();
    }
}
