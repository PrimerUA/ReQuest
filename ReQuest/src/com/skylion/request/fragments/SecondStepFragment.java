package com.skylion.request.fragments;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.skylion.request.R;
import com.skylion.request.views.NewRequestHolder;

public class SecondStepFragment extends Fragment {
	
	private View view;
	
	private EditText rewardEdit; //проверку на 0000 !!!
	private Button backButton;
	private Button createButton;
	
	private NewRequestHolder newRequestHolder;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_second_step, container, false);
		
		initScreen();
		
		return view;
	}

	private void initScreen() {
		newRequestHolder = (NewRequestHolder) getActivity();
		//((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.request_second_step));
		rewardEdit = (EditText) view.findViewById(R.id.newRequest_rewardText);	
		backButton = (Button) view.findViewById(R.id.newRequest_backButton);
		createButton = (Button) view.findViewById(R.id.newRequest_createButton);
		
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				newRequestHolder.showFragment(0, true);
			}
		});
		
		createButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				getActivity().finish();
			}
		});
		
		
	}
//	protected void sendTo(ParseUser parseUser) {
//		ParseFile parseVideoFile = new ParseFile("video.3gp", newRequestHolder.getImage());
//		ParseObject message = new ParseObject("Message");
//		message.put("text", descEdit.getText().toString());
//		message.put("videoFile", parseVideoFile);
//		message.put("author", ParseUser.getCurrentUser());
//		if (parseUser == null)
//			message.put("email", contactEdit.getText().toString());
//		else
//			message.put("targetUser", parseUser);
//		message.saveInBackground(new SaveCallback() {
//			@Override
//			public void done(ParseException e) {
//				progressDialog.dismiss();
//				Toast.makeText(SendActivity.this, "Video message send to " + contactEdit.getText().toString(), Toast.LENGTH_LONG).show();
//				finish();
//			}
//		});
//	}

}
