package com.proyecto.scanapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.proyecto.scanapp.Sensor.MoveSensorEventListener;

import java.io.IOException;

public class LectorActivity extends AppCompatActivity {

    private static LectorActivity ins;
    SensorManager sensorManager;
    private SurfaceView camara;
    private BarcodeDetector detector;
    private CameraSource cameraSource;
    private String token;
    MoveSensorEventListener moveSensorEventListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ins = this;
        setContentView(R.layout.activity_lector);
        setTitle("Scaneando...");
        camara=findViewById(R.id.Scanner);
        initSensor();
        detector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource.Builder(this,detector).setRequestedPreviewSize(640,300).build();
        camara.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //PERMISOS CAMARA
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    return;
                }
                try{
                    cameraSource.start(camara.getHolder());
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        detector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> codes= detections.getDetectedItems();
                if(codes.size()!=0){
                    token=codes.valueAt(0).displayValue;
                    startActivity(new Intent(getApplicationContext(),ProductoActivity.class)
                            .putExtra("id",Integer.parseInt(token)));
                    finish();
                }
            }
        });
    }



    public static LectorActivity getInstance() {
        return ins;
    }


    public void showMessage(){
        Toast toast = Toast.makeText(this, "No mueva el Dispositivo", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void initSensor() {
        sensorManager  = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        moveSensorEventListener = new MoveSensorEventListener();
        sensorManager.registerListener(
                moveSensorEventListener,
                sensorAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(moveSensorEventListener);
    }




}
