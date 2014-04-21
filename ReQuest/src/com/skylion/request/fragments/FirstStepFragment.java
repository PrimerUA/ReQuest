package com.skylion.request.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.skylion.request.R;
import com.skylion.request.views.NewRequestHolder;

public class FirstStepFragment extends Fragment {

	private View view;

	private EditText titleEdit;
	private EditText companyEdit;
	private Button nextButton;
	
	public String testEditText = "";

	private NewRequestHolder newRequestHolder;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_first_step, container, false);

		initScreen();

		return view;
	}

	private void nextButtonStatus()
	{
		if(!"".equals(titleEdit.getText().toString()))
			nextButton.setEnabled(true);
		else
			nextButton.setEnabled(false);
	}
	
	private void initScreen() {
		newRequestHolder = (NewRequestHolder) getActivity();

		// ((ActionBarActivity)
		// getActivity()).getSupportActionBar().setTitle(getString(R.string.request_first_step));
		titleEdit = (EditText) view.findViewById(R.id.newRequest_titleText);
		descEdit = (EditText) view.findViewById(R.id.newRequest_descriptionText);		
		nextButton = (Button) view.findViewById(R.id.newRequest_nextButton);
		nextButtonStatus();
		titleEdit.addTextChangedListener(new TextWatcher() {
					
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				nextButtonStatus();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
				// TODO Auto-generated method stub
				nextButtonStatus();
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				nextButtonStatus();
			}
		});
		
		
		
		nextButton = (Button) view.findViewById(R.id.newRequest_nextButton);

		nextButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				if ("".equals(titleEdit.getText().toString()) || "".equals(descEdit.getText().toString()) || "".equals(companyEdit.getText().toString()))
					newRequestHolder.showFragment(1, true);
				
//				EditText companySalary = (EditText)view.findViewById(R.id.newRequest_companySalary);				
//				newRequestHolder.setSalary(companySalary.getText().toString());
				
				newRequestHolder.setVacancyName(((EditText)view.findViewById(R.id.newRequest_titleText)).getText().toString());
				newRequestHolder.setCompanyName(((EditText)view.findViewById(R.id.newRequest_companyText)).getText().toString());				
				newRequestHolder.setCompanySalary(((EditText)view.findViewById(R.id.newRequest_companySalary)).getText().toString());
				newRequestHolder.setCity(((EditText)view.findViewById(R.id.newRequest_city)).getText().toString());
				newRequestHolder.setDemands(((EditText)view.findViewById(R.id.newRequest_demands)).getText().toString());
				newRequestHolder.setTerms(((EditText)view.findViewById(R.id.newRequest_terms)).getText().toString());
				newRequestHolder.setCompanyDescription(((EditText)view.findViewById(R.id.newRequest_company_description)).getText().toString());
				newRequestHolder.setCompanyAddress(((EditText)view.findViewById(R.id.newRequest_company_address)).getText().toString());
				
			}
		});
	}

}
