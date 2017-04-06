package com.example.myapplication.activities;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myapplication.R;

public class AnimationActivity extends AppCompatActivity {

    ImageView mImageView;
    EditText mEditText;

    Animation mShakeAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        mShakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
        mShakeAnimation.setInterpolator(new CycleInterpolator(7));

        mImageView = (ImageView) findViewById(R.id.image_view);
        mEditText = (EditText) findViewById(R.id.edit_text);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mEditText.startAnimation(mShakeAnimation);
            }
        });
    }

    public void transition(View view) {
        TransitionDrawable transitionDrawable = (TransitionDrawable) mImageView.getDrawable();
        transitionDrawable.startTransition(500);
    }

    public void animation1(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate);
        mImageView.startAnimation(animation);
    }

    public void animation2(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
        mImageView.startAnimation(animation);
    }
}
