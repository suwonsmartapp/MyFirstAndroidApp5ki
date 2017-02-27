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
     * 메모 리스트
     *
     * @param selection     조건절
     * @param selectionArgs 조건절에 매핑할 인자들
     * @param groupBy       Group by
     * @param having        having
     * @param orderBy       order by
     * @return 조건에 맞는 메모 리스트를 반환
     */
    public List<Memo> getMemoList(String selection,
                                  String[] selectionArgs,
                                  String groupBy,
                                  String having,
                                  String orderBy) {
        ArrayList<Memo> memoArrayList = new ArrayList<>();

        // DB에서 읽어오기
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String order = MemoContract.MemoEntry._ID + " DESC";

        Cursor c = db.query(
                MemoContract.MemoEntry.TABLE_NAME,        // The table to query
                null,                                     // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                groupBy,                                     // don't group the rows
                having,                                     // don't filter by row groups
                orderBy == null ? order : orderBy                                 // The sort order
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
                long id = c.getLong(
                        c.getColumnIndexOrThrow(
                                MemoContract.MemoEntry._ID
                        ));
                Memo memo = new Memo(title, content);
                memo.setId(id);
                memoArrayList.add(memo);
            }

            // 커서 닫기
            c.close();
        }
        return memoArrayList;
    }

    /**
     * 전체 메모 리스트
     *
     * @return 전체 메모
     */
    public List<Memo> getMemoList() {
        return getMemoList(null, null, null, null, null);
    }

    /**
     * 메모 삭제
     *
     * @param id 삭제할 메모 id
     * @return 삭제된 행의 수
     */
    public int delete(long id) {
        // Define 'where' part of query.
        String selection = MemoContract.MemoEntry._ID + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(id)};
        // Issue SQL statement.
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int deleted = db.delete(MemoContract.MemoEntry.TABLE_NAME,
                selection,
                selectionArgs);

        // 이 코드로만으로 위 코드를 대체할 수 있다
//        int deleted = db.delete(MemoContract.MemoEntry.TABLE_NAME,
//                "_id=" + id,
//                null);
        return deleted;
    }

    /**
     * 메모 수정
     *
     * @param id       수정할 메모 id
     * @param title    제목
     * @param contents 내용
     * @return 수정된 메모 수
     */
    public int update(long id, String title, String contents) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(MemoContract.MemoEntry.COLUMN_NAME_TITLE, title);
        values.put(MemoContract.MemoEntry.COLUMN_NAME_CONTENTS, contents);

        int count = db.update(
                MemoContract.MemoEntry.TABLE_NAME,
                values,
                MemoContract.MemoEntry._ID + "=" + id,
                null);

        return count;
    }
}
