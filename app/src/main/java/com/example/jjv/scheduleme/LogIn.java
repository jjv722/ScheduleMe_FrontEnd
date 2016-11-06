package com.example.jjv.scheduleme;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.URL;

public class LogIn extends AppCompatActivity implements Async_API.callback {
    EditText email;
    EditText password;
    Button logIn;
    ProgressBar loading;
    LinearLayout signIn;
    String SERVER_URL = "http://192.168.0.4:8000/";
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        logIn = (Button) findViewById(R.id.logIn);
        loading = (ProgressBar) findViewById(R.id.loading);
        signIn = (LinearLayout) findViewById(R.id.sign_in);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLoginInfo();
            }
        });
    }

    public void sendLoginInfo(){
        try {
            JSONObject params = new JSONObject();
            params.put("u", email.getText().toString());
            params.put("p", password.getText().toString());
            URL loginURL = new URL(SERVER_URL + "api/login");
            new Async_API(this, params).execute(loginURL);
        } catch(Exception e) {}
    }

    @Override
    public void start() {
        loading.setVisibility(View.VISIBLE);
        signIn.setVisibility(View.GONE);
    }

    @Override
    public void success(final JSONObject jsonObject) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.GONE);

                Log.d("success:", jsonObject.toString());
                Toast.makeText(
                        getApplicationContext(),
                        "Log in successful!" + jsonObject.toString(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        }, 3000);
    }

    @Override
    public void error() {
        loading.setVisibility(View.GONE);
        signIn.setVisibility(View.VISIBLE);
        // there was a problem contacting the server...
        Toast.makeText(
                getApplicationContext(),
                "There was a problem when trying to log in...",
                Toast.LENGTH_SHORT
        ).show();
    }
}
