package com.example.homecctv;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
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

public class lostpw extends Activity {
    EditText id,answer;
    Button btn_lost_pw;
    ImageView back;
    int questionNum = -1;
    ArrayList<String> pass = new ArrayList<>();
    List<String> data = new ArrayList<>();
    AdapterSpinner1 adapterSpinner1;
    Spinner spinner1;
    JSONObject js = new JSONObject();
    private RequestQueue queue;
    View lost_pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_pw);
        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(Color.rgb(0,0,0));
        }
        id = (EditText)findViewById(R.id.lost_pw_id);
        answer = (EditText)findViewById(R.id.lost_pw_answer);
        queue = Volley.newRequestQueue(this);
        btn_lost_pw = (Button)findViewById(R.id.button_lostpw);
        back = findViewById(R.id.back2);
        lost_pw = findViewById(R.id.lost_pw);

        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        sendRequest();

        spinner1 = findViewById(R.id.lost_pw_request);
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


        String url_lost_pw = "http://35.221.206.41:52274/find/passFind/index";
        final StringRequest lost_pw_request = new StringRequest(Request.Method.POST, url_lost_pw, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {               // 비밀번호찾기 성공 유무 보내주는 것 받음
                String num = null;
                String pw = null;

                String[] arraysum = new String[2];

                try {
                    js = new JSONObject(response);
                    num = js.optString("result");
                    pw = js.optString("password");
                    arraysum[0] = num;
                    arraysum[1] = pw;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(num.equals("1")){             // 성공
                    Toast.makeText(lostpw.this, "비밀번호 찾기에 성공하였습니다.\n"+pw+" 입니다.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplication(), Splash.class));

                }
                else {                           // 실패(id 존재하지 않음)
                    Toast.makeText(lostpw.this, "비밀번호 찾기에 실패하였습니다.\n일치하는 정보가 없습니다..", Toast.LENGTH_SHORT).show();
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
                params.put("id", id.getText().toString());
                params.put("passQNum",Integer.toString(questionNum));
                params.put("passAnswer",answer.getText().toString());
                return params;
            }
        };

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_lost_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.getText().toString().equals("") || questionNum == -1 || answer.getText().toString().equals("")){
                    Toast.makeText(lostpw.this, "비밀번호 찾기에 실패하였습니다.\n빈 칸을 모두 작성하여 주십시오.", Toast.LENGTH_SHORT).show();
                }
                else{
                    queue.add(lost_pw_request);
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