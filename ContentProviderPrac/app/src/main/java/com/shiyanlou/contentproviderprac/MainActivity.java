package com.shiyanlou.contentproviderprac;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RunnableFuture;

public class MainActivity extends AppCompatActivity {

    MyDatabaseHelper dbHelper;

    Button button_insert;
    Button button_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        dbHelper = new MyDatabaseHelper(this, "MyStudents.db", 1);

        //实例化两个按钮
        button_insert = (Button)findViewById(R.id.button_insert);
        button_search = (Button)findViewById(R.id.button_search);

        //为按钮注册点击事件监听器
        button_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户在文本框上的输入信息
                String student = ((EditText)findViewById(R.id.editText_student)).getText().toString();

                String information = ((EditText)findViewById(R.id.editeText_information)).getText().toString();

                insertData(dbHelper.getReadableDatabase(), student, information);

                //显示一个Toast信息，提示用户已经添加成功了
                Toast.makeText(MainActivity.this, "Add student successfully!", Toast.LENGTH_LONG).show();
            }
        });

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户在文本框上输入的关键词
                String key = ((EditText)findViewById(R.id.editeText_keyword)).getText().toString();

                //利用游标对象，执行“行查询”，结果存入了游标对象中
                Cursor cursor = dbHelper.getReadableDatabase().rawQuery("select * from students where" +
                        " student like ? or information like ? ", new String[]{"%" + key + "%", "%"+key + "%"});

                Bundle data = new Bundle();

                data.putSerializable("data", CursorConverToList(cursor));

                //创建一个Intent来携带Bundle对象
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);

                intent.putExtras(data);
                //启动查询结果的Activity
                startActivity(intent);

            }
        });

    }

    protected ArrayList<Map<String, String>> CursorConverToList(Cursor cursor) {
        //创建一个数组列表用于存放查询结果
        ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();

        //在Cursor对象中遍历整个结果集
        while (cursor.moveToNext()) {
            //将结果集中的数据存入ArrayList对象里面
            Map<String, String> map = new HashMap<String, String>();

            //取出记录中第2、3列的值，即学生的名字和信息（第一列为id）

            map.put("student", cursor.getString(1));
            map.put("information", cursor.getString(2));

            result.add(map);
        }

        return result;
    }

    private void insertData(SQLiteDatabase db, String student, String information) {
        //执行插入语句，插入学生的姓名以及信息

        db.execSQL("insert into students values (null, ? , ?)", new String[]{student, information});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
