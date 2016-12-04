package com.scheduleme;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by mauricio on 12/3/16.
 */

public class Authentication implements Serializable {
    private String USER;
    private String PASSWORD;
    public Authentication(String user, String password) {
        this.USER = user;
        this.PASSWORD = password;
    };

    public String getUser(){
        return this.USER;
    }

    public String getPassword() {
        return this.PASSWORD;
    }

    public void save(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences("schedule_me", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("user", "[user]: " + getUser() + ", [password]: " + getPassword());
        editor.commit();
    }

    public static Authentication load(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences("schedule_me", Context.MODE_PRIVATE);
        String data = sharedPref.getString("user", null);
        Authentication result = null;
        if (data != null) {
            if (data.contains("[user]:") && data.contains("[password]:")) {
                String user = data.substring(data.indexOf("[user]: ") + 8 , data.indexOf(","));
                String password = data.substring(data.indexOf("[password]: ") + 12);
                Log.d("user", user);
                Log.d("password", password);
                result = new Authentication(user, password);
            }
        }
        return result;
    }
}
