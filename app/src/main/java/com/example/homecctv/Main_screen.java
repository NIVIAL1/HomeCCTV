package com.example.homecctv;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
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

public class Main_screen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ImageView record, up, down, right, left, up2, down2, right2, left2;
    private RequestQueue queue;
    private static final String TAG = "MAIN";
    TextView record_signal;
    int inputcount;
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(Color.rgb(0,0,0));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        record = findViewById(R.id.record);
        up = findViewById(R.id.button_up);
        down = findViewById(R.id.button_down);
        right = findViewById(R.id.button_right);
        left = findViewById(R.id.button_left);
        up2 = findViewById(R.id.button_up2);
        down2 = findViewById(R.id.button_down2);
        right2 = findViewById(R.id.button_right2);
        left2 = findViewById(R.id.button_left2);
        record_signal = findViewById(R.id.record_signal);
        up2.setVisibility(View.GONE);
        down2.setVisibility(View.GONE);
        right2.setVisibility(View.GONE);
        left2.setVisibility(View.GONE);
        queue = Volley.newRequestQueue(this);
        inputcount = UserData.status;

        if(inputcount == 1){
            record_signal.setText("on_Air");
            record_signal.setTextColor(Color.rgb(255,0,0));
        }
        else{
            record_signal.setText("off_Air");
        }

        String url = "http://35.221.206.41:52274/control/cameraSigFM";          // 서버 주소
        WebView webView = (WebView)findViewById(R.id.webView);

        String url_record = "http://35.221.206.41:52274/control/cameraSigTH";   // 녹화 주소

        String url_web = "http://192.168.43.113:9004/?action=stream";            // 카메라 주소
        webView.loadUrl(url_web);
        webView.setPadding(0,0,0,0);
        //webView.setInitialScale(100);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(POWER_SERVICE);
        boolean isWhiteListing = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            isWhiteListing = pm.isIgnoringBatteryOptimizations(getApplicationContext().getPackageName());
        }
        if (!isWhiteListing) {
            Intent intent = new Intent();
            intent.setAction(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
            startActivity(intent);
        }

        if (RealService.serviceIntent==null) {
            serviceIntent = new Intent(this, RealService.class);
            startService(serviceIntent);
        } else {
            serviceIntent = RealService.serviceIntent;                             //getInstance().getApplication();
            RealService.id = UserData.id;
        }



        final StringRequest stringRequest_up = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                params.put("controlData", "0");
                return params;
            }
        };
        final StringRequest stringRequest_down = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                params.put("controlData", "1");
                return params;
            }
        };
        final StringRequest stringRequest_left = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                params.put("controlData", "2");
                return params;
            }
        };
        final StringRequest stringRequest_right = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                params.put("controlData", "3");
                return params;
            }
        };



        final StringRequest record_request_on = new StringRequest(Request.Method.POST, url_record, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                params.put("controlData", "1");
                return params;
            }
        };
        final StringRequest record_request_off = new StringRequest(Request.Method.POST, url_record, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                params.put("controlData", "0");
                return params;
            }
        };


        stringRequest_up.setTag(TAG);
        stringRequest_down.setTag(TAG);
        stringRequest_left.setTag(TAG);
        stringRequest_right.setTag(TAG);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


//        up.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                queue.add(stringRequest_up);
//            }
//        });
        up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    up.setVisibility(View.INVISIBLE);
                    up2.setVisibility(View.VISIBLE);
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    up2.setVisibility(View.GONE);
                    up.setVisibility(View.VISIBLE);
                    queue.add(stringRequest_up);
                }
                return true;
            }
        });


//        down.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                queue.add(stringRequest_down);
//            }
//        });
        down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    down.setVisibility(View.INVISIBLE);
                    down2.setVisibility(View.VISIBLE);
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    down2.setVisibility(View.GONE);
                    down.setVisibility(View.VISIBLE);
                    queue.add(stringRequest_down);
                }
                return true;
            }
        });

//        right.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                queue.add(stringRequest_right);
//            }
//        });
        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    right.setVisibility(View.INVISIBLE);
                    right2.setVisibility(View.VISIBLE);
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    right2.setVisibility(View.GONE);
                    right.setVisibility(View.VISIBLE);
                    queue.add(stringRequest_right);
                }
                return true;
            }
        });

//        left.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                queue.add(stringRequest_left);
//            }
//        });
        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    left.setVisibility(View.INVISIBLE);
                    left2.setVisibility(View.VISIBLE);
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    left2.setVisibility(View.GONE);
                    left.setVisibility(View.VISIBLE);
                    queue.add(stringRequest_left);
                }
                return true;
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputcount%2==0){
                    inputcount++;
                    record_signal.setText("On_Air");
                    record_signal.setTextColor(Color.rgb(255,0,0));
                    Toast.makeText(Main_screen.this,"녹화 시작",Toast.LENGTH_SHORT);
                    queue.add(record_request_on);
                }
                else{
                    inputcount++;
                    record_signal.setText("Off_Air");
                    record_signal.setTextColor(Color.rgb(0,0,0));
                    Toast.makeText(Main_screen.this,"녹화 중지",Toast.LENGTH_SHORT);
                    queue.add(record_request_off);
                }


            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.logout) {
            UserData.id = null;
            UserData.pw = null;
            UserData.name = null;
            UserData.status = -1;
            UserData.port = null;
            UserData.answer = null;
            UserData.questionNum = -1;
            UserData.phone = null;
            UserData.video_name = null;
            UserData.ip = null;
            RealService.id = "-1";
            Toast.makeText(Main_screen.this,"로그아웃\n로그인 화면으로 돌아갑니다.",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplication(), Splash.class));
        } else if (id == R.id.changesign) {
            startActivity(new Intent(getApplication(), change_info.class));
        } else if (id == R.id.inputimage) {
            startActivity(new Intent(getApplication(), input_image.class));
        } else if (id == R.id.lookup) {
            startActivity(new Intent(getApplication(), lookup.class));
        } else if (id == R.id.withdrawal) {
            startActivity(new Intent(getApplication(), withdrawal.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceIntent!=null) {
            stopService(serviceIntent);
            serviceIntent = null;
        }
    }
}

