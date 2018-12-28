package com.example.user.fragmentexsemple;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{
//
SensorManager sensorManager;
    List<Sensor> sensorList=new ArrayList<>();

    SensorAdapter sensorAdapter;
    Sensor sensorAccelerometr;
    Sensor sensorGiroscope;
    final String tag = "IBMEyes";
    RecyclerView recyclerView;
//    TextView xViewA;
//    TextView yViewA;
//    TextView zViewA;
//    TextView xViewO = null;
//    TextView yViewO = null;
//    TextView zViewO = null;
    TextView tvText;




    StringBuilder sb = new StringBuilder();

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvText = (TextView) findViewById(R.id.tvText);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorAccelerometr = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorGiroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        sensorList.add(sensorAccelerometr);
        sensorList.add(sensorGiroscope);

        sensorAdapter = new SensorAdapter(this, sensorList);
        recyclerView.setAdapter(sensorAdapter);
        sensorAdapter.notifyDataSetChanged();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

      //   xViewA = (TextView) findViewById(R.id.X);
      //    yViewA = (TextView) findViewById(R.id.Y);
      //   zViewA = (TextView) findViewById(R.id.Z);
//        TextView xViewO = (TextView) findViewById(R.id.xboxo);
//        TextView yViewO = (TextView) findViewById(R.id.yboxo);
//        TextView zViewO = (TextView) findViewById(R.id.zboxo);

    }
   // @Override
        public void onSensorChanged(int sensor, float[] value) {
        synchronized (this) {

            Log.d(tag, "onSensorChanged: " + sensor + ", x: " +
                    value[0] + ", y: " + value[1] + ", z: " + value[2]);
//            if (sensor == SensorManager.) {
//                xViewO.setText("Orientation X: " + values[0]);
//                yViewO.setText("Orientation Y: " + values[1]);
//                zViewO.setText("Orientation Z: " + values[2]);
//            }
            if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
//                xViewA.setText("Accel X: " + value[0]);
//                yViewA.setText("Accel Y: " + value[1]);
//                zViewA.setText("Accel Z: " + value[2]);
            }
        }
        }


    @Override
    public void onClick(View v) {
        int selectedItemposition=recyclerView.getChildPosition(v);
        Sensor sensor=sensorList.get(selectedItemposition);
        Intent intent=new Intent(this,GrafActivity.class);
        intent.putExtra( "sensortype",sensor.getType());
        this.startActivity(intent);
    }

///*****
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(listener, sensorAccelerometr,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listener, sensorGiroscope,
                SensorManager.SENSOR_DELAY_NORMAL);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showInfo();
                    }
                });
            }
        };
        timer.schedule(task, 0, 400);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listener);
       // timer.cancel();
    }

    String format(float values[]) {
        return String.format("%1$.1f\t\t%2$.1f\t\t%3$.1f", values[0], values[1],
                values[2]);
    }

    void showInfo() {
        sb.setLength(0);
        sb.append("Акселерометр: " + format(valuesAccel) +  "  m/s^2")

                .append("\nГироскоп : " + format(valuesGiroscope) + "  rad/s" );


      tvText.setText(sb);
    }

    float[] valuesAccel = new float[3];
    float[] valuesGiroscope = new float[3];

    SensorEventListener listener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    for (int i = 0; i < 3; i++) {
                        valuesAccel[i] = event.values[i];

                    }
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    for (int i = 0; i < 3; i++) {
                        valuesGiroscope[i] = event.values[i];
                    }
                    break;

            }

        }

    };

}

