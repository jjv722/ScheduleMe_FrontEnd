package com.example.jjv.scheduleme.Auth;

import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.jjv.scheduleme.R;

public class LogMe extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_me);

        //  Optimization for setting full screen image.
        Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.city8);
        Bitmap destination = BitmapCache.getInstance().get("log_me");

        // not cached...
        if (destination == null) {
            if (src.getWidth() >= src.getHeight()){
                destination = Bitmap.createBitmap(
                        src,
                        src.getWidth()/2 - src.getHeight()/2,
                        0,
                        src.getHeight(),
                        src.getHeight()
                );
            } else {
                destination = Bitmap.createBitmap(
                        src,
                        0,
                        src.getHeight()/2 - src.getWidth()/2,
                        src.getWidth(),
                        src.getWidth()
                );
            }
            BitmapCache.getInstance().add("log_me", destination);
        }

        ImageView iv_background = (ImageView) findViewById(R.id.background);
        iv_background.setImageBitmap(destination);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, new LoginFragment())
                    .commit();
        }
    }

    public void switchFragment(Fragment f) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.fragment_container, f)
                .commit();
    }
}
