package com.example.homecctv;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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



public class Splash extends Activity {
    EditText id,pw;
    private RequestQueue queue;
    JSONObject js = new JSONObject();
    Button btn_login;
    TextView btn_signup, btn_lostid, btn_lostpw;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(Color.rgb(00,85,77));
        }
        btn_signup = findViewById(R.id.signup);
        btn_lostid = findViewById(R.id.lostid);
        btn_lostpw = findViewById(R.id.lostpw);

        btn_login = findViewById(R.id.button_login);
        id = findViewById(R.id.user_id);
        pw = findViewById(R.id.user_pw);

        queue = Volley.newRequestQueue(this);

        String url_login = "http://35.221.206.41:52274/login";

        final StringRequest login_request = new StringRequest(Request.Method.POST, url_login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {               // 로그인 성공 유무 보내주는 것 받음
                String num = null;

                try {
                    js = new JSONObject(response);
                    num = js.optString("result");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(num.equals("1")){             // 성공
                    UserData.questionNum = Integer.parseInt(js.optString("passQNum"));
                    UserData.answer = js.optString("passAnswer");
                    UserData.phone = js.optString("phoneNum");
                    UserData.ip = js.optString("ipNum");
                    UserData.port = js.optString("portNum");
                    UserData.name = js.optString("userName");
                    UserData.id = id.getText().toString();
                    UserData.pw = pw.getText().toString();
                    UserData.status = Integer.parseInt(js.optString("status"));
                    Toast.makeText(Splash.this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplication(), Main_screen.class));
                }
                else if(num.equals("2")){        // 실패(비밀번호 틀림)
                    Toast.makeText(Splash.this, "로그인에 실패하였습니다.\n비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                }
                else {                           // 실패(id 존재하지 않음)
                    Toast.makeText(Splash.this, "로그인에 실패하였습니다.\n일치하는 계정이 없습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",id.getText().toString());
                params.put("password",pw.getText().toString());
                return params;
            }
        };

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.getText().toString().equals("") || pw.getText().toString().equals("")){
                    Toast.makeText(Splash.this,"로그인에 실패하였습니다.\n빈 칸을 모두 작성하여 주십시오.",Toast.LENGTH_SHORT).show();
                }
                else{
                    queue.add(login_request);

                }
            }
        });


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



    }
}
