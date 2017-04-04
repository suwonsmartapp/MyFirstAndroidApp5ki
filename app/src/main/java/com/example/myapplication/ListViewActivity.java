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

import com.example.myapplication.activities.AsyncTaskActivity;
import com.example.myapplication.activities.BankActivity;
import com.example.myapplication.activities.BasketballActivity;
import com.example.myapplication.activities.BroadcastReceiverActivity;
import com.example.myapplication.activities.ChatActivity;
import com.example.myapplication.activities.ColorFragmentActivity;
import com.example.myapplication.activities.CustomDesignActivity;
import com.example.myapplication.activities.FragmentExam1Activity;
import com.example.myapplication.activities.GalleryActivity;
import com.example.myapplication.activities.GeoIpActivity;
import com.example.myapplication.activities.ImageFragmentActivity;
import com.example.myapplication.activities.LifeCycleActivity;
import com.example.myapplication.activities.ListViewExamActivity;
import com.example.myapplication.activities.LoginActivity;
import com.example.myapplication.activities.MainActivity;
import com.example.myapplication.activities.MapsActivity;
import com.example.myapplication.activities.MemoActivity;
import com.example.myapplication.activities.MusicPlayerActivity;
import com.example.myapplication.activities.NaverTranslateApiActivity;
import com.example.myapplication.activities.NavigationDrawerActivity;
import com.example.myapplication.activities.PaletteActivity;
import com.example.myapplication.activities.NewsXMLActivity;
import com.example.myapplication.activities.RealmExamActivity;
import com.example.myapplication.activities.RecyclerViewActivity;
import com.example.myapplication.activities.ScrollingActivity;
import com.example.myapplication.activities.ServiceActivity;
import com.example.myapplication.activities.SettingsActivity;
import com.example.myapplication.activities.ThreadActivity;
import com.example.myapplication.activities.ViewPagerActivity;
import com.example.myapplication.activities.ViewPagerExamActivity;
import com.example.myapplication.activities.WeatherActivity;
import com.example.myapplication.activities.WeatherApiActivity;

import java.util.ArrayList;
import java.util.Collections;
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
        addItem("GoogleMap 데모", "GoogleMap 데모", MapsActivity.class);
        addItem("네이버 기계번역 API 연습", "너무 힘들어", NaverTranslateApiActivity.class);
        addItem("갤러리", "CursorAdaper, Provider", GalleryActivity.class);
        addItem("스레드", "Thread, Handler", ThreadActivity.class);
        addItem("스레드", "AsyncTask", AsyncTaskActivity.class);
        addItem("네트워크 채팅", "멀티 Thread", ChatActivity.class);
        addItem("RecyclerView", "RecyclerView", RecyclerViewActivity.class);
        addItem("BroadcastReceiver", "로컬 브로드캐스트", BroadcastReceiverActivity.class);
        addItem("스크롤 테크닉", "CoordinatorLayout", ScrollingActivity.class);
        addItem("뮤직 플레이어", "아주대 218쪽", MusicPlayerActivity.class);
        addItem("NavigationDrawer", "기본 템플릿", NavigationDrawerActivity.class);
        addItem("Service", "IntentService, Service, bindService", ServiceActivity.class);
        addItem("로그인", "PHP와 연동", LoginActivity.class);
        addItem("로그인", "Realm", RealmExamActivity.class);
        addItem("커스텀 디자인", "Toast", CustomDesignActivity.class);
        addItem("Palette", "Palette", PaletteActivity.class);
        addItem("뉴스", "XML 파싱", NewsXMLActivity.class);
        addItem("설정화면", "SettingsActivity", SettingsActivity.class);

        Collections.reverse(mDataList);

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
