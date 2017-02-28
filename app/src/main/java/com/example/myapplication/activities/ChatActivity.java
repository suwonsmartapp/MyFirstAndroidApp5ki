package com.example.myapplication.activities;

import android.os.Bundle;
import android.support.annotation.WorkerThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, ReceiverThread.OnReceiveListener {

    private TextView mMessageTextView;
    private EditText mMessageEditText;
    private SenderThread mThread1;

    private Socket mSocket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mMessageTextView = (TextView) findViewById(R.id.message_text);
        mMessageEditText = (EditText) findViewById(R.id.message_edit);
        findViewById(R.id.send_button).setOnClickListener(this);

        new Thread(new Runnable() {
            @Override
            public void run() {

                // 일단은 테스트 용으로 본인의 아이피를 입력해서 진행하겠습니다.
                try {
                    mSocket = new Socket("192.168.0.10", 7777);
                    // 두번째 파라메터 로는 본인의 닉네임을 적어줍니다.
                    mThread1 = new SenderThread(mSocket, "오준석2");
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
    }

    @Override
    @WorkerThread
    public void onReceive(final String message) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 메세지 갱신
                mMessageTextView.append("\n" + message);
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