package com.example.homecctv;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class lookup extends Activity {
    CalendarView calendarView;
    Button btnCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(Color.rgb(00,85,77));
        }
        setContentView(R.layout.lookup);
        calendarView = (CalendarView)findViewById(R.id.calendar);
        btnCalendar = (Button)findViewById(R.id.btn_calendar);

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                Date curDate = new Date(calendarView.getDate());
                calendar.setTime(curDate);
                String selectedDate = Integer.toString(calendar.get(Calendar.YEAR))+"년 "
                        +Integer.toString(calendar.get(Calendar.MONTH)+1)+"월 "+Integer.toString(calendar.get(Calendar.DATE))+"일";
                Toast.makeText(lookup.this,selectedDate,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), video_list.class);
                intent.putExtra("date",selectedDate);
                startActivity(intent);
            }
        });
    }

}
