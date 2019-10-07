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

public class lostpw extends Activity {
    EditText id;
    Button btn_lostpw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_pw);
        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(Color.rgb(00,85,77));
        }
        id = (EditText)findViewById(R.id.lost_pw_id);
        btn_lostpw = (Button)findViewById(R.id.button_lostpw);

        btn_lostpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.getText().toString().equals("abab")){
                    Toast.makeText(lostpw.this,"비밀번호는 1234 입니다.",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplication(), Splash.class));
                }
                else{
                    Toast.makeText(lostpw.this,"비밀번호 찾기에 실패하였습니다.\n입력한 정보와 일치하는 계정이 없습니다.",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
