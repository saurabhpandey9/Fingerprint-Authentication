package com.developerdesk9.fingerprintauth;

import android.app.Activity;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;


public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {


    private Context context;
    public FingerprintHandler(Context context){
            this.context=context;
    }

    public void Auth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){

        CancellationSignal cancellationSignal=new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject,cancellationSignal,0,this,null);

    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        this.update("Authentication error"+ errString,false);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Authentication failed",false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        this.update(""+helpString,false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("Authenticated",true);
    }

    private void update(String s, boolean b) {

        TextView textView= (TextView) ((Activity)context).findViewById(R.id.fingerprint_tv);
        ImageView logo=(ImageView)((Activity)context).findViewById(R.id.fingerprint_logo_IV);

        textView.setText(s);

        if (b==true){
            textView.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
              logo.setImageResource(R.mipmap.tick_icon);
        }


    }
}
