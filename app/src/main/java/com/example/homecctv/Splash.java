package com.example.homecctv;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Splash extends Activity {
    EditText id;
    EditText pw;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(Color.rgb(00,85,77));
        }

        Button btn_login = (Button)findViewById(R.id.button_login);
        TextView btn_signup = (TextView)findViewById(R.id.signup);
        TextView btn_lostid = (TextView)findViewById(R.id.lostid);
        TextView btn_lostpw = (TextView)findViewById(R.id.lostpw);
        id = (EditText)findViewById(R.id.user_id);
        pw = (EditText)findViewById(R.id.user_pw);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(getApplication(), signup.class));
            }
        });

        btn_lostid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), lostid.class));
            }
        });

        btn_lostpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), lostpw.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pw.getText().toString().equals("111")){
                    Toast.makeText(Splash.this,"로그인에 성공하였습니다.",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplication(), Main_screen.class));
                }
                else{
                    Toast.makeText(Splash.this,"로그인에 실패하였습니다.\n아이디와 비밀번호가 일치하지 않습니다.",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
