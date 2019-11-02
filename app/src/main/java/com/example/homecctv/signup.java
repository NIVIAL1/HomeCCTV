package com.example.homecctv;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class signup extends AppCompatActivity {
    public String path;
    public String result = "hellow";
    final int PICTURE_REQUEST_CODE = 100;
    EditText sign_id, sign_pw, sign_pw2, sign_name, sign_pw_answer, sign_phone, sign_IP, sign_PORT;
    int questionNum = -1;
    Button sign_up, selectimg;
    TextView signup_imgname;
    ImageView img, back;
    ArrayList<String> pass = new ArrayList<>();
    ArrayList<String> signup_result = new ArrayList<>();
    List<String> data = new ArrayList<>();
    private RequestQueue queue;
    Spinner spinner1;
    JSONObject js = new JSONObject();
    AdapterSpinner1 adapterSpinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.rgb(00, 85, 77));
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }
        sign_id= (EditText) findViewById(R.id.sign_id);
        sign_pw=(EditText) findViewById(R.id.sign_pw);
        sign_pw2=(EditText)findViewById(R.id.sign_pw2);
        sign_name=(EditText)findViewById(R.id.sign_name);
        sign_pw_answer=(EditText)findViewById(R.id.sign_pw_answer);
        sign_phone=(EditText)findViewById(R.id.sign_phone);
        sign_IP=(EditText)findViewById(R.id.sign_IP);
        sign_PORT=(EditText)findViewById(R.id.sign_PORT);
        sign_up = (Button) findViewById(R.id.button_sign_up);
        selectimg = (Button) findViewById(R.id.button_select_image);
        signup_imgname = (TextView) findViewById(R.id.sign_imagename);
        img = (ImageView) findViewById(R.id.sign_img);
        back = findViewById(R.id.back0);
        queue = Volley.newRequestQueue(this);



        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        sendRequest();





        spinner1 = findViewById(R.id.sign_pw_request);
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


        String url_signup = "http://35.221.206.41:52274/register/index";
        final StringRequest signup_request = new StringRequest(Request.Method.POST, url_signup, new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                signup_result = new ArrayList<>();
                String num = null;


                try {
                    js = new JSONObject(response2);
                    num = js.optString("result");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                 if(num.equals("1")){                   // 성공
                        Toast.makeText(signup.this, "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplication(), Splash.class));
                    }
                    else{                               // 실패
                        Toast.makeText(signup.this, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
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
                params.put("id", sign_id.getText().toString());
                params.put("password",sign_pw.getText().toString());
                params.put("userName",sign_name.getText().toString());
                params.put("phoneNum",sign_phone.getText().toString());
                params.put("passQNum",Integer.toString(questionNum));
                params.put("passAnswer",sign_pw_answer.getText().toString());
                params.put("ipNum",sign_IP.getText().toString());
                params.put("portNum",sign_PORT.getText().toString());

                return params;
            }
        };
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
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICTURE_REQUEST_CODE);           // 갤러리에서 이미지 선택
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sign_pw.getText().toString().equals(sign_pw2.getText().toString())) {
                    Toast.makeText(signup.this, "회원가입에 실패하였습니다.\n비밀번호와 비밀번호 확인을 일치시켜주십시오.", Toast.LENGTH_SHORT).show();
                } else if (sign_id.getText().toString().equals("") || sign_pw.getText().toString().equals("") || sign_pw2.getText().toString().equals("") || sign_name.getText().toString().equals("") || questionNum == -1
                        || sign_pw_answer.getText().toString().equals("") || sign_phone.getText().toString().equals("") || sign_IP.getText().toString().equals("") || sign_PORT.getText().toString().equals("") ) {
                    Toast.makeText(signup.this, "회원가입에 실패하였습니다.\n빈 칸을 모두 작성하여 주십시오.", Toast.LENGTH_SHORT).show();
                } else if(!signup_imgname.getText().toString().equals("")){
                    Toast.makeText(signup.this, "회원가입에 실패하였습니다.\n이미지를 입력해 주십시오.", Toast.LENGTH_SHORT).show();
                } else {
                    queue.add(signup_request);

                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                AndroidUploader uploader = new AndroidUploader();
                                result = uploader.uploadPicture(path,sign_id.getText().toString(),sign_name.getText().toString());
                            } catch (Exception e) {
                                Log.e(e.getClass().getName(), e.getMessage());
                            }
                        }
                    }).start();
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
                            Log.d("img",urione.toString());
                            switch (i) {
                                case 0:
                                    img.setImageURI(urione);
                                    Log.d("img",urione.toString());
                                    break;
                            }
                        }
                    }
                } else if (uri != null) {
                    path=getPath(uri);
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
                pass = new ArrayList<>();
                String[] array = jsonParser(response);
                String[] question = array[1].split(",");

                for (int i = 0; i < question.length; i++) {
                    pass.add(question[i]);
                    data.add(question[i]);
                }
                adapterSpinner1.notifyDataSetChanged();
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



    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }
}