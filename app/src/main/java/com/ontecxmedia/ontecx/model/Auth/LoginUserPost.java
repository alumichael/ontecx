package com.ontecxmedia.ontecx.model.Auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginUserPost implements Serializable
{

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;

    public LoginUserPost(String username, String password) {

        this.username = username;
        this.password = password;
    }


}