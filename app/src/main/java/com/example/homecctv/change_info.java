package com.example.homecctv;

import android.app.Activity;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class change_info extends Activity {
    EditText pw;
    Button change_info;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(Color.rgb(00,85,77));
        }
        setContentView(R.layout.change_info);
        pw = (EditText)findViewById(R.id.change_pw);
        change_info = (Button)findViewById(R.id.button_change_info);
        back = findViewById(R.id.back3);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        change_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pw.getText().toString().equals("111")){
                    Toast.makeText(change_info.this,"정보 변경에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                    change_info.this.finish();
                }
                else{
                    Toast.makeText(change_info.this,"정보 변경에 실패하였습니다.\n 주어진 양식에 맞게 다시 작성해주십시오.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
