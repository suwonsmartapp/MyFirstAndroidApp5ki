package com.example.myapplication.activities;

import android.os.Bundle;
import android.support.annotation.WorkerThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, ReceiverThread.OnReceiveListener {

    public static final String NAME = "오준석2";
    private ListView mMessageListView;
    private EditText mMessageEditText;
    private SenderThread mThread1;

    private Socket mSocket = null;
    private ChatAdapter mAdapter;
    private ArrayList<Chat> mChatDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mMessageListView = (ListView) findViewById(R.id.list_view);
        mMessageEditText = (EditText) findViewById(R.id.message_edit);
        findViewById(R.id.send_button).setOnClickListener(this);

        mChatDataList = new ArrayList<>();
        mAdapter = new ChatAdapter(mChatDataList);
        mMessageListView.setAdapter(mAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {

                // 일단은 테스트 용으로 본인의 아이피를 입력해서 진행하겠습니다.
                try {
                    mSocket = new Socket("192.168.0.10", 7777);
                    // 두번째 파라메터 로는 본인의 닉네임을 적어줍니다.
                    mThread1 = new SenderThread(mSocket, NAME);
                    ReceiverThread thread2 = new ReceiverThread(mSocket);

                    thread2.setOnReceiveListener(ChatActivity.this);

                    mThread1.start();
                    thread2.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void onClick(View v) {
        mThread1.sendMessage(mMessageEditText.getText().toString());
        mMessageEditText.setText("");
    }

    @Override
    @WorkerThread
    public void onReceive(final String message) {

        String[] split = message.split(">");

        // ~~가 입장하셨습니다 처리 무시
        if (split.length < 2) {
            return;
        }

        String nickname = split[0];
        String msg = split[1];

        final Chat chat = new Chat();
        if (NAME.equals(nickname)) {
            // 나
            chat.isMe = true;
        } else {
            // 남
            chat.isMe = false;
        }
        chat.nickname = nickname;
        chat.message = msg;

        // UI 스레드로 실행
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 메세지 갱신
                mChatDataList.add(chat);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onDestroy() {
        try {
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    public static class Chat {
        public String message;
        public String nickname;
        public boolean isMe;
    }

    private static class ChatAdapter extends BaseAdapter {

        private final List<Chat> mData;

        public ChatAdapter(List<Chat> list) {
            mData = list;
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
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();

                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat, parent, false);

                viewHolder.layoutMe = (LinearLayout) convertView.findViewById(R.id.layout_me);
                viewHolder.layoutYou = (LinearLayout) convertView.findViewById(R.id.layout_you);
                viewHolder.nickname = (TextView) convertView.findViewById(R.id.nickname_text);
                viewHolder.bubbleYou = (TextView) convertView.findViewById(R.id.bubble_you_text);
                viewHolder.bubbleMe = (TextView) convertView.findViewById(R.id. bubble_me_text);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Chat chat = mData.get(position);

            if (chat.isMe) {
                viewHolder.bubbleMe.setText(chat.message);

                viewHolder.layoutMe.setVisibility(View.VISIBLE);
                viewHolder.layoutYou.setVisibility(View.GONE);
            } else {
                viewHolder.bubbleYou.setText(chat.message);
                viewHolder.nickname.setText(chat.nickname);

                viewHolder.layoutMe.setVisibility(View.GONE);
                viewHolder.layoutYou.setVisibility(View.VISIBLE);
            }

            convertView.setEnabled(false);

            return convertView;
        }
    }

    private static class ViewHolder {
        LinearLayout layoutMe;
        LinearLayout layoutYou;
        TextView nickname;
        TextView bubbleYou;
        TextView bubbleMe;
    }
}

class ReceiverThread extends Thread {

    interface OnReceiveListener {
        void onReceive(String message);
    }

    OnReceiveListener mListener;

    public void setOnReceiveListener(OnReceiveListener listener) {
        mListener = listener;
    }

    Socket socket;

    public ReceiverThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Log.d("receiver", "run: ");
            while (true) {
                String str = reader.readLine();

                if (mListener != null) {
                    mListener.onReceive(str);
                }
            }

        } catch (Exception e) {
            Log.e("chat", "run: " + e.getMessage());
        }
    }

}

/**
 * 메시지의 발신을 담당하는 스레드 입니다.
 */

class SenderThread extends Thread {
    Socket socket;
    String name;
    private PrintWriter mWriter;

    public SenderThread(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(final String message) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                mWriter.println(message);
                mWriter.flush();
            }
        }).start();
    }

    @Override
    public void run() {

        try {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            mWriter = new PrintWriter(socket.getOutputStream());

            // 제일 먼저 서버로 대화명을 송신합니다.
            mWriter.println(name);
            mWriter.flush();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

}