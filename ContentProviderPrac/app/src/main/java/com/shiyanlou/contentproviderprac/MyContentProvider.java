package com.shiyanlou.contentproviderprac;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.lang.ref.PhantomReference;

public class MyContentProvider extends ContentProvider {

    //声明一个静态的URI Matcher对象
    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    //创建 两个常量，用于标记URI的类别
    private static final int STUDENTS = 1;
    private  static final int STUDENT = 2;

    private MyDatabaseHelper dbOpenHelper;
    static {
        matcher.addURI(Students.AUTHORITY, "students", STUDENTS);
        matcher.addURI(Students.AUTHORITY, "student/#", STUDENT);
    }



    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        // 获得数据库的实例
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        // 变量num用于记录待删除的记录数
        int num = 0;

        switch (matcher.match(uri))
        {
            // 如果URI的参数代表操作全部的学生信息数据项，则删除这些数据项
            case STUDENTS:
                num = db.delete("students", where, whereArgs);
                break;

            // 如果URI的参数代表操作指定的学生信息数据项，则需要解析出指定学生的ID
            case STUDENT:

                long id = ContentUris.parseId(uri);
                String whereClause = Students.Student._ID + "=" + id;
                // 同理，拼接操作语句
                if (where != null && !where.equals(""))
                {
                    whereClause = whereClause + " and " + where;
                }
                num = db.delete("students", whereClause, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri:" + uri);
        }
        // 调用notifyChange()方法，发出通知，表明数据已经被改变
        getContext().getContentResolver().notifyChange(uri, null);
        return num;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // 获得数据库的实例
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        switch (matcher.match(uri))
        {
            // 如果URI的参数代表操作全部的学生信息数据项
            case STUDENTS:
                // 插入学生相关数据，然后返回插入后的ID
                long rowId = db.insert("students", Students.Student._ID, values);
                // 如果插入成功，rowID肯定多于0个，所以返回其URI
                if (rowId > 0)
                {
                    // 在已存在的URI后面追加ID
                    Uri studentUri = ContentUris.withAppendedId(uri, rowId);
                    // 调用notifyChange()方法，发出通知，表明数据已经被改变
                    getContext().getContentResolver().notifyChange(studentUri, null);
                    return studentUri;
                }
                break;
            default :
                throw new IllegalArgumentException("Unknown Uri:" + uri);
        }
        return null;
    }

    @Override
    public boolean onCreate() {

        dbOpenHelper = new MyDatabaseHelper(this.getContext(), "myStudents.db", 1);
        return true;
    }

    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)) {
            case STUDENTS:
                return "vnd.android.cursor.dir/com.shiyanlou.students";

            case STUDENT:
                return "vnd.android.cursor.item/com.shiyanlou.student";

            default:
                throw new IllegalArgumentException("Unknown Uri:" + uri);
        }
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String where,
                        String[] whereArgs, String sortOrder) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        switch (matcher.match(uri))
        {
            // 如果URI的参数代表操作全部的学生信息数据项
            case STUDENTS:
                // 执行相应的查询
                return db.query("students", projection, where,
                        whereArgs, null, null, sortOrder);
            // 如果URI的参数代表操作指定某个学生的数据
            case STUDENT:
                // 如果URI的参数代表操作指定的学生信息数据项，则需要解析出指定学生的ID
                long id = ContentUris.parseId(uri);

                String whereClause = Students.Student._ID + "=" + id;

                // 如果之前的where表达式存在，则直接用它来拼接新的操作语句
                if (where != null && !"".equals(where))
                {
                    whereClause = whereClause + " and " + where;
                }

                // 执行查询，返回结果
                return db.query("students", projection, whereClause, whereArgs,
                        null, null, sortOrder);
            default:
                throw new IllegalArgumentException("Unknown Uri:" + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String where,
                      String[] whereArgs) {
        // 获得数据库的实例
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        int num = 0;
        switch (matcher.match(uri))
        {
            // 如果URI的参数代表操作全部的学生信息数据项
            case STUDENTS:
                // 更新指定的记录
                num = db.update("students", values, where, whereArgs);
                break;

            // 如果URI的参数代表操作指定的学生信息数据项，则需要解析出指定学生的ID
            case STUDENT:

                long id = ContentUris.parseId(uri);
                String whereClause = Students.Student._ID + "=" + id;
                // 同理，进行操作语句的拼接
                if (where != null && !where.equals(""))
                {
                    whereClause = whereClause + " and " + where;
                }
                num = db.update("students", values, whereClause, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri:" + uri);
        }

        // 调用notifyChange()方法，发出通知，表明数据已经被改变
        getContext().getContentResolver().notifyChange(uri, null);
        return num;
    }
}
