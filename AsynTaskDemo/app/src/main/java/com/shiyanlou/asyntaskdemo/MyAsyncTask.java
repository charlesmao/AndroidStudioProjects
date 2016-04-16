package com.shiyanlou.asyntaskdemo;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by AQ-DN-01-076B on 2016/4/6.
 */
public class MyAsyncTask extends AsyncTask<Integer, Integer, String> {

    private TextView txt;
    private ProgressBar pgbar;

    public MyAsyncTask(TextView txt, ProgressBar pgbar) {
        super();
        this.txt = txt;
        this.pgbar = pgbar;
    }

    //该方法不运行在UI线程中,主要用于异步操作,通过调用publishProgress()方法
    //触发onProgressUpdate对UI进行操作
    @Override
    protected String doInBackground(Integer... params) {

        DelayOperator dop = new DelayOperator();
        int i = 0;
        for (i = 10; i <= 100; i+=10) {
            dop.delay();
            publishProgress(i);
        }


        return i + params[i].intValue() + "";
    }

    @Override
    protected void onPreExecute() {
        txt.setText("开始执行异步线程~");
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int value = values[0];
        pgbar.setProgress(value);
    }
}
