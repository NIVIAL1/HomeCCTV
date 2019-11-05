package com.example.homecctv;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class lookup extends Activity {
    CalendarView calendarView;
    Button btnCalendar;
    String selectedDate;
    ImageView back;
    public static ArrayList<String> videoArr = new ArrayList();
    public static String date = "";
    JSONObject js = new JSONObject();
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lookup);
        intent = new Intent(getApplicationContext(), VideoList.class);
        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(Color.rgb(00,85,77));
        }

        calendarView = (CalendarView)findViewById(R.id.calendar);
        btnCalendar = (Button)findViewById(R.id.btn_calendar);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.format(new Date());

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = year+"-"+(month+1)+"-"+dayOfMonth;
                date = selectedDate;
            }
        });
        back = findViewById(R.id.back5);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }


        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
                //서버통신으로 날짜(date)를 보내고 받아서 동영상 이름 배열을 videoArr에 저장

            }
        });
    }
    public ArrayList<String> sendRequest() {
        String url = "http://35.221.206.41:52274/loading/videoList";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("test",response);
                response  = response.replace("\"","");
                response  = response.replace("[","");
                response  = response.replace("]","");
                String[] name = response.split(",");
                videoArr.clear();
                for (int i = 0; i < name.length; i++) {
                    videoArr.add(name[i]);
                }
                startActivity(intent);
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
                params.put("id",UserData.id);
                params.put("selectedDate",date);
                return params;
            }
        };
        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
        return videoArr;
    }



}
