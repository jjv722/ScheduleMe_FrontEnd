package com.scheduleme.Auth;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.scheduleme.Authentication;
import com.scheduleme.Main_Menu;
import com.scheduleme.R;
import com.scheduleme.ScheduleMeAPI;
import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LogMe extends Activity implements Callback<ResponseBody> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Authentication auth = Authentication.load(this);
        if (auth != null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.8:8000/")
                    .build();
            ScheduleMeAPI service = retrofit.create(ScheduleMeAPI.class);
            Call<ResponseBody> myLogin = service.login(
                    auth.getUser(),
                    auth.getPassword()
            );
            myLogin.enqueue(this);
        } else {
            setContentView(R.layout.activity_log_me);
            Picasso.with(this).load(R.drawable.city8).into((ImageView) findViewById(R.id.background));
            if (findViewById(R.id.fragment_container) != null) {
                if (savedInstanceState != null) {
                    return;
                }
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, new LoginFragment())
                        .commit();
            }
        }
    }

    public void switchFragment(Fragment f) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (f.getClass() == LoginFragment.class) {
            ft.setCustomAnimations(R.animator.slide_in_right, 0);
        } else if (f.getClass() == RegisterFragment.class) {
            ft.setCustomAnimations(R.animator.slide_in_left, 0);
        }
        ft.replace(R.id.fragment_container, f);
        ft.commit();
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        Log.d("response", ""+response.code());
        if (response.code() == 200) {
            Toast.makeText(
                    this,
                    "Log in successful!",
                    Toast.LENGTH_SHORT
            ).show();

            Intent intent = new Intent(this, Main_Menu.class);
            startActivity(intent);
            finish();
            // remove animation
            overridePendingTransition(0, 0);
        } else {
            Toast.makeText(
                    this,
                    "Log in Error!",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Toast.makeText(
                this,
                "Log in Error!",
                Toast.LENGTH_SHORT
        ).show();
    }
}
