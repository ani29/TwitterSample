package com.example.twitterexample;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * 
 * @author Aniruddh
 *
 */
public class MainActivity extends Activity {

	Button searchButton;
	EditText searchBox;
	Context ctx ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = this ;
		setContentView(R.layout.activity_main);
		searchButton = (Button) findViewById(R.id.searchButton);
		searchBox = (EditText) findViewById(R.id.searchBox);
		searchButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				String searchKey = searchBox.getText().toString().trim();
				if(searchKey.length() == 0){
					Toast.makeText(ctx, "Please Enter Search Query", Toast.LENGTH_LONG).show();		
				}
				else{
					Intent i = new Intent(getApplicationContext(),TwitterListActivity.class);
					//sending search key with intent
					i.putExtra("SEARCH_STRING", searchKey);
					startActivity(i);
				}
			}
		});
	}
	
}