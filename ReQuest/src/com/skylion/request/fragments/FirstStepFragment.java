package com.skylion.request.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.skylion.request.R;
import com.skylion.request.views.NewRequestHolder;

public class FirstStepFragment extends Fragment {
	
	private View view;
	
	private EditText titleEdit;
	private EditText descEdit;
	private EditText companyEdit;
	private ImageButton logoImageButton;
	private ImageView logoImageView;
	private Button nextButton;
	
	private NewRequestHolder meetingStepsHolderScreen;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_first_step, container, false);
		
		initScreen();
		
		return view;
	}

	private void initScreen() {
		meetingStepsHolderScreen = (NewRequestHolder) getActivity();
		
		((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.request_first_step));
		
		titleEdit = (EditText) view.findViewById(R.id.newRequest_rewardText);	
		descEdit = (EditText) view.findViewById(R.id.newRequest_descriptionText);	
		companyEdit = (EditText) view.findViewById(R.id.newRequest_companyText);
		logoImageButton = (ImageButton) view.findViewById(R.id.newRequest_logoButton);
		logoImageView = (ImageView) view.findViewById(R.id.newRequest_logoImage);
		nextButton = (Button) view.findViewById(R.id.newRequest_nextButton);
		
		nextButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				meetingStepsHolderScreen.showFragment(1, true);
			}
		});
	}

}
