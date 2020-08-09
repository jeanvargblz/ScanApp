package com.proyecto.scanapp.Sensor;

public class DoubleExponentialSmoothing {
    private float alpha;
    private float beta;
    private float tendence;
    private float lastFiltered;
    private float current;
    private boolean isFirst = true;

    public DoubleExponentialSmoothing(float alpha, float beta){
        this.alpha = alpha;
        this.beta = beta;
    }
    public void pushValue(float x){
        if(isFirst){
            lastFiltered = x;
            tendence = x - tendence;
            isFirst = false;
        }
        else {
            current = doubleLowPass(x, lastFiltered, tendence);
            tendence = tendence(current, lastFiltered, tendence);
            lastFiltered = current;

        }
    }
    public float getValue (){return current;}

    private float doubleLowPass(float current, float last, float tendence){
        return (last + tendence)*(1.0f - alpha) + current*alpha;
    }
    private float tendence(float currentF, float lastF, float lastB){
        return  lastB * (1.0f - beta) + beta*(currentF - lastF);
    }
}
