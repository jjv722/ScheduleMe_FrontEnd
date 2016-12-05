package com.scheduleme.Auth;

import android.app.Fragment;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
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

import com.scheduleme.Network.AuthenticationCalls;
import com.scheduleme.R;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by mauricio on 11/6/16.
 */

public class RegisterFragment extends Fragment implements Callback<ResponseBody> {
    public Button register;
    public TextView login;
    public EditText name;
    public EditText email;
    public EditText password;
    public LinearLayout layout;
    public ProgressBar loading;

    @Override
    public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
        Log.d("onResponse", "code: "+ response.code());
        if (response.code() == 200) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loading.setVisibility(View.GONE);
                    Toast.makeText(
                            getActivity(),
                            "Register successful!",
                            Toast.LENGTH_SHORT
                    ).show();
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
                    if (str.contains("user already exists")) {
                        msg = "Email is currently registered.";
                    } else {
                        msg = "There was a problem when trying to sign up...";
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
        Log.d("onFailure", "failed");
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.8:8000/")
                .build();
        AuthenticationCalls service = retrofit.create(AuthenticationCalls.class);
        Call<ResponseBody> myRegister = service.register(
                name.getText().toString(),
                email.getText().toString(),
                password.getText().toString()
        );
        myRegister.enqueue(this);
        layout.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }

}
