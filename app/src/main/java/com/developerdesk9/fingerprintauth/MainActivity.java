package com.developerdesk9.fingerprintauth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv_finger_auth;
    private ImageView logo;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo=findViewById(R.id.fingerprint_logo_IV);
        tv_finger_auth=findViewById(R.id.fingerprint_tv);

        // 1. check for SDK version
        // 2. check for hardware
        // 3. check for permission (mainfest)
        // 4. check whether app has any secured lock for not
        // 5.check for at least one finger registered

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            fingerprintManager= (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager=(KeyguardManager)getSystemService(KEYGUARD_SERVICE);

            if(!fingerprintManager.isHardwareDetected()){
                tv_finger_auth.setText("Hardware not detected");

            }
            else if(ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
                tv_finger_auth.setText("Permission denied !");
            }
            else if(!keyguardManager.isKeyguardSecure()){
                tv_finger_auth.setText("No lock on this app");
            }
            else if (!fingerprintManager.hasEnrolledFingerprints()){
                tv_finger_auth.setText("Please add your fingerprints");
            }
            else {
                tv_finger_auth.setText("Use fingerprint to UNLOCK");

                FingerprintHandler fingerprintHandler =new FingerprintHandler(this);
                fingerprintHandler.Auth(fingerprintManager,null);
            }

        }

    }
}
