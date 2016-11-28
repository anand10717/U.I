package com.uandme.retrofit;

/**
 * Created by anand-3985 on 26/11/16.
 */
public class Response_Find {

    private String frdMail;
    private String frdName;
    private String frdTokenId;
    private String code;
    public  Response_Find(){

    }

    public Response_Find(String frdMail, String frdName, String frdTokenId, String code) {
        this.frdMail = frdMail;
        this.frdName = frdName;
        this.frdTokenId = frdTokenId;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFrdMail() {
        return frdMail;
    }

    public void setFrdMail(String frdMail) {
        this.frdMail = frdMail;
    }

    public String getFrdName() {
        return frdName;
    }

    public void setFrdName(String frdName) {
        this.frdName = frdName;
    }

    public String getFrdTokenId() {
        return frdTokenId;
    }

    public void setFrdTokenId(String frdTokenId) {
        this.frdTokenId = frdTokenId;
    }
}
