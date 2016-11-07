package com.example.jjv.scheduleme;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.URL;

public class LogMe extends AppCompatActivity implements Async_API.callback {
    public String SERVER_URL = "http://192.168.0.4:8000/";

    public Button logIn;
    public EditText email;
    public EditText password;
    public LinearLayout layout;
    public ProgressBar loading;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_me);
        logIn = (Button) findViewById(R.id.logIn);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                sendLoginInfo();
            }
        });
        layout = (LinearLayout) findViewById(R.id.sign_in);
        loading = (ProgressBar) findViewById(R.id.loading);
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
        layout.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void success(final JSONObject jsonObject) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.GONE);
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
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
                Toast.makeText(
                        getApplicationContext(),
                        "There was a problem when trying to log in...",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }, 3000);
    }
}
