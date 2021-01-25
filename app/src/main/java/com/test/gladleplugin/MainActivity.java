package com.test.gladleplugin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.TraceCompat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.test.allen.AllenLog;

public class MainActivity extends AppCompatActivity {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    private static final int corePoolSize = Math.max(2, Math.min(CPU_COUNT -1, 4));

    private static final int maximunPoolSize = CPU_COUNT * 2 + 1;

    public static final String TAG = "MainActivity";

    private ImageView imageView;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        TraceCompat.beginSection("begin");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setImage();

            }
        });

        imageView = findViewById(R.id.iv_image);

        imageView.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {

            }
        });

        Glide.with(this).load(Uri.parse("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2432834852,1155554581&fm=26&gp=0.jpg")).into(imageView);

        TraceCompat.endSection();
        Log.d(TAG, "onCreate: "+ System.currentTimeMillis());
    }

    @AllenLog
    private void setImage() {

        Glide.with(this).load(Uri.parse("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2432834852,1155554581&fm=26&gp=0.jpg")).into(imageView);
    }

    private void test(int s) {

        Log.i(TAG, "value:  "+s);

    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}