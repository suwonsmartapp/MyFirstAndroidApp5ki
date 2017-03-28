package com.example.myapplication.activities;

import android.app.ListActivity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ItemNewsBinding;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsXMLActivity extends ListActivity {

    private OkHttpClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mClient = new OkHttpClient();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url("https://news.google.co.kr/news?cf=all&hl=ko&pz=1&ned=kr&output=rss")
                            .build();
                    Response response = null;
                    response = mClient.newCall(request).execute();
                    final String xml = response.body().string();

                    // 파싱
                    final List<News> data = parse(xml);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 어댑터 꽂아주기
                            NewsAdapter adapter = new NewsAdapter(data);
                            setListAdapter(adapter);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private List<News> parse(String xml) {
        try {
            return new NewsParser().parse(xml);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class News {
        String title;
        String link;
        String pubDate;
        String category;
    }

    private static class NewsAdapter extends BaseAdapter {

        private List<News> mData;
        private ItemNewsBinding mmBinding;

        public NewsAdapter(List<News> data) {
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
            ViewHolder holder;
            if (mmBinding != null) {
                mmBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_news, parent, false);

                holder = new ViewHolder();

                holder.titleTextView = mmBinding.titleText;
                holder.dateTextView = mmBinding.dateText;
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            News news = (News) getItem(position);
            holder.titleTextView.setText(news.title);
            holder.dateTextView.setText(news.pubDate);

            return convertView;
        }
    }

    private static class ViewHolder {
        TextView titleTextView;
        TextView dateTextView;
    }

    private static class NewsParser {
        public List<News> parse(String xml) throws XmlPullParserException {
            XmlPullParser parser = Xml.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);


            return null;
        }
    }
}
