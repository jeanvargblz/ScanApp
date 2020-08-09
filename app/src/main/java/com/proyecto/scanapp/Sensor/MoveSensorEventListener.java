package com.proyecto.scanapp.Sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

public class MoveSensorEventListener implements SensorEventListener {
    private static final String TAG = "AcceSensorEventListener";

    private float alpha = 0.3f;
    private float beta = 0.3f;
    private float[] accelerationValues;
    private boolean estadoMensaje;
    private DoubleExponentialSmoothing doubleSmoothing;

    public MoveSensorEventListener(){
        doubleSmoothing = new DoubleExponentialSmoothing(alpha,beta);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        double sumOfSquares, acceleration,acceleration1;
        accelerationValues = event.values.clone();
        float currentx = accelerationValues[0];
        float currenty = accelerationValues[1];
        float currentz = accelerationValues[2];


        sumOfSquares = (currentx * currentx) + (currenty * currenty) + (currentz * currentz);
        acceleration1 = Math.sqrt(sumOfSquares);

        doubleSmoothing.pushValue((float) acceleration1);
        acceleration = doubleSmoothing.getValue();

        determineMovement(acceleration);

        Log.d(TAG,  acceleration1+ ","+ acceleration);


    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void determineMovement(double acceleration) {

        if (acceleration >= 3 && !estadoMensaje) {

            LectorActivity.getInstance().showMessage();

            Log.d("ESTADO MOVIMIENTO", "NO MUEVA EL DISPOSITIVO");
            estadoMensaje = true;

        } else if(acceleration <=1 && estadoMensaje) {
            Log.d("ESTADO MOVIMIENTO", "DISPOSITIVO QUIETO ");
            estadoMensaje = false;
        }

    }

}
