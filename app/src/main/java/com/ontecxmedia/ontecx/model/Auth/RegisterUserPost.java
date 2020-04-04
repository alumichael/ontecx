package com.ontecxmedia.ontecx.model.Auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegisterUserPost implements Serializable {


    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("username")
    @Expose
    private String username;


    public RegisterUserPost(String email, String name, String password, String username) {

        this.email = email;
        this.name = name;
        this.password = password;
        this.username = username;
    }




}