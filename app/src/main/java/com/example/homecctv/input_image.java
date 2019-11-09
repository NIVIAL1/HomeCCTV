package com.example.homecctv;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class input_image extends AppCompatActivity {

    final int PICTURE_REQUEST_CODE = 1;
    public String result = "hellow";
    ImageView image, back;
    TextView input_img_name;
    Button btn_input_image;
    public String path = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_image);
        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(Color.rgb(0,0,0));
        }

        Button MultiAlbumButton = (Button)findViewById(R.id.button_input_image);
        image = (ImageView)findViewById(R.id.img);
        input_img_name = (TextView)findViewById(R.id.input_image_name);
        btn_input_image = (Button)findViewById(R.id.button_input_img);
        back = findViewById(R.id.back4);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_input_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(path.equals(null)){
                    Toast.makeText(input_image.this,"이미지 입력에 실패하였습니다.\n사진을 선택해 주십시오.",Toast.LENGTH_SHORT).show();
                }
                else{

                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                AndroidUploader uploader = new AndroidUploader();
                                result = uploader.uploadPicture(path,UserData.id,UserData.name);
                            } catch (Exception e) {
                                Log.e(e.getClass().getName(), e.getMessage());
                            }
                        }
                    }).start();
                    Toast.makeText(input_image.this,"이미지 입력에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                    input_image.this.finish();
                }


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
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        if (requestCode == PICTURE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                image.setImageResource(0);
                input_img_name.setText("");
                final Uri uri = data.getData();
                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    for (int i = 0; i < 3; i++) {
                        if (i < clipData.getItemCount()) {
                            Uri urione = clipData.getItemAt(i).getUri();
                            Log.d("img",urione.toString());
                            switch (i) {
                                case 0:
                                    image.setImageURI(urione);
                                    Log.d("img",urione.toString());
                                    break;
                            }
                        }
                    }
                } else if (uri != null) {
                    path=getPath(uri);
                    image.setImageURI(uri);
                }
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }



}
