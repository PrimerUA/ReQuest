package com.skylion.request.views;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.skylion.request.R;

public class NewRecommendActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_recommend);
		getSupportActionBar().setTitle(R.string.title_activity_new_recommend);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private EditText NameEdit;
		private EditText DateEdit;
		private EditText SummaryeEdit;
		private EditText EmailEdit;
		private EditText ExperienceEdit;
		private EditText LastJobEdit;
		private EditText PostEdit;
		private EditText CommentEdit;
		private Button sendButton;
		private ImageButton logoImageButton;
		private ImageView logoImageView;
		private Date bDate;
		private byte[] image = null;
		private boolean isSendDate;

		// private static Context mContext;

		private View rootView = null;

		private ProgressDialog myProgressDialog;
		private int PICK_IMAGE = 1;

		public PlaceholderFragment() {
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
			sendButton = (Button) rootView.findViewById(R.id.button_rcCandidate_send);

			logoImageButton = (ImageButton) rootView.findViewById(R.id.rcCandidate_imageButton);
			logoImageView = (ImageView) rootView.findViewById(R.id.rcCandidate_imageView);

			sendButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if ("".equals(NameEdit.getText().toString()) || "".equals(EmailEdit.getText().toString())
							|| "".equals(DateEdit.getText().toString()) || "".equals(SummaryeEdit.getText().toString()))
						Toast.makeText(v.getContext(), getString(R.string.rc_candidate_required), Toast.LENGTH_SHORT).show();
					else {
						checkDate();
						if (isSendDate) {
							myProgressDialog = ProgressDialog.show(getActivity(), getString(R.string.connection),
									getString(R.string.rc_candidate_creating), true);
							ParseObject rcCandidate = new ParseObject("Responds");
							rcCandidate.put("birthDate", bDate.toString());
							rcCandidate.put("name", NameEdit.getText().toString());
							rcCandidate.put("email", EmailEdit.getText().toString());
							rcCandidate.put("experience", ExperienceEdit.getText().toString());
							rcCandidate.put("lastJob", LastJobEdit.getText().toString());
							rcCandidate.put("lastPosition", PostEdit.getText().toString());
							rcCandidate.put("user", ParseUser.getCurrentUser());
							rcCandidate.put("type", 1);
							if (getImage() != null) {
								ParseFile file = new ParseFile("photo.png", getImage());
								rcCandidate.put("photo", file);
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
												getString(R.string.rc_candidate_creation_error) + " " + e.getMessage(), Toast.LENGTH_LONG)
												.show();
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
					startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), NewRequestHolder.PICK_IMAGE);
				}
			});

		}

		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (requestCode == PICK_IMAGE && data != null && data.getData() != null) {
				Uri uri = data.getData();
				Context context = getActivity().getApplicationContext();
				Cursor cursor = context.getContentResolver().query(uri,
						new String[] { android.provider.MediaStore.Images.ImageColumns.DATA }, null, null, null);
				cursor.moveToFirst();
				setImage(read(new File(cursor.getString(0))));

				if (getImage() != null) {
					byte[] imgdata = getImage();
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inSampleSize = 1;
					Bitmap bitmap = BitmapFactory.decodeByteArray(imgdata, 0, imgdata.length, options);
					logoImageView.setImageBitmap(bitmap);
				} else
					logoImageView.setVisibility(View.GONE);
			}
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
	}

}
