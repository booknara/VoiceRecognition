package com.booknara.voicerecognition;

import java.util.ArrayList;
import java.util.List;

import com.booknara.util.VoiceRecognitionIntentFactory;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final int REQUEST_CODE = 1;
	private ListView mResultList;
	private Button mMicButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mMicButton = (Button) findViewById(R.id.mic_button);
		mResultList = (ListView) findViewById(R.id.result_list);

		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities == null || activities.size() == 0) {
		   mMicButton.setEnabled(false);
		   Toast.makeText(getApplicationContext(), "Not Supported", Toast.LENGTH_LONG).show();
		}
		mMicButton.setOnClickListener(new OnClickListener() {
		   @Override
		   public void onClick(View v) {
			   runVoiceRecognition();
		   }
		});
	}

	private void runVoiceRecognition() {
		Intent intent = VoiceRecognitionIntentFactory.getFreeFormRecognizeIntent("Speak Now...");
		startActivityForResult(intent, REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			mResultList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, matches));
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.voice, menu);
		return true;
	}

}
