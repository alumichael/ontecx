package com.ontecxmedia.ontecx.model.Auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegisterUserGet implements Serializable {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("tokens")
    @Expose
    private Tokens tokens;
    @SerializedName("username")
    @Expose
    private String username;


    public String getEmail() {
        return email;
    }


    public String getName() {
        return name;
    }


    public Tokens getTokens() {
        return tokens;
    }


    public String getUsername() {
        return username;
    }


}
