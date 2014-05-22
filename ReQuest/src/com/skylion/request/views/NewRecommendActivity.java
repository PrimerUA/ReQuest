package com.skylion.request.views;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.skylion.request.R;
import com.skylion.request.entity.RequestConstants;
import com.skylion.request.utils.DateTimeSelector;
import com.skylion.request.utils.DateTimeSelectorListener;

public class NewRecommendActivity extends ActionBarActivity {

	public String vacancyId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_recommend);
		
		getSupportActionBar().setTitle(R.string.title_activity_new_recommend);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent intent = getIntent();
		vacancyId = intent.getStringExtra("vacancyObjectId");
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment(vacancyId)).commit();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recommend_candidate, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		finish();
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	@SuppressLint("ValidFragment")
	public static class PlaceholderFragment extends Fragment {

		private EditText NameEdit;
		private EditText DateEdit;
		private EditText SummaryeEdit;
		private EditText EmailEdit;
		private EditText ExperienceEdit;
		private EditText LastJobEdit;
		private EditText PostEdit;
		private EditText CommentEdit;
		private EditText summaryFileEdit;
		private Button sendButton;
		private ImageButton logoImageButton;
		private ImageButton logoImageButtonDate;
		private ImageView logoImageView;
		private Date bDate;
		private int status = RequestConstants.RESPOND_STATUS_NEW;
		private byte[] image = null;
		private byte[] summaryFile = null;

		private boolean isSendDate;
		private ParseObject vacancyObj;
		ParseObject rcCandidate;

		// private static Context mContext;

		private View rootView = null;
		private ProgressDialog myProgressDialog;
		private int PICK_IMAGE = 1;
		private int PICK_DOCUMENT = 2;
		private DateTimeSelector dateSelector;
		private String vacancyId;
		private Button documentButton;
		private String extension = "";
		private String photoExtension = "";
		private String resumExtension = "";

		@SuppressLint("ValidFragment")
		public PlaceholderFragment(String vacancyId) {
			this.vacancyId = vacancyId;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.fragment_new_recommend, container, false);
			initScreen();
			return rootView;
		}

		private void checkDate() {

			String newRequestDate = DateEdit.getText().toString();
			SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
			try {
				bDate = df.parse(newRequestDate);
				isSendDate = true;
			} catch (java.text.ParseException e1) {
				isSendDate = false;
				Toast.makeText(getActivity(), getString(R.string.rc_candidate_date_formate_error) + " " + e1.getMessage(),
						Toast.LENGTH_LONG).show();
			}
		}

		private void initScreen() {

			myProgressDialog = new ProgressDialog(getActivity());
			NameEdit = (EditText) rootView.findViewById(R.id.rcCandidate_name);
			DateEdit = (EditText) rootView.findViewById(R.id.rcCandidate_bdate);
			SummaryeEdit = (EditText) rootView.findViewById(R.id.rcCandidate_summary);
			EmailEdit = (EditText) rootView.findViewById(R.id.rcCandidate_email);
			ExperienceEdit = (EditText) rootView.findViewById(R.id.rcCandidate_experiance);
			LastJobEdit = (EditText) rootView.findViewById(R.id.rcCandidate_last_job);
			PostEdit = (EditText) rootView.findViewById(R.id.rcCandidate_post_at_last_job);
			CommentEdit = (EditText) rootView.findViewById(R.id.rcCandidate_comment);
			summaryFileEdit = (EditText) rootView.findViewById(R.id.rc_Candidate_summary_file);
			sendButton = (Button) rootView.findViewById(R.id.button_rcCandidate_send);
			logoImageButton = (ImageButton) rootView.findViewById(R.id.rcCandidate_imageButton);
			logoImageButtonDate = (ImageButton) rootView.findViewById(R.id.rcCandidate_dateButton);
			documentButton = (Button) rootView.findViewById(R.id.rc_Candidate_pdf);
			logoImageView = (ImageView) rootView.findViewById(R.id.rcCandidate_imageView);
			dateSelector = new DateTimeSelector();
			dateSelector.init(Calendar.getInstance());
			dateSelector.setListener(new DateTimeSelectorListener() {

				@Override
				public void updateDate() {
					DateEdit.setText(dateSelector.getDateString());
				}
			});

			logoImageButtonDate.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dateSelector.openDateDialog(getActivity());
				}
			});
			logoImageButton = (ImageButton) rootView.findViewById(R.id.rcCandidate_imageButton);
			logoImageView = (ImageView) rootView.findViewById(R.id.rcCandidate_imageView);
			sendButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if ("".equals(NameEdit.getText().toString()) || "".equals(EmailEdit.getText().toString())
							|| "".equals(DateEdit.getText().toString()) || "".equals(SummaryeEdit.getText().toString()))
						Toast.makeText(v.getContext(), getString(R.string.rc_candidate_required), Toast.LENGTH_SHORT).show();
					else {
						checkDate();
						if (isSendDate) {
							myProgressDialog = ProgressDialog.show(getActivity(), getString(R.string.connection),
									getString(R.string.rc_candidate_creating), true);

							rcCandidate = new ParseObject("Responds");

							ParseQuery<ParseObject> query = ParseQuery.getQuery("Requests");
							query.getInBackground(vacancyId, new GetCallback<ParseObject>() {
								public void done(ParseObject object, ParseException e) {
									if (e == null) {
										vacancyObj = object;
										rcCandidate.put("birthDate", bDate.toString());
										rcCandidate.put("name", NameEdit.getText().toString());
										rcCandidate.put("email", EmailEdit.getText().toString());
										rcCandidate.put("experience", ExperienceEdit.getText().toString());
										rcCandidate.put("lastJob", LastJobEdit.getText().toString());
										rcCandidate.put("lastPosition", PostEdit.getText().toString());
										rcCandidate.put("user", ParseUser.getCurrentUser());
										rcCandidate.put("request", vacancyObj);
										rcCandidate.put("type", status);
										if (getImage() != null) {
											ParseFile file = new ParseFile("photo" + photoExtension, getImage());
											rcCandidate.put("photo", file);
										}
										if (getSummaryFile() != null) {
											ParseFile file = new ParseFile("resume" + resumExtension, getSummaryFile());
											rcCandidate.put("proof", file);
										}
										rcCandidate.put("comment", CommentEdit.getText().toString());
										rcCandidate.saveInBackground(new SaveCallback() {

											@Override
											public void done(ParseException e) {
												if (e == null) {
													myProgressDialog.dismiss();
													Toast.makeText(getActivity(), R.string.rc_candidate_success, Toast.LENGTH_LONG).show();
													getActivity().finish();
												} else {
													Toast.makeText(getActivity(),
															getString(R.string.rc_candidate_creation_error) + " " + e.getMessage(),
															Toast.LENGTH_LONG).show();
												}
											}
										});
									} else {
										vacancyObj = null;
									}
								}
							});
						}
					}
				}
			});

			logoImageButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), PICK_IMAGE);
				}
			});

			documentButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent intent = new Intent();
					intent.setType("document/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(Intent.createChooser(intent, getString(R.string.select_cv)), PICK_DOCUMENT);
				}
			});
		}

		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (requestCode == PICK_IMAGE) {
				if (data != null && data.getData() != null) {
					setImage(read(new File(getFilePath(data))));
					photoExtension = extension;
					if (getImage() != null) {
						byte[] imgdata = getImage();
						BitmapFactory.Options options = new BitmapFactory.Options();
						options.inSampleSize = 1;
						Bitmap bitmap = BitmapFactory.decodeByteArray(imgdata, 0, imgdata.length, options);
						logoImageView.setImageBitmap(bitmap);
					} else
						logoImageView.setVisibility(View.GONE);
				}
			} else {
				if (requestCode == PICK_DOCUMENT && data != null && data.getData() != null) {
					setSummaryFile(read(new File(getFilePath(data))));
					resumExtension = extension;
					summaryFileEdit.setText("Summary file selected");
				}
			}
		}

		private String getFilePath(Intent data) {
			Uri uri = data.getData();
			Context context = getActivity().getApplicationContext();
			Cursor cursor = context.getContentResolver().query(uri, new String[] { android.provider.MediaStore.Images.ImageColumns.DATA },
					null, null, null);
			cursor.moveToFirst();
			String path = cursor.getString(0);
			extension = path.substring(path.lastIndexOf("."));
			return path;
		}

		public byte[] read(File file) {
			ByteArrayOutputStream bos = null;
			try {
				@SuppressWarnings("resource")
				FileInputStream fis = new FileInputStream(file);
				bos = new ByteArrayOutputStream();
				byte[] buf = new byte[(int) file.length()];
				for (int readNum; (readNum = fis.read(buf)) != -1;) {
					bos.write(buf, 0, readNum);
				}
			} catch (IOException ex) {
				Log.d("message", "Error: " + ex.getMessage());
			}
			return bos.toByteArray();
		}

		public byte[] getImage() {
			return image;
		}

		public void setImage(byte[] image) {
			this.image = image;
		}

		public byte[] getSummaryFile() {
			return summaryFile;
		}

		public void setSummaryFile(byte[] summaryFile) {
			this.summaryFile = summaryFile;
		}

	}
}
