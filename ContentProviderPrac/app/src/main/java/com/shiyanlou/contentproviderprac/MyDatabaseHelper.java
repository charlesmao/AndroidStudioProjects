package com.shiyanlou.contentproviderprac;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AQ-DN-01-076B on 2016/3/12.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    final String CREATE_TABLE_SQL = "create table students(_id integer primary key autoincrement, student, information)";

    public MyDatabaseHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("Method onUpdate has been called.OLD:" + oldVersion + "Changed into NEW:" + newVersion);
    }
}
