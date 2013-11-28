package com.example.androidworkshop;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AccelerometerExampleActivity extends Activity implements
		SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    
    private TextView sensingText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.accelerometer_activity);
    	
    	// The SensorManager handles our sensors
    	sensorManager = (SensorManager) getApplicationContext()
                .getSystemService(SENSOR_SERVICE);
    	// in this example we are using the accelerometer
        accelerometer = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
        // get the text view to display data
        sensingText = (TextView) findViewById(R.id.text_sensing_data);
    }
    
    public void onClick(View view) {
    	switch (view.getId()) {
		case R.id.button_start_sensing:
			// registerListener starts the sensor
			sensorManager.registerListener(
					this,	// context
					accelerometer, //sensor
					SensorManager.SENSOR_DELAY_NORMAL // how often we want to get values from the sensor
             );
			break;
		case R.id.button_stop_sensing:
			// unregister the sensor to stop sensing
			sensorManager.unregisterListener(this);
			// clear the text view when stopped
			sensingText.setText("");
			break;
		}
    }
    
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// nothing to do here in this example 
	}
	
	// called whenever we get new values from the sensor
	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			String sensingData = ""
					+ "x-value: " + sensorEvent.values[0] + "\n"
					+ "y-value: " + sensorEvent.values[1] + "\n"
					+ "z-value: " + sensorEvent.values[2] + "\n";
			sensingText.setText(sensingData);
		 }
	}
}
