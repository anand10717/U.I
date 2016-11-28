package com.uandme.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.uandme.singleton.AppLocalData;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {



    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Token ######", "Refreshed token: " + refreshedToken);

        AppLocalData.getInstance(getApplicationContext()).saveData(AppLocalData.MY_TOKEN,refreshedToken);

    }

    private void sendRegistrationToServer(String token,String old) {


    }

}
