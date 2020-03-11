package com.example.gyro;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView textView;
    TextView textX, textY, textZ;

    private SensorManager sensorManager;

    private Sensor gyroscopeSensor;
    private Sensor accelerometerSensor;

    private int currentSensor;

    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tvResult);
        textX = (TextView) findViewById(R.id.textX);
        textY = (TextView) findViewById(R.id.textY);
        textZ = (TextView) findViewById(R.id.textZ);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

    }


    public boolean checkSensorAvailability(int sensorType) {
        boolean isSensor = false;
        if (sensorManager.getDefaultSensor(sensorType) != null) {
            isSensor = true;
        }
        return isSensor;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        if (event.sensor.getType() == currentSensor) {

            if (currentSensor == Sensor.TYPE_GYROSCOPE) {
                System.out.println("entered");
                if (event.values[2] > 0.5f) {
                    textView.setText("Anti Clock");
                    textX.setText("X : " + (int) x + " rad/s");
                    textY.setText("Y : " + (int) y + " rad/s");
                    textZ.setText("Z : " + (int) z + " rad/s");
                } else if (event.values[2] < -0.5f) {
                    textView.setText("Clock");
                    textX.setText("X : " + (int) x + " rad/s");
                    textY.setText("Y : " + (int) y + " rad/s");
                    textZ.setText("Z : " + (int) z + " rad/s");

                }
            }
             if (currentSensor == Sensor.TYPE_ACCELEROMETER) {

                long curTime = System.currentTimeMillis();
                  x = event.values[0];
                  y = event.values[1];
                  z = event.values[2];
                if ((curTime - lastUpdate) > 100) {
                    long diffTime = (curTime - lastUpdate);
                    lastUpdate = curTime;

                    float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

                    if (speed > SHAKE_THRESHOLD) {
                        Toast.makeText(getApplicationContext(), "Your phone just shook", Toast.LENGTH_LONG).show();
                        textX.setText("X : " + (int) x + " m/s^2");
                        textY.setText("Y : " + (int) y + " m/s^2");
                        textZ.setText("Z : " + (int) z + " m/s^2");
                    }

                    last_x = x;
                    last_y = y;
                    last_z = z;
                }
            }
        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void gyroscopeSensorOnClick(View view) {
        if (checkSensorAvailability(Sensor.TYPE_GYROSCOPE)) {
            currentSensor = Sensor.TYPE_GYROSCOPE;
        } else {
            textView.setText("Gyroscope not available");
        }
    }
    public void accelerometerSensorOnClick(View view) {
        if (checkSensorAvailability(Sensor.TYPE_ACCELEROMETER)) {
            currentSensor = Sensor.TYPE_ACCELEROMETER;
        } else {
            textView.setText("Accelerometer not available");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

       sensorManager.registerListener(this, accelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL);

        sensorManager.registerListener(this, gyroscopeSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


}

