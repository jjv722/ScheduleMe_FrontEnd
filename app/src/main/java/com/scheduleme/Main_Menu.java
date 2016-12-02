package com.scheduleme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.scheduleme.Profile.My;

/**
 * Created by jjv on 11/9/16.
 */

public class Main_Menu extends AppCompatActivity {
    private Toolbar t;
    private DrawerLayout d;
    private int id;
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.main_menu);
        Toast.makeText(
                this,
                "Inside MainActivity",
                Toast.LENGTH_SHORT
        ).show();
        t = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(t);

        d = (DrawerLayout) findViewById(R.id.drawer_layout);

        id = R.id.profile;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new My())
                .commit();
    }

    public void navigation(View v){
        if (id == v.getId()) {
            return;
        }
        id = v.getId();
        Fragment f = null;
        String out = null;
        switch (id){
            case R.id.profile: {
                f = new My();
                out = "switching to profile";
                break;
            }
            case R.id.bookNow: {
                out = "switching to book now / not implemented";
                break;
            }
            case R.id.browseLocation: {
                out = "switching to browse location / not implemented";
                break;
            }
            case R.id.history: {
                out = "switching to history / not implemented";
                break;
            }
        }
        Toast.makeText(
                this,
                out,
                Toast.LENGTH_SHORT
        ).show();
        if (d.isDrawerOpen(Gravity.LEFT)){
            d.closeDrawer(Gravity.LEFT);
        }
        if (f != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            android.R.anim.slide_in_left,
                            android.R.anim.slide_out_right,
                            android.R.anim.slide_in_left,
                            android.R.anim.slide_out_right)
                    .replace(R.id.fragment_container, f)
                    .addToBackStack("current")
                    .commit();
        }
    }

}
