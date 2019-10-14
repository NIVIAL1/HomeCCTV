package com.example.homecctv;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class withdrawal extends Activity {
    EditText pw;
    Button btn_withdrawl;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withdrawal);
        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(Color.rgb(00,85,77));
        }

        pw = (EditText)findViewById(R.id.withdrawal_pw);
        btn_withdrawl = (Button)findViewById(R.id.button_withdrawal);
        back = findViewById(R.id.back7);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_withdrawl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pw.getText().toString().equals("111")){
                    Toast.makeText(withdrawal.this,"회원 탈퇴에 성공했습니다.\n초기화면으로 돌아갑니다.",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplication(), Splash.class));
                    withdrawal.this.finish();
                }
                else{
                    Toast.makeText(withdrawal.this,"회원 탈퇴에 실패하였습니다.\n비밀번호를 다시 입력해 주십시오.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
