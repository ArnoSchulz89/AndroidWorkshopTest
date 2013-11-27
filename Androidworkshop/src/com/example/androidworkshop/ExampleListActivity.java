package com.example.androidworkshop;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ExampleListActivity extends ListActivity{
	private List<String> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		list = getIntent().getStringArrayListExtra("list");
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this,
				android.R.layout.simple_list_item_1,
				list
		);
		setListAdapter(adapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String toastMessage = list.get(position);
		Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
		super.onListItemClick(l, v, position, id);
	}
}