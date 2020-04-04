package com.ontecxmedia.ontecx;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ontecxmedia.ontecx.activities.MainActivity;

import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Splash extends AppCompatActivity {

    @BindView(R.id.img_logo)
    ImageView imgLogo;

    @BindView(R.id.txt_version)
    TextView txtVersion;


    // Animation
    Animation slide_front_left, blink,slide_front_right,zoomIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//      Create instance of Preference class
      //  UserPreferences userPreferences = new UserPreferences(this);

//        Display app version on the Screen
        try{
            PackageInfo packageInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = "Version: " + packageInfo.versionName;
            txtVersion.setText(version);
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }


            // load the animation
        zoomIn = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.zoom_in);

            blink = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.blink);

            //start animation
            imgLogo.startAnimation(zoomIn);


            Thread myThread = new Thread(){
                @Override
                public void run() {
                    try {
                        sleep(3000);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            myThread.start();


    }

}
