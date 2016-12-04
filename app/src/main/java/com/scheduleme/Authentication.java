package com.scheduleme;

import java.io.Serializable;

/**
 * Created by mauricio on 12/3/16.
 */

public class Authentication implements Serializable {
    public String USER;
    public String PASSWORD;
    Authentication(String user, String password) {
        this.USER = user;
        this.PASSWORD = password;
    };
}
