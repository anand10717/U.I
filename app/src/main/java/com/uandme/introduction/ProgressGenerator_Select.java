package com.uandme.introduction;

import com.dd.processbutton.ProcessButton;
import com.uandme.retrofit.ApiClient;
import com.uandme.retrofit.ApiInterface;
import com.uandme.retrofit.Response_Find;
import com.uandme.retrofit.Response_Find;
import com.uandme.singleton.AppLocalData;

import retrofit2.Call;
import retrofit2.Callback;

public class ProgressGenerator_Select {

    public interface OnCompleteListener {

        public void onComplete();
    }

    private OnCompleteListener mListener;
    SelectFriend_Activity activity;
    private int mProgress = 0;

    private String mailid ;
    AppLocalData appLocalData;

    public ProgressGenerator_Select(OnCompleteListener listener, SelectFriend_Activity activity) {
        mListener = listener;
        this.activity = activity;
    }

    public void start(final ProcessButton button) {


         mailid = activity.editFriendEmail.getText().toString();
         appLocalData = AppLocalData.getInstance(activity.getContext());

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<Response_Find> call = apiService.find(
                mailid
                );

        call.enqueue(new Callback<Response_Find>() {
            @Override
            public void onResponse(Call<Response_Find> call, retrofit2.Response<Response_Find> response) {

                if("200".equalsIgnoreCase(response.body().getCode())) {
                    button.setText("Connected");
                    appLocalData.saveData(AppLocalData.FRD_NAME,response.body().getFrdName());
                    appLocalData.saveData(AppLocalData.FRD_MAILID,mailid);

                    activity.frdName.setText(response.body().getFrdName());
                } else {
                    button.setText("User Not Found");
                }

                mListener.onComplete();
            }

            @Override
            public void onFailure(Call<Response_Find> call, Throwable t) {
                button.setText("Failed");
                mListener.onComplete();
            }
        });
    }

}