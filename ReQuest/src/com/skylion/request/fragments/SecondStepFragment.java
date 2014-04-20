package com.skylion.request.fragments;

import java.util.zip.Inflater;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.appcompat.R.layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.skylion.request.R;
import com.skylion.request.views.NewRequestHolder;
import com.skylion.request.fragments.*;;

public class SecondStepFragment extends Fragment {

	private View view;

	private EditText rewardEdit; // check for 0000 !!!
	private Button backButton;
	private Button createButton;
	private ImageButton logoImageButton;
	private ImageView logoImageView;	

	private NewRequestHolder newRequestHolder;	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_second_step, container, false);

		initScreen();

		return view;
	}
		
	private void initScreen() {
		newRequestHolder = (NewRequestHolder) getActivity();
		// ((ActionBarActivity)
		// getActivity()).getSupportActionBar().setTitle(getString(R.string.request_second_step));
		rewardEdit = (EditText) view.findViewById(R.id.newRequest_rewardText);
		backButton = (Button) view.findViewById(R.id.newRequest_backButton);
		createButton = (Button) view.findViewById(R.id.newRequest_createButton);
		logoImageButton = (ImageButton) view.findViewById(R.id.newRequest_logoButton);
		logoImageView = (ImageView) view.findViewById(R.id.newRequest_logoImage);
		
		logoImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, getString(R.string.choose_logo_text)), NewRequestHolder.PICK_IMAGE);
			}
		});

		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				newRequestHolder.showFragment(0, true);
			}
		});				
		
		createButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// check for 0 and sent data to parse
				int reward = Integer.parseInt(((EditText)view.findViewById(R.id.newRequest_rewardText)).getText().toString());
				ParseObject vacancyObject = new ParseObject("Requests");
//				EditText requestTitle = (EditText)view.findViewById(R.id.newRequest_rewardText);				
				vacancyObject.put("title", newRequestHolder.getVacancyName());
				vacancyObject.put("description", newRequestHolder.getCandidateDescription());
				vacancyObject.put("reward", reward);
//				vacancyObject.put("image", newRequestHolder.);				
				vacancyObject.put("user", ParseUser.getCurrentUser());
				vacancyObject.put("company", newRequestHolder.getCompanyName());
				vacancyObject.put("salary", newRequestHolder.getCompanySalary());
				vacancyObject.put("city", newRequestHolder.getCity());
				vacancyObject.put("demands", newRequestHolder.getDemands());
				vacancyObject.put("terms", newRequestHolder.getTerms());
				vacancyObject.put("company_description", newRequestHolder.getCompanyDescription());
				vacancyObject.put("company_address", newRequestHolder.getCompanyAddress());
				vacancyObject.saveInBackground();
				getActivity().finish();															
			}
		});

	}
	// protected void sendTo(ParseUser parseUser) {
	// ParseFile parseVideoFile = new ParseFile("video.3gp",
	// newRequestHolder.getImage());
	// ParseObject message = new ParseObject("Message");
	// message.put("text", descEdit.getText().toString());
	// message.put("videoFile", parseVideoFile);
	// message.put("author", ParseUser.getCurrentUser());
	// if (parseUser == null)
	// message.put("email", contactEdit.getText().toString());
	// else
	// message.put("targetUser", parseUser);
	// message.saveInBackground(new SaveCallback() {
	// @Override
	// public void done(ParseException e) {
	// progressDialog.dismiss();
	// Toast.makeText(SendActivity.this, "Video message send to " +
	// contactEdit.getText().toString(), Toast.LENGTH_LONG).show();
	// finish();
	// }
	// });
	// }

}
