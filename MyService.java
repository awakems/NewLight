package com.together.awake.newlight;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;


public class MyService extends Service {

    private Camera camera;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        camera = Camera.open();
        final Parameters p = camera.getParameters();
        p.setFlashMode(Parameters.FLASH_MODE_TORCH);
        camera.setParameters(p);
        camera.startPreview();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "My Light OFF", Toast.LENGTH_LONG).show();
        final Parameters p = camera.getParameters();
        p.setFlashMode(Parameters.FLASH_MODE_OFF);
        camera.setParameters(p);
        camera.stopPreview();
        if (camera != null) {
            camera.release();
        }

    }

    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "My Light ON", Toast.LENGTH_LONG).show();

    }


}
