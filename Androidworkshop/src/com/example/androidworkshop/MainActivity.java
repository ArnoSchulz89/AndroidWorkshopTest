package com.example.androidworkshop;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		showJSONDummy();
	}
	
	/**
	 * handles clicks
	 * @param view clicked view from the layout file
	 */
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.button_list_activity:
			Intent intent = new Intent(this, ExampleListActivity.class);
			intent.putStringArrayListExtra("list", (ArrayList<String>) getDummyDataList());

			startActivity(intent);
			break;
		}
	}
	
	/**
	 * displays the content of a specific JSON file in LogCat
	 */
	private void showJSONDummy() {
		try {
			JSONObject jsonObject = readJSONFromFile();
			Log.d(MainActivity.class.toString(), jsonObject.getString("name"));
			Log.d(MainActivity.class.toString(), jsonObject.getString("vorname"));
			Log.d(MainActivity.class.toString(), jsonObject.getInt("alter")+"");
			Log.d(MainActivity.class.toString(), jsonObject.getBoolean("single")+"");
			Log.d(MainActivity.class.toString(), jsonObject.getJSONArray("hobbys").toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * creates a JSONObject from the file "person.json" of the assets folder
	 * @return JSONObject
	 */
	private JSONObject readJSONFromFile() {
		String jsonString = null;
		JSONObject jsonObject = null;
		try {
			 //open the inputStream to the file 
			InputStream inputStream = getAssets().open("person.json");

	        int sizeOfJSONFile = inputStream.available();

	        //array that will store all the data 
	        byte[] bytes = new byte[sizeOfJSONFile];

	        //reading data into the array from the file
	        inputStream.read(bytes);

	        //close the input stream
	        inputStream.close();
	        
	        jsonString = new String(bytes,"UTF-8");
	        jsonObject = new JSONObject(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	/**
	 * generates an ArrayList with dummy data
	 * @return ArrayList with dummy data
	 */
	private List<String> getDummyDataList() {
		List<String> dummyDataList = new ArrayList<String>();
		dummyDataList.add("Element 1");
		dummyDataList.add("Element 2");
		dummyDataList.add("Element 3");
		
		return dummyDataList;
	}
}