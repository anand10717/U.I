package com.uandme.retrofit;

/**
 * Created by anand-3985 on 26/11/16.
 */
public class Response_Login {

    private String code ;
    private String name;

    public Response_Login(){

    }

    public Response_Login(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
