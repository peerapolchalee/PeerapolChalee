package com.example.ss.chapter9;

import android.content.Intent;
import android.net.nsd.NsdManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class registerActivity extends AppCompatActivity {

    private EditText etUser;
    private EditText etPass;
    private EditText etConPass;
    private Button btnRegist;
    private EditText etDis;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etDis = (EditText) findViewById(R.id.etDis);
        etUser = (EditText) findViewById(R.id.etUser);
        etPass = (EditText) findViewById(R.id.etPass);
        etConPass = (EditText) findViewById(R.id.etConPass);
        btnRegist = (Button) findViewById(R.id.btnRegist);

        validate();
        setEvent();
    }

    private boolean validate() {
        //TODO validate
        String display = etDis.getText().toString();
        String username = etUser.getText().toString();
        String password = etPass.getText().toString();
        String conpass = etConPass.getText().toString();

        if (display.isEmpty() || username.isEmpty() || password.isEmpty() || conpass.isEmpty()) {
            return false;
        } else if (!password.equals(conpass)) {
            return false;
        } else {
            return false;
        }

    }

    private void setEvent() {
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {
                    Intent i = new Intent(registerActivity.this, loginActivity.class);
                    startActivity(i);

                    new register(etUser.getText().toString(),
                            etPass.getText().toString(),
                            etConPass.getText().toString(),
                            etDis.getText().toString()).execute();
                } else {
                    Toast.makeText(registerActivity.this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT);
                }
            }
        });
    }



    private class register extends AsyncTask<Void, Void, String> {

        private final String username;
        private final String password;
        private String displayName;
        private String passwordcon;

        public register(String username, String password, String passwordconfrim, String display) {
            this.username = username;
            this.password = password;
            this.passwordcon = passwordcon;
            this.displayName = displayName;
        }
        @Override
        protected String doInBackground(Void... params) {
            OkHttpClient client =new OkHttpClient();
            Request request;
            Response response;


            RequestBody requestBody = new FormBody.Builder()
                    .add("username" , username)
                    .add("password", password)
                    .add("password_con", passwordcon)
                    .add("display_name", displayName)
                    .build();

            request = new Request.Builder()
                    .url("http://kimhun55.com/pollservices/signup.php")
                    .post(requestBody)
                    .build();
            try{

                response = client.newCall(request).execute();

                if(response.isSuccessful()){
                    return  response.body().string();
                }

            }catch (IOException ex){
                ex.printStackTrace();
            }



            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            Toast.makeText(registerActivity.this,s,Toast.LENGTH_SHORT).show();
            try {
                JSONObject rootOdj = new JSONObject(s);
                if(rootOdj.has("result")){
                    JSONObject resultObj = rootOdj.getJSONObject("result");
                    if (resultObj.getInt("result")==1){
                        Toast.makeText(RegisterActivity.this, resultObj.getString("result_desc"), Toast.LENGTH_LONG).show();
                        finish();
                    }else {
                        Toast.makeText(RegisterActivity.this, resultObj.getString("result_desc"), Toast.LENGTH_LONG).show();
                    }
                }
            } catch (JSONException ex){

            }

        }
            }
        }
    }