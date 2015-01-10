package com.together.awake.newlight;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.View.OnClickListener;



public class Main extends Activity implements OnClickListener{

    ImageButton btnSwitch;

    private boolean isFlashOn = false;
    private boolean hasFlash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSwitch = (ImageButton) findViewById(R.id.btnSwitch);

        hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!hasFlash) {
            // device doesn't support flash
            // Show alert message and close the application
            AlertDialog alert = new AlertDialog.Builder(Main.this)
                    .create();
            alert.setTitle("Error");
            alert.setMessage("Sorry, your device doesn't support flash light!");
            alert.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // closing the application
                    finish();
                }
            });
            alert.show();
            return;
        }
        btnSwitch.setImageResource(R.drawable.btn_switch_off);
        btnSwitch.setOnClickListener(this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MyService.class));
    }

    private void toggleButtonImage(){
        if(isFlashOn){
            btnSwitch.setImageResource(R.drawable.btn_switch_off);
            isFlashOn = false;
        }else{
            btnSwitch.setImageResource(R.drawable.btn_switch_on);
            isFlashOn = true;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSwitch:
                if (isFlashOn){
                    stopService(new Intent(this, MyService.class));
                    toggleButtonImage();

                }else {
                    startService(new Intent(this, MyService.class));
                    toggleButtonImage();
                }
        }
    }
}
