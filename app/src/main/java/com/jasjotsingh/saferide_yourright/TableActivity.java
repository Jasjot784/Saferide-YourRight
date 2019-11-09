package com.jasjotsingh.saferide_yourright;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class TableActivity extends AppCompatActivity implements SensorEventListener {

    private TextView xText,yText,zText;
    private Sensor mySensor;
    private SensorManager SM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        xText = findViewById(R.id.xText);
        yText = findViewById(R.id.yText);
        zText = findViewById(R.id.zText);

        //create our sensor manager
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        //Accelerometer Sensor
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Register Sensor Listener
        SM.registerListener(this,mySensor,SensorManager.SENSOR_DELAY_NORMAL);

        //
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        xText.setText("X : "+event.values[0]);
        yText.setText("Y : "+event.values[0]);
        zText.setText("Z : "+event.values[0]);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //not in use

    }
}

