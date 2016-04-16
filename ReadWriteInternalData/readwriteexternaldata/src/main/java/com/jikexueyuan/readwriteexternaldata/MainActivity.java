package com.jikexueyuan.readwriteexternaldata;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView;

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

        editText = (EditText) findViewById(R.id.et_file_content);
        textView = (TextView) findViewById(R.id.show_content);

        findViewById(R.id.writeBtn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                File sdcard = Environment.getExternalStorageDirectory();
                File myfile = new File(sdcard, "myfile.txt");

                if (sdcard.exists()) {
                    try {
                        if (!myfile.exists()) {
                            myfile.createNewFile();
                        }
                        Toast.makeText(getApplicationContext(), "文件创建完成", Toast.LENGTH_SHORT).show();
                        FileOutputStream fos = new FileOutputStream(myfile);
                        OutputStreamWriter osw = new OutputStreamWriter(fos);
                        osw.write(editText.getText().toString());
                        osw.flush();
                        osw.close();
                        fos.close();
                        Toast.makeText(getApplicationContext(), "写入成功！", Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "当前系统不具备SD卡目录", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
}
