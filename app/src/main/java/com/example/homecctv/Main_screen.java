package com.example.homecctv;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class Main_screen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView record, up, down, right, left;
    private RequestQueue queue;
    private static final String TAG = "MAIN";

    Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            mHandler.sendEmptyMessageDelayed(0,100);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(Color.rgb(00,85,77));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        queue = Volley.newRequestQueue(this);
        String url = "http://35.221.206.41:52273/control/cameraSigFM";
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
                params.put("controlData", "3");
                return params;
            }
        };
        stringRequest_up.setTag(TAG);
        stringRequest_down.setTag(TAG);
        stringRequest_left.setTag(TAG);
        stringRequest_right.setTag(TAG);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        record = findViewById(R.id.record);
        up = findViewById(R.id.button_up);
        down = findViewById(R.id.button_down);
        right = findViewById(R.id.button_right);
        left = findViewById(R.id.button_left);

        String TAG = this.getClass().getSimpleName();
        

        up.setOnLongClickListener(new ImageView.OnLongClickListener(){
            public boolean onLongClick(View v){
                mHandler.sendEmptyMessageDelayed(0,100);
                queue.add(stringRequest_up);
                return false;
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeMessages(0);
                queue.add(stringRequest_up);
            }

        });
        down.setOnLongClickListener(new ImageView.OnLongClickListener(){
            public boolean onLongClick(View v){
                mHandler.sendEmptyMessageDelayed(0,100);
                queue.add(stringRequest_down);
                return false;
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeMessages(0);
                queue.add(stringRequest_down);
            }
        });
        right.setOnLongClickListener(new ImageView.OnLongClickListener(){
            public boolean onLongClick(View v){
                mHandler.sendEmptyMessageDelayed(0,100);
                queue.add(stringRequest_right);
                return false;
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeMessages(0);
                queue.add(stringRequest_right);
            }
        });
        left.setOnLongClickListener(new ImageView.OnLongClickListener(){
            public boolean onLongClick(View v){
                mHandler.sendEmptyMessageDelayed(0,100);
                queue.add(stringRequest_left);
                return false;
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeMessages(0);
                queue.add(stringRequest_left);
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            int inputcount = 0;

            @Override
            public void onClick(View v) {
                if(inputcount%2==0){
                    inputcount++;
                    Toast.makeText(Main_screen.this,"녹화를 시작합니다.",Toast.LENGTH_SHORT).show();
                }
                else{
                    inputcount++;
                    Toast.makeText(Main_screen.this,"녹화를 종료합니다.",Toast.LENGTH_SHORT).show();
                }


            }
        });
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.logout) {
            Toast.makeText(Main_screen.this,"로그아웃\n로그인 화면으로 돌아갑니다.",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplication(), Splash.class));
            Main_screen.this.finish();
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
}
