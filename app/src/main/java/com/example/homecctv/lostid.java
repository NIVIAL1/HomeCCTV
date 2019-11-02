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

import java.util.HashMap;
import java.util.Map;

public class lostid extends Activity {
    EditText name, phone;
    Button btn_lostid;
    ImageView back;
    private RequestQueue queue;
    String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_id);

        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(Color.rgb(00,85,77));
        }


        name = (EditText)findViewById(R.id.lost_id_name);
        phone = (EditText)findViewById(R.id.lost_id_phone);

        btn_lostid = (Button)findViewById(R.id.button_lostid);

        back = findViewById(R.id.back1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        queue = Volley.newRequestQueue(this);
        String url_lostid = "http://35.221.206.41:52274/find/idFind";
        final StringRequest lostid_request = new StringRequest(Request.Method.POST, url_lostid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // 아이디찾기 성공 유무 보내주는 것 받음
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userName",name.getText().toString());
                params.put("phoneNum",phone.getText().toString());
                Log.d("test","params 생성");
                return params;
            }
        };

        btn_lostid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("") || phone.getText().toString().equals("")){
                    Toast.makeText(lostid.this,"아이디 찾기에 실패하였습니다.\n빈 칸을 모두 작성하여 주십시오.",Toast.LENGTH_SHORT).show();
                }
                else{
                    queue.add(lostid_request);
                    Toast.makeText(lostid.this,"아이디는 ababk 입니다.",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplication(), Splash.class));

                }
            }
        });

    }
}