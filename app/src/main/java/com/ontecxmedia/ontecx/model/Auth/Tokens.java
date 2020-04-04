package com.ontecxmedia.ontecx.model.Auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Tokens implements Serializable {

    @SerializedName("refresh")
    @Expose
    private String refresh;
    @SerializedName("access")
    @Expose
    private String access;

    public String getRefresh() {
        return refresh;
    }

    public String getAccess() {
        return access;
    }


}
