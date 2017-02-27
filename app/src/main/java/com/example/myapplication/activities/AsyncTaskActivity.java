package com.example.myapplication.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;

public class AsyncTaskActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        mTextView = (TextView) findViewById(R.id.text_view);

        // 제약사항
        // 1. AsyncTask 클래스는 반드시 UI 스레드에서 로드 해야 된다
        // 2. AsyncTask 인스턴스는 반드시 UI 스레드에서 생성해야 된다
        // 3. execute() 도 반드시 UI 스레드에서 호출해야 된다
        // 4. 모든 콜백들은 직접 호출하면 안된다
        // 5. 태스크 인스턴스는 한번만 실행할 수 있다.
        MyAsyncTask task = new MyAsyncTask();
        task.execute(0);
        task.cancel(true);

//        // 일반적인 사용법
//        // 순차적으로 실행
//        new MyAsyncTask().execute(0);
//        new MyAsyncTask().execute(0);
//        new MyAsyncTask().execute(0);
//
//        // 병렬로 수행되는 AsyncTask
//        new MyAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 0);
//        new MyAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 0);


    }

    public void progressClick(View view) {
//        new ProgressTask(this).execute();
        DownloadDialogFragment fragment =
                DownloadDialogFragment.newInstance("처리 중 입니다");

        fragment.show(getSupportFragmentManager(), "download");

    }

    public void downloadClick(View view) {
        new DownloadTask(this).execute();
    }

    public static class DownloadDialogFragment extends DialogFragment {

        public static DownloadDialogFragment newInstance(String message) {
            DownloadDialogFragment fragment = new DownloadDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString("message", message);
            fragment.setArguments(bundle);
            return fragment;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String message = getArguments().getString("message");

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(message);
            return builder.create();
        }
    }

    private class DownloadTask extends AsyncTask<Void, Integer, Void> {
        private final Context mContext;
        private ProgressDialog mDialog;

        public DownloadTask(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            // 다이얼로그 보이기
            mDialog = new ProgressDialog(mContext);
            mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mDialog.setMessage("처리 중 입니다 ...");
            mDialog.create();
            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(100);

                    // UI 갱신
                    publishProgress(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // 다이얼로그 숨기기
            if (mDialog != null) {
                mDialog.dismiss();
            }
        }
    }

    private class ProgressTask extends AsyncTask<Void, Void, Void> {
        private final Context mContext;
        private ProgressDialog mDialog;

        public ProgressTask(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            // 다이얼로그 보이기
            mDialog = new ProgressDialog(mContext);
            mDialog.setMessage("처리 중 입니다 ...");
            mDialog.create();
            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // 다이얼로그 숨기기
            mDialog.dismiss();
        }
    }

    private class MyAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            // 최초 실행 되는 부분
        }

        @WorkerThread
        @Override
        protected Integer doInBackground(Integer... params) {
            // 오래 걸리는 처리
            int number = params[0];
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                number++;

                // UI 갱신
                publishProgress(number);    // onProgressUpdate로 넘어감
            }
            return number;  // onPostExecute로 넘어감
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // UI 갱신
            mTextView.setText(values[0] + "");
        }

        @Override
        protected void onPostExecute(Integer integer) {
            // 오래걸리는 처리가 끝난 후 호출
            Log.d("AsyncTask", "onPostExecute: " + integer);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            // 취소 처리
        }
    }
}
