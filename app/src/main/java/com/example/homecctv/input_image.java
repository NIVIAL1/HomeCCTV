package com.example.homecctv;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class input_image extends AppCompatActivity {

    final int PICTURE_REQUEST_CODE = 100;
    ImageView image1, image2, image3;
    TextView input_img_name;
    Button btn_input_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_image);
        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(Color.rgb(00,85,77));
        }

        Button MultiAlbumButton = (Button)findViewById(R.id.button_input_image);
        image1 = (ImageView)findViewById(R.id.img1);
        image2 = (ImageView)findViewById(R.id.img2);
        image3 = (ImageView)findViewById(R.id.img3);
        input_img_name = (TextView)findViewById(R.id.input_image_name);
        btn_input_image = (Button)findViewById(R.id.button_input_img);

        btn_input_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(input_image.this,"이미지 입력에 실패하였습니다.\n사진을 다시 선택해 주십시오.",Toast.LENGTH_LONG).show();

            }
        });
        MultiAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICTURE_REQUEST_CODE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == PICTURE_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                image1.setImageResource(0);
                image2.setImageResource(0);
                image3.setImageResource(0);
                input_img_name.setText("");
                Uri uri = data.getData();
                ClipData clipData = data.getClipData();

                if(clipData!=null){
                    for(int i = 0 ; i < 3 ; i++){
                        if(i<clipData.getItemCount()){
                            Uri urione = clipData.getItemAt(i).getUri();
                            switch(i){
                                case 0:
                                    image1.setImageURI(urione);
                                    break;
                                case 1:
                                    image2.setImageURI(urione);
                                    break;
                                case 2:
                                    image3.setImageURI(urione);
                                    break;

                            }
                        }
                    }
                }
                else if(uri!=null){
                    image1.setImageURI(uri);
                }
            }

        }
    }




}
