package com.example.androidworkshop;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class ExampleService extends Service {
	private boolean running;
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		running = false;
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(intent != null) {
			if(intent.hasExtra("command_start_stop")) {
				if(running) {
					running = false;
					Toast.makeText(this, "stoppe Service", Toast.LENGTH_LONG).show();
					stopSelf();
				}
				else {
					running = true;
					Toast.makeText(this, "starte Service", Toast.LENGTH_LONG).show();
				}
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}
}