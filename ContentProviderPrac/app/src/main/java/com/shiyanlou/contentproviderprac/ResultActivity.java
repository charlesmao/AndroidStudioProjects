package com.shiyanlou.contentproviderprac;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    //声明一个ListView对象用于显示查询结果
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
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

        //实例化listView对象
        listView = (ListView)findViewById(R.id.listView_result);

        Intent intent = getIntent();

        Bundle data = intent.getExtras();

        List<Map<String, String>> list = (List<Map<String, String>>)data.getSerializable("data");

        //将上面的List封装成SimpleAdapter对象，用于填充列表的数据

        SimpleAdapter adapter = new SimpleAdapter(ResultActivity.this , list, R.layout.item ,
                new String[]{"student" , "information"}, new int[]{R.id.editText_item_student, R.id.editText_item_information});

        listView.setAdapter(adapter);
    }

}
