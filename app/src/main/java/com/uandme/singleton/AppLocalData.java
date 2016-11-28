package com.uandme.singleton;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by anand-3985 on 24/11/16.
 */
public class AppLocalData {
    private  SharedPreferences sharedpreferences;
    private Context context;
    private static AppLocalData appLocalData;

    public static final String KEY_PREF = "uandme" ;
    public static final String MY_TOKEN = "mytoken";
    public static final String MY_CODE = "mycode";
    public static final String MY_MSG_TABLE = "mymsgtable";

    public static final String MY_MAILID = "mailid" ;
    public static final String MY_PASSWORD = "password";

    public static final String FRD_MAILID = "frdmailid" ;

    public static final String MY_NAME = "myname";
    public static final String FRD_NAME = "frdname";

    public boolean alive ;

    private AppLocalData(){

    }
    public static  AppLocalData getInstance(Context context){
        if(appLocalData == null){
            appLocalData = new AppLocalData();
            appLocalData.sharedpreferences = context.getSharedPreferences(KEY_PREF, context.MODE_PRIVATE);
            appLocalData.context = context;
            appLocalData.alive = true;
        }
        return appLocalData;
    }
    public void saveData(String key,String value){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }
    public String getData(String key){
        return sharedpreferences.getString(key, "");
    }
}
