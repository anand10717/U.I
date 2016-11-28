package com.uandme.introduction;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;
import com.uandme.R;

import agency.tango.materialintroscreen.SlideFragment;

public class Login_Activity extends SlideFragment implements ProgressGenerator_Login.OnCompleteListener,View.OnClickListener{

    private static final String EXTRAS_ENDLESS_MODE = "EXTRAS_ENDLESS_MODE";

    private boolean verified = false;

     ActionProcessButton btnSignIn ;

     public EditText editEmail ;
     public EditText editPassword ;
     public EditText editName ;

     ProgressGenerator_Login progressGenerator;

     Login_Activity login_activity ;

    @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_login_, container, false);

        editEmail = (EditText) view.findViewById(R.id.mailid);
        editPassword = (EditText) view.findViewById(R.id.password);
        editName = (EditText) view.findViewById(R.id.myname);

        btnSignIn = (ActionProcessButton) view.findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);

        login_activity= this;

        return view;
    }

    @Override
    public void onClick(View v) {
        Log.d("button","clicked");

        progressGenerator = new ProgressGenerator_Login(this,login_activity);
        progressGenerator.start(btnSignIn);

        btnSignIn.setEnabled(false);
        editEmail.setEnabled(false);
        editPassword.setEnabled(false);
    }
    @Override
    public int backgroundColor() {
        return R.color.custom_slide_background;
    }

    @Override
    public int buttonsColor() {
        return R.color.custom_slide_buttons;
    }

    @Override
    public boolean canMoveFurther() {
        return verified;
    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return "Login to Continue.";
    }

    @Override
    public void onComplete() {
        editEmail.setEnabled(true);
        editPassword.setEnabled(true);
        btnSignIn.setEnabled(true);

        if(btnSignIn.getText().toString().equalsIgnoreCase("success")){
            verified =true ;
        }else {
            verified = false;
        }
    }
}