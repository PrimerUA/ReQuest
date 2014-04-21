package com.skylion.request.fragments;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.impl.cookie.DateParseException;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.text.method.DateTimeKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

	private EditText rewardEdit; // check for 0000 !!!
	private EditText expDate;
	private Date expDateToParse;
	private Button backButton;
	private Button createButton;
	private ImageButton logoImageButton;
	private ImageView logoImageView;

	private NewRequestHolder newRequestHolder;
	private ProgressDialog myProgressDialog;
	private boolean isSendDate;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_second_step, container, false);

		initScreen();

		return view;
	}
			
	private void checkDate() {
		
		String newRequestDate = expDate.getText().toString();
		SimpleDateFormat df = new SimpleDateFormat("HH-mm dd-MM-yyyy");				
		try {
			expDateToParse = df.parse(newRequestDate);
			isSendDate = true;
		} 
		catch (java.text.ParseException e1) {
			isSendDate = false;
			Toast.makeText(getActivity(), getString(R.string.date_formate_error) + " " + e1.getMessage(), Toast.LENGTH_LONG).show();
		}	
	}
	
	private void initScreen() {
		myProgressDialog = new ProgressDialog(getActivity());
		newRequestHolder = (NewRequestHolder) getActivity();
		// ((ActionBarActivity)
		// getActivity()).getSupportActionBar().setTitle(getString(R.string.request_second_step));
		rewardEdit = (EditText) view.findViewById(R.id.newRequest_rewardText);
		backButton = (Button) view.findViewById(R.id.newRequest_backButton);
		createButton = (Button) view.findViewById(R.id.newRequest_createButton);
		logoImageButton = (ImageButton) view.findViewById(R.id.newRequest_logoButton);
		logoImageView = (ImageView) view.findViewById(R.id.newRequest_logoImage);			
		expDate = (EditText) view.findViewById(R.id.newRequest_date);				
		
		logoImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), NewRequestHolder.PICK_IMAGE);							
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
				if("".equals(rewardEdit.getText().toString()))
					Toast.makeText(getActivity(), getString(R.string.fill_reward_field), Toast.LENGTH_LONG).show();
				else
				{
					if(Integer.parseInt(rewardEdit.getText().toString()) == 0)
						Toast.makeText(getActivity(), getString(R.string.fill_reward_not_zero), Toast.LENGTH_LONG).show();
					else
						sendVacancy();
				}				
			}
		});
		   
	}

	protected void sendVacancy() {
		checkDate();
		myProgressDialog = ProgressDialog.show(getActivity(), getString(R.string.connection),
				getString(R.string.connecting_new_vacancy), true);
		ParseObject vacancyObject = new ParseObject("Requests");
		vacancyObject.put("title", newRequestHolder.getVacancyName());
		vacancyObject.put("description", newRequestHolder.getCandidateDescription());
		vacancyObject.put("reward", Integer.parseInt(rewardEdit.getText().toString()));
		vacancyObject.put("user", ParseUser.getCurrentUser());
		vacancyObject.put("company", newRequestHolder.getCompanyName());
		vacancyObject.put("salary", newRequestHolder.getCompanySalary());
		vacancyObject.put("city", newRequestHolder.getCity());
		vacancyObject.put("demands", newRequestHolder.getDemands());
		vacancyObject.put("terms", newRequestHolder.getTerms());
		vacancyObject.put("company_description", newRequestHolder.getCompanyDescription());
		vacancyObject.put("company_address", newRequestHolder.getCompanyAddress());
		if(isSendDate)
			vacancyObject.put("expire", expDateToParse);
		if (newRequestHolder.getImage() != null) {
			ParseFile file = new ParseFile("logo.png", newRequestHolder.getImage());
			vacancyObject.put("image", file);
		}																	
		
		vacancyObject.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				if (e == null) {
					myProgressDialog.dismiss();
					Toast.makeText(getActivity(), R.string.vacancy_created, Toast.LENGTH_LONG).show();
					getActivity().finish();
				}
				else 
				{
					Toast.makeText(getActivity(), getString(R.string.vacancy_creation_error) + " " + e.getMessage(),
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == NewRequestHolder.PICK_IMAGE && data != null && data.getData() != null) {
			Uri uri = data.getData();
			Cursor cursor = newRequestHolder.getContentResolver().query(uri,
					new String[] { android.provider.MediaStore.Images.ImageColumns.DATA }, null, null, null);
			cursor.moveToFirst();
			newRequestHolder.setImage(newRequestHolder.read(new File(cursor.getString(0))));
			
			if(newRequestHolder.getImage() != null)
			{
				byte[] imgdata = newRequestHolder.getImage();
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 1;
				Bitmap bitmap = BitmapFactory.decodeByteArray(imgdata, 0, imgdata.length, options);
				logoImageView.setImageBitmap(bitmap);
			}
			else
				logoImageView.setVisibility(View.GONE);
		}
	}
	
	private void updateLabel() {
		// TODO Auto-generated method stub
		
	}

}
