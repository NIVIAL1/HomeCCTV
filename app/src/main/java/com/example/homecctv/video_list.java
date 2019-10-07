package com.example.homecctv;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class video_list extends Activity {
    TextView date_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.rgb(00, 85, 77));
        }
        setContentView(R.layout.video_list);

        date_name = (TextView)findViewById(R.id.video_date);
        Intent intent = getIntent();
        String selectedDate = intent.getExtras().getString("date");
        date_name.setText(selectedDate);


    }
}
