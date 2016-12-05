package com.scheduleme;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.scheduleme.Profile.My;

/**
 * Created by jjv on 11/9/16.
 */

public class Main_Menu extends AppCompatActivity {
    private Toolbar t;
    private DrawerLayout d;
    private ActionBarDrawerToggle toggle;
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
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeButtonEnabled(true);
            toggle = new ActionBarDrawerToggle(this, d, t, R.string.open_drawer, R.string.close_drawer);
            toggle.syncState();
        }

        id = R.id.profile;
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new My())
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        id = findViewById(R.id.fragment_container).getId();
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
                f = MapFragment.newInstance();
                ((MapFragment) f).getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(new LatLng(30.2672, -97.7431))
                                .zoom(10)
                                .build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                });
                setTitle("Browse Locations");
                out = "switching to browse location";
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
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.animator.slide_in_right,
                            R.animator.slide_out_left,
                            R.animator.slide_in_left,
                            R.animator.slide_out_right)
                    .replace(R.id.fragment_container, f)
                    .addToBackStack("current")
                    .commit();
        }
    }

}
