package com.example.myapplication.activities;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityDataBindingBinding;
import com.example.myapplication.databinding.FragmentDataBindingBinding;

public class DataBindingActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityDataBindingBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_data_binding);

        mBinding.button10.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    public static class MyFragment extends Fragment {

        private FragmentDataBindingBinding mBinding;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            mBinding = DataBindingUtil.inflate(inflater,
                    R.layout.fragment_data_binding,
                    container,
                    false);
            return mBinding.getRoot();
        }
    }
}
