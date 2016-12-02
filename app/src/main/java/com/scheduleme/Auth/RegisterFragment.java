package com.scheduleme.Auth;

import android.app.Fragment;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.scheduleme.Async_API;
import com.scheduleme.R;

import org.json.JSONObject;

import java.net.URL;

/**
 * Created by mauricio on 11/6/16.
 */

public class RegisterFragment extends Fragment implements Async_API.callback {
//    public String SERVER_URL = "http://10.147.28.184:8000/";
    public String SERVER_URL = "http://10.147.12.210:8000/";
    public String SERVER_PATH = "api/register";
    public Button register;
    public TextView login;
    public EditText name;
    public EditText email;
    public EditText password;
    public LinearLayout layout;
    public ProgressBar loading;
    private Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        login = (TextView) view.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        getActivity(),
                        "Switching to log in fragment...",
                        Toast.LENGTH_SHORT
                ).show();
                LogMe l = (LogMe) getActivity();
                l.switchFragment(new LoginFragment());
            }
        });
        register = (Button) view.findViewById(R.id.register);
        name = (EditText) view.findViewById(R.id.name);
        email = (EditText) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                sendLoginInfo();
            }
        });
        layout = (LinearLayout) view.findViewById(R.id.sign_in);
        loading = (ProgressBar) view.findViewById(R.id.loading);
    }

    public void sendLoginInfo(){
        try {
            JSONObject params = new JSONObject();
            params.put("name", name.getText().toString());
            params.put("u", email.getText().toString());
            params.put("p", password.getText().toString());
            URL loginURL = new URL(SERVER_URL + SERVER_PATH);
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
                        getActivity(),
                        "Register successful!" + jsonObject.toString(),
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
                        getActivity(),
                        "There was a problem when trying to log in...",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }, 3000);
    }
}
