package com.example.homecctv;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.lang.invoke.MethodHandles;

public class VideoList extends AppCompatActivity {
    ListView listView;
    VideoListAdapter videoAdapter;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videolist);
        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(Color.rgb(0,0,0));
        }

        listView = findViewById(R.id.listView);
        back = findViewById(R.id.back20);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        videoAdapter = new VideoListAdapter(this);
        listView.setAdapter(videoAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(VideoList.this, position+"번째 선택", Toast.LENGTH_SHORT).show();
                UserData.video_name = lookup.videoArr.get(position);


                //startActivity(new Intent(VideoList.this, video_view.class));
//                String path = "http://35.221.206.41:52274/loading/videoLoading/?videoName="+UserData.video_name;
//                Log.d("test",path);
//                Intent intent = new Intent();
//
//                intent.setAction(Intent.ACTION_VIEW);
//                File videoFile = new File(path);
//
//                Uri uriFromVideoFile = Uri.fromFile(videoFile);
//
//                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFromVideoFile);
//                }
//                else{
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(path)));
//                }
//                intent.setDataAndType(uriFromVideoFile.fromFile(videoFile), "application/pdf");
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                startActivity(intent);


            }
        });
    }

}
