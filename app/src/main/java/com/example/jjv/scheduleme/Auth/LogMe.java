package com.example.jjv.scheduleme.Auth;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.jjv.scheduleme.R;
import com.squareup.picasso.Picasso;

public class LogMe extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
