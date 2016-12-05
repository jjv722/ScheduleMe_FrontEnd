package com.scheduleme.Auth;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scheduleme.Main_Menu;
import com.scheduleme.R;
import com.scheduleme.Network.AuthenticationCalls;


import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by mauricio on 11/6/16.
 */

public class LoginFragment extends Fragment implements Callback<ResponseBody> {

    @Override
    public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
        Log.d("onResponse", "onResponse");
        Log.d("response code", String.valueOf(response.code()));
        if (response.code() == 200) {
            // we gucci
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loading.setVisibility(View.GONE);
                    Toast.makeText(
                            getActivity(),
                            "Log in successful!",
                            Toast.LENGTH_SHORT
                    ).show();

                    /*
                    *   Save auth information onto preferences.
                    * */
                    com.scheduleme.Authentication auth = new com.scheduleme.Authentication(
                            email.getText().toString(),
                            password.getText().toString()
                    );
                    auth.save(getActivity());

                    Intent intent = new Intent(getActivity(), Main_Menu.class);
                    startActivity(intent);
                    getActivity().finish();
                    Animation a = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_right);
                    a.reset();
                    RelativeLayout rl = (RelativeLayout) getActivity().findViewById(R.id.rootView);
                    rl.clearAnimation();
                    rl.startAnimation(a);
                }
            }, 3000);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loading.setVisibility(View.GONE);
                    layout.setVisibility(View.VISIBLE);
                    String str;
                    try {
                        str = response.errorBody().string();
                        Log.d("response", str);
                    } catch (IOException e) {
                        str = "";
                    }
                    String msg;
                    if (str.contains("wrong password")) {
                        msg = "The password provided is wrong.";
                    } else if (str.contains("user does not exist")) {
                        msg = "Email is not registered.";
                    } else {
                        msg = "There was a problem when trying to log in...";
                    }
                    Toast.makeText(
                            getActivity(),
                            msg,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }, 3000);
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.d("onFailure", "onFailure");
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

    public Button logIn;
    public EditText email;
    public EditText password;
    public LinearLayout layout;
    public ProgressBar loading;
    public TextView forgot;
    public TextView register;
    private Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        forgot = (TextView) view.findViewById(R.id.forgot);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogMe l = (LogMe) getActivity();
                Toast.makeText(getActivity(), "Feature has not been implemented yet...", Toast.LENGTH_SHORT).show();
            }
        });
        register = (TextView) view.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogMe l = (LogMe) getActivity();
                Toast.makeText(getActivity(), "Please switch to register fragment..", Toast.LENGTH_SHORT).show();
                l.switchFragment(new RegisterFragment());
            }
        });
        logIn = (Button) view.findViewById(R.id.logIn);
        email = (EditText) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);
        logIn.setOnClickListener(new View.OnClickListener() {
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.7:8000/")
                .build();
        AuthenticationCalls service = retrofit.create(AuthenticationCalls.class);
        layout.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        Call<ResponseBody> myLogin = service.login(
                email.getText().toString(),
                password.getText().toString()
        );
        myLogin.enqueue(this);
    }
}
