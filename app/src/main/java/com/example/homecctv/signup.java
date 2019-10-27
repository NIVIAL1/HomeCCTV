package com.example.homecctv;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class signup extends AppCompatActivity {

    final int PICTURE_REQUEST_CODE = 100;
    EditText pw;
    Button sign_up, selectimg;
    TextView signup_imgname;
    ImageView img, back;
    ArrayList<String> pass = null;
    List<String> data = new ArrayList<>();
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.rgb(00, 85, 77));
        }
        pw = (EditText) findViewById(R.id.sign_pw);
        sign_up = (Button) findViewById(R.id.button_sign_up);
        selectimg = (Button) findViewById(R.id.button_select_image);
        signup_imgname = (TextView) findViewById(R.id.sign_imagename);
        img = (ImageView) findViewById(R.id.sign_img);
        back = findViewById(R.id.back0);
        queue = Volley.newRequestQueue(this);

        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        Log.d("tste", "Before");
        sendRequest();
        Log.d("tste","dd");

        Spinner spinner1;
        AdapterSpinner1 adapterSpinner1;


        spinner1 = findViewById(R.id.sign_pw_request);
        adapterSpinner1 = new AdapterSpinner1(this, data);
        spinner1.setAdapter(adapterSpinner1);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        selectimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICTURE_REQUEST_CODE);
            }
        });

        String urls = "http://35.221.206.41:52274/imageWrite";
        final StringRequest img_post = new StringRequest(Request.Method.POST, urls, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(signup.this,response,Toast.LENGTH_SHORT);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("img"," ");
                return params;
            }
        };

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pw.getText().toString().equals("111")) {
                    queue.add(img_post);

                    Toast.makeText(signup.this, "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplication(), Splash.class));
                } else {
                    Toast.makeText(signup.this, "회원가입에 실패하였습니다.\n주어진 양식을 충족시켜주십시오.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            if (requestCode == PICTURE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    img.setImageResource(0);
                    signup_imgname.setText("");
                    final Uri uri = data.getData();
                    ClipData clipData = data.getClipData();

                    if (clipData != null) {
                        for (int i = 0; i < 3; i++) {
                            if (i < clipData.getItemCount()) {
                                Uri urione = clipData.getItemAt(i).getUri();
                                switch (i) {
                                    case 0:
                                        img.setImageURI(urione);
                                        break;
                                }
                            }
                        }
                    } else if (uri != null) {
                        img.setImageURI(uri);
                    }
                }
            }
        }
    public ArrayList<String> sendRequest() {
        String url = "http://35.221.206.41:52274/register/getQuestion";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Log.d("tste", response);
                Log.d("tste", "Make pass");
                pass = new ArrayList<>();
                Log.d("tste", "Make pass");
                String[] array = jsonParser(response);
                Log.d("tste", "number make");
                String[] question = array[1].split(",");
                Log.d("tste", "question make");

                for (int i = 0; i < question.length; i++) {
                    pass.add(question[i]);
                    data.add(question[i]);

                }
            }
        },
             new Response.ErrorListener() { //에러발생시 호출될 리스너 객체
                @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("tste", error.toString());
                    }
                }
        ) {
            //만약 POST 방식에서 전달할 요청 파라미터가 있다면 getParams 메소드에서 반환하는 HashMap 객체에 넣어줍니다.
            //이렇게 만든 요청 객체는 요청 큐에 넣어주는 것만 해주면 됩니다.
            //POST방식으로 안할거면 없어도 되는거같다.
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
            JSONObject js = new JSONObject(response);
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