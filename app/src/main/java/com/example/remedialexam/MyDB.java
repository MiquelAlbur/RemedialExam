package com.example.remedialexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDB {

    private MyDatabaseHelper dbHelper;

    private SQLiteDatabase database;


    public final static String CON_TABLE = "Product"; // name of table
    public final static String CON_ID = "_id"; // id
    public final static String CON_NAME = "name";  // name
    public final static String COUNT = "count";

    /**
     * @param context
     */
    public MyDB(Context context) {
        dbHelper = new MyDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();

//        dbHelper.onCreate(database);//Per si volem reinciar la database
        //Primer insert
        ContentValues values = new ContentValues();
        values.put(CON_ID, 0);
        values.put(CON_NAME, "test");
        values.put(COUNT, "1");
        database.insert(CON_TABLE, null, values);
    }


    public long createRecords(String name, int count) {
        int id = nextId();
        ContentValues values = new ContentValues();
        values.put(CON_ID, id);
        values.put(CON_NAME, name);
        values.put(COUNT, count);
        return database.insert(CON_TABLE, null, values);
    }

    public long updateRecords(int id, String name, int count) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("count", count);
        return database.update(CON_TABLE, cv, "_id = ?", new String[]{String.valueOf(id)});
    }

    public void deleteItem(int s) {
        database.delete(CON_TABLE, "_id = ?", new String[]{String.valueOf(s)});
    }

    public Cursor selectRecords() {
        String[] cols = new String[]{CON_ID, CON_NAME, COUNT};
        Cursor mCursor;
        mCursor = database.query(true, CON_TABLE, cols, null
                , null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor; // iterate to get each value.
    }

    private int nextId() {
        String[] cols = new String[]{CON_ID};
        Cursor mCursor = database.query(true, CON_TABLE, cols, null
                , null, null, null, "_id desc", null);
        mCursor.moveToFirst();
        int id = mCursor.getInt(0) + 1;
        return id;
    }
}