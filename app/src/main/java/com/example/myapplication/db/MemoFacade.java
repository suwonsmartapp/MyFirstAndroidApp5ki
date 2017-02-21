package com.example.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.models.Memo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junsuk on 2017. 2. 21..
 */

public class MemoFacade {

    private MemoDbHelper mDbHelper;

    public MemoFacade(Context context) {
        mDbHelper = new MemoDbHelper(context);
    }

    /**
     * 메모를 추가한다
     *
     * @param title    제목
     * @param contents 내용
     * @return 추가된 row 의 id, 만약 에러가 발생되면 -1
     */
    public long insert(String title, String contents) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // 이거 한 줄로 됨
//                db.execSQL("INSERT INTO memo (title, contents) VALUES ('" + title + "', '" + content + "')");

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(MemoContract.MemoEntry.COLUMN_NAME_TITLE, title);
        values.put(MemoContract.MemoEntry.COLUMN_NAME_CONTENTS, contents);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(MemoContract.MemoEntry.TABLE_NAME, null, values);
        return newRowId;
    }

    /**
     * 전체 메모 리스트
     *
     * @return 전체 메모
     */
    public List<Memo> getMemoList() {
        ArrayList<Memo> memoArrayList = new ArrayList<>();

        // DB에서 읽어오기
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // 이거랑 아래 코드랑 같다
//        Cursor cursor = db.rawQuery("SELECT * FROM memo", null);


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                MemoContract.MemoEntry._ID,
                MemoContract.MemoEntry.COLUMN_NAME_TITLE,
                MemoContract.MemoEntry.COLUMN_NAME_CONTENTS
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = MemoContract.MemoEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = {"My Title"};

        // How you want the results sorted in the resulting Cursor
        // ORDER BY _id DESC
        String sortOrder =
                MemoContract.MemoEntry._ID + " DESC";

        Cursor c = db.query(
                MemoContract.MemoEntry.TABLE_NAME,        // The table to query
                null,                                     // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (c != null) {
            // 커서를 Memo로 변환

            // 커서를 처음 항목이로 이동
//            c.moveToFirst();
            while (c.moveToNext()) {
                String title = c.getString(
                        c.getColumnIndexOrThrow(
                                MemoContract.MemoEntry.COLUMN_NAME_TITLE));
                String content = c.getString(
                        c.getColumnIndexOrThrow(
                                MemoContract.MemoEntry.COLUMN_NAME_CONTENTS
                        )
                );
                Memo memo = new Memo(title, content);
                memoArrayList.add(memo);
            }

            // 커서 닫기
            c.close();
        }
        return memoArrayList;
    }
}
