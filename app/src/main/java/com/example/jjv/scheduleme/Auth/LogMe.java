package com.example.jjv.scheduleme.Auth;

import android.support.v4.app.Fragment;
import android.app.ListFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;

import com.example.jjv.scheduleme.R;

import java.util.ArrayList;

public class LogMe extends FragmentActivity {
    public ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_me);

        //  Optimization for setting full screen image.
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.city3), size.x, size.y, true);
        ImageView iv_background = (ImageView) findViewById(R.id.background);
        iv_background.setImageBitmap(bmp);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            fragments.add(new LoginFragment());
            fragments.add(new RegisterFragment());
            for (Fragment f : fragments) {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, f).commit();
            }
            switchFragment(0);
        }
    }

    public void switchFragment(int i){
        for (Fragment f : fragments) {
            getSupportFragmentManager().beginTransaction().hide(f).commit();
        }
        getSupportFragmentManager().beginTransaction().show(fragments.get(i)).commit();
    }
}
