package com.uandme.dbhandle;

/**
 * Created by anand-3985 on 25/11/16.
 */
public class Messages {
    private String id;
    private String sender = "";
    private String timestamp = "";
    private String msg = "";
    private String img_uri = "";

    public Messages(){

    }
    public Messages(String id, String timestamp, String sender, String msg, String img_uri) {
        this.id = id;
        this.sender = sender;
        this.timestamp = timestamp;
        this.msg = msg;
        this.img_uri = img_uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getImg_uri() {
        return img_uri;
    }

    public void setImg_uri(String img_uri) {
        this.img_uri = img_uri;
    }
@Override
    public String toString(){


    return "id ## " + id + " sender ## " + sender +" time ## " +timestamp + " msg ## " + msg ;
}
}
