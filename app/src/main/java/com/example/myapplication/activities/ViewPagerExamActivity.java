package com.example.myapplication.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.fragments.ListViewFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerExamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_exam);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(ListViewFragment.newInstance(createCharacterList('a', 'z')));
        fragmentList.add(ListViewFragment.newInstance(createCharacterList('A', 'Z')));
        fragmentList.add(ListViewFragment.newInstance(createCharacterList('ㄱ', 'ㅎ')));
        fragmentList.add(ListViewFragment.newInstance(createCharacterList('0', '9')));

        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(),
                fragmentList,
                new String[]{"영어 소문자", "영어 대문자", "한글", "숫자"});
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    private List<String> createCharacterList(char start, char end) {
        List<String> list = new ArrayList<>();
        char ch = start;
        for (char i = ch; i <= end; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    private static class MyAdapter extends FragmentPagerAdapter {
        private List<Fragment> mmFragmentList;
        private String[] mmPageTitles;

        public MyAdapter(FragmentManager fm,
                         List<Fragment> fragmentList,
                         String[] pageTitles) {
            super(fm);
            mmFragmentList = fragmentList;
            mmPageTitles = pageTitles;
        }

        @Override
        public Fragment getItem(int position) {
            return mmFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mmFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mmPageTitles[position];
        }
    }
}
