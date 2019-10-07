package com.example.homecctv;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(Color.rgb(00,85,77));
        }

        ImageView loading = (ImageView)findViewById(R.id.loading_gif);
        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(loading);
        Glide.with(this).load(R.raw.loading).into(gifImage);

        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(), 1000);

    }

    private class splashhandler implements Runnable{
        public void run(){
            startActivity(new Intent(getApplication(), Splash.class));
            MainActivity.this.finish();
        }
    }
    public void onBackPressed(){

    }
}

