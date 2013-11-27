package com.example.androidworkshop;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	/**
	 * handles clicks
	 * @param view clicked view
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
	 * generates an ArrayList with dummy data
	 * @return ArrayList with dummy data
	 */
	public List<String> getDummyDataList() {
		List<String> dummyDataList = new ArrayList<String>();
		dummyDataList.add("Element 1");
		dummyDataList.add("Element 2");
		dummyDataList.add("Element 3");
		
		return dummyDataList;
	}
}