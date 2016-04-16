package shiyanlou.com.activitypractice;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    String msg = "Shiyanlou :";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(msg, "The function onCreate() was called!");
    }


    @Override
    protected void onStart() {
        super.onStart();

        Log.d(msg, "the function onStart() was called.");

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(msg, "The function onResume() was called.");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(msg, "the function onPause() was called.");

    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg, "The function onStop() was called.");
    }

    // 当Activity被销毁的时候会调用onDestroy()方法
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The function onDestroy() was called.");
    }

}
