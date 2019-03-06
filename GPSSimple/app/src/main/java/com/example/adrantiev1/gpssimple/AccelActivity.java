package com.example.adrantiev1.gpssimple;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AccelActivity extends AppCompatActivity implements SensorEventListener
{


    private SensorManager manager;
    TextView tvXCoor;
    TextView tvYCoor;
    TextView tvZCoor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accel);

        tvXCoor = (TextView)findViewById(R.id.x_coordinate);
        tvYCoor = (TextView)findViewById(R.id.y_coordinate);
        tvZCoor = (TextView)findViewById(R.id.z_coordinate);

        manager =(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        manager.registerListener(this,
                manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            tvXCoor.setText("X = " + x);
            tvYCoor.setText("Y = " + y);
            tvZCoor.setText("Z = " + z);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {
        //ignored
    }
}
