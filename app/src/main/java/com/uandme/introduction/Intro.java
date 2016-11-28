package com.uandme.introduction;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.FloatRange;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.uandme.MainActivity;
import com.uandme.R;
import com.uandme.singleton.AppLocalData;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;

public class Intro extends MaterialIntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(AppLocalData.getInstance(getApplicationContext()).getData(AppLocalData.FRD_NAME)!=null && !AppLocalData.getInstance(getApplicationContext()).getData(AppLocalData.FRD_NAME).equals(""))
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        enableLastSlideAlphaExitTransition(true);

        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });


        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.first_slide_background)
                        .buttonsColor(R.color.first_slide_buttons)
                        .image(R.drawable.welcome)
                        .title("Welcome")
//                        .neededPermissions(new String[]{Manifest.permission.ACCESS_NOTIFICATION_POLICY})

                        .description("Get Started")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMessage("");
                    }
                }, "")

        );

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.second_slide_background)
                .buttonsColor(R.color.second_slide_buttons)
                .image(R.drawable.logo)
                .title("U&Me")
                .description("Connect with your Special one")
                .build()
               );

        addSlide(new Login_Activity());
        addSlide(new SelectFriend_Activity());
        /*addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorAccent)
                .buttonsColor(R.color.colorPrimary)
                .title("Here you Go")
                .description("Enjoy your Privacy!!!")
                .build());*/
    }

    @Override
    public void onFinish() {
        super.onFinish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }




}
