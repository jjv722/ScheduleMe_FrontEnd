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
    private String TOKEN;
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

    public String getToken(){
        return this.TOKEN;
    }

    public void save(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences("schedule_me", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("user", "[user]: " + getUser() + ", [password]: " + getPassword() + ", [token]: " + getToken());
        editor.commit();
    }

    public static Authentication load(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences("schedule_me", Context.MODE_PRIVATE);
        String data = sharedPref.getString("user", null);
        Authentication result = null;
        if (data != null) {
            if (data.contains("[user]:") && data.contains("[password]:") && data.contains("[token]:")) {
                String user = data.substring(data.indexOf("[user]: ") + 8 , data.indexOf(","));
                String password = data.substring(data.indexOf("[password]: ") + 12, data.indexOf(", ", data.indexOf("[password]: ") + 12));
                String token = data.substring(data.indexOf("[token]: ") + 9);
                Log.d("user", user);
                Log.d("password", password);
                result = new Authentication(user, password);
                result.setToken(token);
            }
        }
        return result;
    }

    public void setToken(String t){
        this.TOKEN = t;
    }

}
