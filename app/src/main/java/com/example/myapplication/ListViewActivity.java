package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.activities.BankActivity;
import com.example.myapplication.activities.BasketballActivity;
import com.example.myapplication.activities.ColorFragmentActivity;
import com.example.myapplication.activities.FragmentExam1Activity;
import com.example.myapplication.activities.GeoIpActivity;
import com.example.myapplication.activities.ImageFragmentActivity;
import com.example.myapplication.activities.LifeCycleActivity;
import com.example.myapplication.activities.ListViewExamActivity;
import com.example.myapplication.activities.MainActivity;
import com.example.myapplication.activities.MemoActivity;
import com.example.myapplication.activities.ViewPagerActivity;
import com.example.myapplication.activities.ViewPagerExamActivity;
import com.example.myapplication.activities.WeatherActivity;
import com.example.myapplication.activities.WeatherApiActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayList<Map<String, Object>> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        // 뷰
        mListView = (ListView) findViewById(R.id.list_view);

        // 데이터
        mDataList = new ArrayList<>();
        addItem("농구 점수판", "Button, OnClickListener 연습", BasketballActivity.class);
        addItem("커피앱", "CheckBox", MainActivity.class);
        addItem("날씨앱", "모델클래스를 활용하여 BaseAdapter 연습", WeatherActivity.class);
        addItem("메모장", "연습문제", MemoActivity.class);
        addItem("은행 예제", "연습문제", BankActivity.class);
        addItem("LifeCycle", "생명주기", LifeCycleActivity.class);
        addItem("Fragment", "ColorFragment", ColorFragmentActivity.class);
        addItem("리스트뷰 연습", "리스트뷰 연습", ListViewExamActivity.class);
        addItem("프래그먼트 연습", "프래그먼트 연습1", FragmentExam1Activity.class);
        addItem("프래그먼트 콜백 연습", "콜백", ImageFragmentActivity.class);
        addItem("ViewPager", "", ViewPagerActivity.class);
        addItem("ViewPager 연습문제", "TabLayout + ViewPager", ViewPagerExamActivity.class);
        addItem("날씨앱 API버전", "API 사용 연습", WeatherApiActivity.class);
        addItem("FreeGeoIp", "API 사용 연습", GeoIpActivity.class);

        // 어댑터
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1,
//                mDataList);

//        SimpleAdapter adapter = new SimpleAdapter(this,
//                mDataList,
//                android.R.layout.simple_list_item_2,
//                new String[]{"title", "desc"},
//                new int[]{android.R.id.text1, android.R.id.text2});

        MyAdapter adapter = new MyAdapter(mDataList);

        mListView.setAdapter(adapter);

        // 이벤트
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
                Intent intent = (Intent) map.get("intent");
                startActivity(intent);
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListViewActivity.this, "롱클릭" + position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });


    }

    private void addItem(String title, String desc, Class cls) {
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("desc", desc);
        map.put("intent", new Intent(this, cls));
        mDataList.add(map);
    }

    private static class MyAdapter extends BaseAdapter {


        private final List<Map<String, Object>> mData;

        public MyAdapter(List<Map<String, Object>> data) {
            mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(android.R.layout.simple_list_item_2, parent, false);
            }

            TextView text1 = (TextView) convertView.findViewById(android.R.id.text1);
            TextView text2 = (TextView) convertView.findViewById(android.R.id.text2);

            Map<String, Object> item = mData.get(position);

            text1.setText((String) item.get("title"));
            text2.setText((String) item.get("desc"));

            return convertView;
        }
    }
}
