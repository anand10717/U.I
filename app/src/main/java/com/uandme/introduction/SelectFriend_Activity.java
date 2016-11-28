package com.uandme.introduction;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.uandme.R;

import agency.tango.materialintroscreen.SlideFragment;

public class SelectFriend_Activity extends SlideFragment implements ProgressGenerator_Select.OnCompleteListener,View.OnClickListener{
    private static final String EXTRAS_ENDLESS_MODE = "EXTRAS_ENDLESS_MODE";
   
    private boolean verified = false;

     ActionProcessButton btnfind ;
     public EditText editFriendEmail;
    public TextView frdName ;

     ProgressGenerator_Select progressGenerator;

    SelectFriend_Activity activity ;
    @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_select, container, false);


        editFriendEmail = (EditText) view.findViewById(R.id.frdmailid);


        btnfind = (ActionProcessButton) view.findViewById(R.id.findfrd);
        btnfind.setOnClickListener(this);

        frdName = (TextView)view.findViewById(R.id.frdName);

        activity= this;
        return view;
    }

    @Override
    public void onClick(View v) {
        Log.d("button","clicked");
        progressGenerator = new ProgressGenerator_Select(this,activity);
        progressGenerator.start(btnfind);
        btnfind.setEnabled(false);
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
        return "Select Your Friend.";
    }

    @Override
    public void onComplete() {

        btnfind.setEnabled(true);
        if(btnfind.getText().toString().equalsIgnoreCase("Connected")){
        verified =true ;
        }else {
            verified = false;
        }
    }
}