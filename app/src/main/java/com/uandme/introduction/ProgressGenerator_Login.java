package com.uandme.introduction;

import com.dd.processbutton.ProcessButton;
import com.uandme.MainActivity;
import com.uandme.retrofit.ApiClient;
import com.uandme.retrofit.ApiInterface;
import com.uandme.retrofit.Response_Login;
import com.uandme.singleton.AppLocalData;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;

public class ProgressGenerator_Login {

    public interface OnCompleteListener {

        public void onComplete();
    }

    private OnCompleteListener mListener;
    Login_Activity login_activity;
    private int mProgress = 0;

    public ProgressGenerator_Login(OnCompleteListener listener, Login_Activity login_activity) {
        mListener = listener;
        this.login_activity = login_activity;
    }

    public void start(final ProcessButton button) {

        final String mailid = login_activity.editEmail.getText().toString();
        final String password = login_activity.editPassword.getText().toString();
        final  String name = login_activity.editName.getText().toString();
        final AppLocalData appLocalData = AppLocalData.getInstance(login_activity.getContext());

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<Response_Login> call = apiService.login(
                mailid,
                password,
                appLocalData.getData(AppLocalData.MY_TOKEN),
                name);

        call.enqueue(new Callback<Response_Login>() {
            @Override
            public void onResponse(Call<Response_Login> call, retrofit2.Response<Response_Login> response) {

                if("200".equalsIgnoreCase(response.body().getCode())) {
                    button.setText("Success");
                    appLocalData.saveData(AppLocalData.MY_MAILID,mailid);
                    appLocalData.saveData(AppLocalData.MY_PASSWORD,password);
                    appLocalData.saveData(AppLocalData.MY_NAME,response.body().getName());
                } else {
                    button.setText("Invalid User");
                }

                mListener.onComplete();
            }

            @Override
            public void onFailure(Call<Response_Login> call, Throwable t) {
                button.setText("Failed");
                Toast.makeText(login_activity.getContext(),"Network Error",Toast.LENGTH_LONG).show();

                mListener.onComplete();
            }
        });
    }

}