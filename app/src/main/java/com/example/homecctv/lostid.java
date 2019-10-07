package com.example.homecctv;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class lostid extends Activity {
    EditText name;
    Button btn_lostid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_id);
        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(Color.rgb(00,85,77));
        }

        name = (EditText)findViewById(R.id.lost_id_name);
        btn_lostid = (Button)findViewById(R.id.button_lostid);

        btn_lostid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("홍길동")){
                    Toast.makeText(lostid.this,"아이디는 ababk 입니다.",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplication(), Splash.class));
                }
                else{
                    Toast.makeText(lostid.this,"아이디 찾기에 실패하였습니다.\n입력한 정보와 일치하는 계정이 없습니다.",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}