package com.example.homecctv;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class signup extends Activity {

    final int PICTURE_REQUEST_CODE = 100;
    EditText pw;
    Button sign_up, selectimg;
    TextView signup_imgname;
    ImageView img, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(Color.rgb(00,85,77));
        }
        pw = (EditText)findViewById(R.id.sign_pw);
        sign_up = (Button)findViewById(R.id.button_sign_up);
        selectimg = (Button)findViewById(R.id.button_select_image);
        signup_imgname = (TextView)findViewById(R.id.sign_imagename);
        img = (ImageView)findViewById(R.id.sign_img);
        back = findViewById(R.id.back0);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        selectimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICTURE_REQUEST_CODE);
            }
        });



        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pw.getText().toString().equals("111")){
                    Toast.makeText(signup.this,"회원가입에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplication(), Splash.class));
                }
                else{
                    Toast.makeText(signup.this,"회원가입에 실패하였습니다.\n주어진 양식을 충족시켜주십시오.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == PICTURE_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                img.setImageResource(0);
                signup_imgname.setText("");
                Uri uri = data.getData();
                ClipData clipData = data.getClipData();

                if(clipData!=null){
                    for(int i = 0 ; i < 3 ; i++){
                        if(i<clipData.getItemCount()){
                            Uri urione = clipData.getItemAt(i).getUri();
                            switch(i){
                                case 0:
                                    img.setImageURI(urione);
                                    break;
                            }
                        }
                    }
                }
                else if(uri!=null){
                    img.setImageURI(uri);
                }
            }

        }
    }
}
