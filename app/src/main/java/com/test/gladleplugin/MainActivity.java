package com.test.gladleplugin;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.test.allen.AllenLog;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setImage();
            }
        });

        imageView = findViewById(R.id.iv_image);
    }

    @AllenLog
    private void setImage() {

        Glide.with(this).load(Uri.parse("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2432834852,1155554581&fm=26&gp=0.jpg")).into(imageView);
    }

    private void test(int s) {

        Log.i(TAG, "value:  "+s);

    }
}