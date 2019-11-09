package com.example.homecctv;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class withdrawal extends Activity {
    EditText pw, pw2;
    Button btn_withdrawl;
    ImageView back;
    private RequestQueue queue;
    JSONObject js = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withdrawal);
        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(Color.rgb(0,0,0));
        }

        pw = (EditText)findViewById(R.id.withdrawal_pw);
        pw2 = (EditText)findViewById(R.id.withdrawal_pw2);
        btn_withdrawl = (Button)findViewById(R.id.button_withdrawal);
        back = findViewById(R.id.back7);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        queue = Volley.newRequestQueue(this);
        String url_withdrawl = "http://35.221.206.41:52274/infoChange/delete";

        final StringRequest withdrawal_request= new StringRequest(Request.Method.POST, url_withdrawl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {               // 아이디찾기 성공 유무 보내주는 것 받음
                String result = null;

                try {
                    js = new JSONObject(response);
                    result = js.optString("result");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(result.equals("1")){             // 성공
                    Toast.makeText(withdrawal.this, "회원탈퇴에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplication(), Splash.class));

                }
                else {                              // 실패
                    Toast.makeText(withdrawal.this, "회원탈퇴에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",UserData.id);
                params.put("password",pw.getText().toString());
                Log.d("test",params.toString());
                return params;
            }
        };

        btn_withdrawl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pw.getText().toString().equals("") || pw2.getText().toString().equals("")){
                    Toast.makeText(withdrawal.this,"회원 탈퇴에 실패하였습니다.\n빈 칸을 모두 작성하여 주십시오.",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplication(), Splash.class));
                    withdrawal.this.finish();
                }
                else if(!pw.getText().toString().equals(pw2.getText().toString())){
                    Toast.makeText(withdrawal.this,"회원 탈퇴에 실패하였습니다.\n비밀번호와 비밀번호 확인을 일치시켜주십시오.",Toast.LENGTH_SHORT).show();
                }
                else if(!pw.getText().toString().equals(UserData.pw)){
                    Toast.makeText(withdrawal.this,"회원 탈퇴에 실패하였습니다.\n비밀번호를 다시 입력해 주십시오.",Toast.LENGTH_SHORT).show();
                }
                else{
                    queue.add(withdrawal_request);
                }
            }
        });

    }

}
