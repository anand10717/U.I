package com.uandme.singleton;

import android.app.Activity;

import com.uandme.MainActivity;

/**
 * Created by anand-3985 on 24/11/16.
 */
public class ActivitySingleton  {

    private MainActivity activity;
    private static ActivitySingleton s = null;

    private ActivitySingleton(){

    }
    public static MainActivity getInstance(){
        if(s == null){
            return null;
        }
        return s.activity;
    }
    public static void setInstance(MainActivity activity){
        if(s == null){
            s = new ActivitySingleton();
            s.activity = activity;
        }
    }
/*
    public Activity getActivity() {
        return activity;
    }

    private void setActivity(Activity activity) {
        this.activity = activity;
    }*/
}
