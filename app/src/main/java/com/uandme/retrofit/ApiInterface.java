package com.uandme.retrofit;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by anand-3985 on 17/9/16.
 */public interface ApiInterface {
    @FormUrlEncoded
    @POST("uandme/send.php")
    Call<Response_send> send(@Field("id") long id, @Field("time") String time, @Field("sender") String sender, @Field("msg") String msg, @Field("mailid") String mailid);

    @FormUrlEncoded
    @POST("uandme/register.php")
    Call<Response_Login> login(@Field("mailid") String mailid, @Field("password") String password,@Field("tokenid") String tokenid,@Field("name") String name);

    @FormUrlEncoded
    @POST("uandme/find.php")
    Call<Response_Find> find(@Field("frdmailid") String frdmailid);

}
