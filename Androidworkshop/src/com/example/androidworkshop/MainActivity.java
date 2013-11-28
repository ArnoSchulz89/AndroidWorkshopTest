package com.example.androidworkshop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView jsonTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		jsonTextView = (TextView) findViewById(R.id.text_json_download);
		showJSONDummy();
	}

	/**
	 * handles clicks
	 * 
	 * @param view
	 *            clicked view from the layout file
	 */
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.button_list_activity:
			Intent intent = new Intent(this, ExampleListActivity.class);
			intent.putStringArrayListExtra("list",
					(ArrayList<String>) getDummyDataList());

			startActivity(intent);
			break;
		case R.id.button_download_json:
			// url to get JSON file from
			String stringUrl = "http://api.mymemory.translated.net/get?q=Hello&langpair=en|it";

			// check if there is an Internet connection
			ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

			if (networkInfo != null && networkInfo.isConnected()) {
				new DownloadJSONTask().execute(stringUrl);
			} else {
				jsonTextView.setText("No network connection available.");
			}
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
			Log.d(MainActivity.class.toString(),
					jsonObject.getString("vorname"));
			Log.d(MainActivity.class.toString(), jsonObject.getInt("alter")
					+ "");
			Log.d(MainActivity.class.toString(),
					jsonObject.getBoolean("single") + "");
			Log.d(MainActivity.class.toString(),
					jsonObject.getJSONArray("hobbys").toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * creates a JSONObject from the file "person.json" of the assets folder
	 * 
	 * @return JSONObject
	 */
	private JSONObject readJSONFromFile() {
		String jsonString = null;
		JSONObject jsonObject = null;
		try {
			// open the inputStream to the file
			InputStream inputStream = getAssets().open("person.json");

			int sizeOfJSONFile = inputStream.available();

			// array that will store all the data
			byte[] bytes = new byte[sizeOfJSONFile];

			// reading data into the array from the file
			inputStream.read(bytes);

			// close the input stream
			inputStream.close();

			jsonString = new String(bytes, "UTF-8");
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
	 * 
	 * @return ArrayList with dummy data
	 */
	private List<String> getDummyDataList() {
		List<String> dummyDataList = new ArrayList<String>();
		dummyDataList.add("Element 1");
		dummyDataList.add("Element 2");
		dummyDataList.add("Element 3");

		return dummyDataList;
	}

	private class DownloadJSONTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... stringUrl) {
			// generate an URL
			URL url = null;
			try {
				url = new URL(stringUrl[0]);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			// create the connection
			try {
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setRequestProperty("Content-Type","application/json");

				// create a buffered reader if the response code is OK
				// create a response String (the JSON text)
				int responseCode = urlConnection.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					BufferedReader in = new BufferedReader(
							new InputStreamReader(
									urlConnection.getInputStream()));
				
				    // read line by line of the response and create 
					// text block of all lines with the StringBuilder
					String response = "";
					String responseLine = "";
					while ((responseLine = in.readLine()) != null) {
						response += responseLine + "\n";
	                }
					in.close();
					urlConnection.disconnect();
					
					return response;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}
		
		// after the download we can create an JSONObject from the response
		@Override
		protected void onPostExecute(String response) {
			if(response != null) {
				JSONObject jsonMainNode;
		        try {
		            JSONObject jsonResponse = new JSONObject(response);
		            jsonMainNode = jsonResponse.getJSONObject("responseData");
		            
		            // show the translated text in the text view of this layout
		            jsonTextView.setText(jsonMainNode.getString("translatedText"));
		        } catch (JSONException e) {
		            e.printStackTrace();
		        }
			}
			
			super.onPostExecute(response);
		}
	}
}