package com.example.homecctv;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class change_info extends Activity {
    EditText pw, pw2, answer, phone, ip, port;
    Button change_info;
    ImageView back;
    int questionNum = -1;
    ArrayList<String> pass = new ArrayList<>();
    List<String> data = new ArrayList<>();
    AdapterSpinner1 adapterSpinner1;
    Spinner spinner1;
    JSONObject js = new JSONObject();
    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(Color.rgb(00,85,77));
        }
        setContentView(R.layout.change_info);
        pw = findViewById(R.id.change_pw);
        pw2 = findViewById(R.id.change_pw2);
        answer = findViewById(R.id.change_pw_answer);
        phone = findViewById(R.id.change_phone);
        ip = findViewById(R.id.change_IP);
        port = findViewById(R.id.change_PORT);

        change_info = (Button)findViewById(R.id.button_change_info);


        queue = Volley.newRequestQueue(this);

        back = findViewById(R.id.back3);

        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        sendRequest();

        spinner1 = findViewById(R.id.change_pw_request);
        adapterSpinner1 = new AdapterSpinner1(this, data);
        spinner1.setAdapter(adapterSpinner1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                questionNum = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String url_change = "http://35.221.206.41:52274/infoChange/update";
        final StringRequest change_request = new StringRequest(Request.Method.POST, url_change, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String num = null;
                try {
                    js = new JSONObject(response);
                    num = js.optString("result");


                } catch (JSONException e) {
                    e.printStackTrace();
                }



                if(num.equals("1")){                // 성공
                    Toast.makeText(change_info.this, "정보변경에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                    change_info.this.finish();
                }
                else{                               // 실패
                    Toast.makeText(change_info.this, "정보변경에 실패하였습니다.", Toast.LENGTH_SHORT).show();
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
                params.put("id", UserData.id);
                params.put("password",pw.getText().toString());
                params.put("phoneNum",phone.getText().toString());
                params.put("passQNum",Integer.toString(questionNum));
                params.put("passAnswer",answer.getText().toString());
                params.put("ipNum",ip.getText().toString());
                params.put("portNum",port.getText().toString());

                return params;
            }
        };
        change_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pw.getText().toString().equals(pw2.getText().toString())){
                    Toast.makeText(change_info.this, "정보변경에 실패하였습니다.\n비밀번호와 비밀번호 확인을 일치시켜주십시오.", Toast.LENGTH_SHORT).show();
                }
                else if(pw.getText().toString().equals("") || phone.getText().toString().equals("") || answer.getText().toString().equals("")
                        || ip.getText().toString().equals("") || port.getText().toString().equals("") || questionNum == -1){
                    Toast.makeText(change_info.this, "정보변경에 실패하였습니다.\n모든 정보를 입력해주십시오.", Toast.LENGTH_SHORT).show();
                }
                else{
                    queue.add(change_request);
                }
            }
        });




    }
    public ArrayList<String> sendRequest() {
        String url = "http://35.221.206.41:52274/register/getQuestion";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                pass = new ArrayList<>();
                String[] array = jsonParser(response);
                String[] question = array[1].split(",");

                for (int i = 0; i < question.length; i++) {
                    pass.add(question[i]);
                    data.add(question[i]);
                }
                adapterSpinner1.notifyDataSetChanged();                 // 데이터 수정 후 수정값으로 저장
            }
        },
                new Response.ErrorListener() { //에러발생시 호출될 리스너 객체
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
        return pass;
    }
    public String[] jsonParser (String response){
        String num = null;
        String question = null;

        String[] arraysum = new String[2];
        try {
            js = new JSONObject(response);
            num = js.optString("passQNum");
            question = js.optString("question");
            arraysum[0] = num;
            arraysum[1] = question;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arraysum;
    }
}
