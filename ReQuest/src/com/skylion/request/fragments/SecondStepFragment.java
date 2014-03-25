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

}
