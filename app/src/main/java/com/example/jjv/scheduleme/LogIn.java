package com.example.jjv.scheduleme;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.net.URL;

public class LogIn extends AppCompatActivity implements Async_API.callback {
    EditText email;
    EditText password;
    Button logIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        logIn = (Button) findViewById(R.id.logIn);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLoginInfo();
            }
        });
    }

    public void sendLoginInfo(){
        JSONObject params = new JSONObject();
        try{
            params.put("u", email.getText().toString());
            params.put("p", password.getText().toString());
            URL loginURL = new URL("http://192.168.0.4:8000/api/login");
            new Async_API(this, params).execute(loginURL);
        }
        catch(Exception e){

        }
    }

    @Override
    public void start() {

    }

    @Override
    public void success(JSONObject jsonObject) {
        Log.d("success:", jsonObject.toString());
    }

    @Override
    public void error() {

    }
}
