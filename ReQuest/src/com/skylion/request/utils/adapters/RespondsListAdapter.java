package com.skylion.request.utils.adapters;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.skylion.parse.settings.ParseConstants;
import com.skylion.parse.settings.ParseTable;
import com.skylion.request.R;
import com.skylion.request.entity.RequestConstants;
import com.skylion.request.entity.Respond;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RespondsListAdapter extends BaseAdapter implements OnClickListener {

	private static class ViewHolder {

		public TextView name;
		public TextView createdAt;
		public TextView updatedAt;
		public ImageView photo;
		public TextView email;
		public TextView bdate;
		public TextView lastJob;
		public TextView lastPost;
		public TextView experience;
		public TextView comment;
		public TextView user;
		public TextView status;
		private ImageView avatar;
	}

	private View view;

	private LayoutInflater inflater;
	private Context context = null;
	private List<Respond> respondList;
	private File path = null;

	public RespondsListAdapter(Context context, List<Respond> result) {
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		respondList = result;
		this.context = context;
	}

	protected void finalize() {
		if (path != null)
			path.delete();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		view = convertView;
		if (view == null)
			view = inflater.inflate(R.layout.respond_list_item, null);

		ViewHolder holder = new ViewHolder();

		holder.name = (TextView) view.findViewById(R.id.respondsItem_name_textView);
		holder.createdAt = (TextView) view.findViewById(R.id.respondsItem_createdAt_textView);
		holder.updatedAt = (TextView) view.findViewById(R.id.respond_item_updatedAt_textView);
		holder.photo = (ImageView) view.findViewById(R.id.respondsItem_imageView);
		holder.email = (TextView) view.findViewById(R.id.respondsItem_email_editText);
		holder.bdate = (TextView) view.findViewById(R.id.respondsItem_bdateText);
		holder.lastJob = (TextView) view.findViewById(R.id.respondsItem_lastJob_Text);
		holder.lastPost = (TextView) view.findViewById(R.id.respondsItem_lastPost_Text);
		holder.experience = (TextView) view.findViewById(R.id.respondsItem_experience_editText);
		holder.comment = (TextView) view.findViewById(R.id.respodsItem_comment_editText);
		holder.user = (TextView) view.findViewById(R.id.respondsItem_authorText);
		holder.avatar = (ImageView) view.findViewById(R.id.respondsItem_avatarText);
		holder.status = (TextView) view.findViewById(R.id.textView_status);

		Respond respond = respondList.get(position);

		DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher).imageScaleType(ImageScaleType.EXACTLY_STRETCHED).resetViewBeforeLoading(true)
				.cacheInMemory(true).cacheOnDisc(true).displayer(new RoundedBitmapDisplayer(Integer.MAX_VALUE)).build();
		ImageLoader.getInstance().displayImage(respond.getUser().getString(ParseConstants.USER_AVATAR), holder.avatar, options);

		Button downloadCVButton = (Button) view.findViewById(R.id.respondsItem_getCV_button);
		downloadCVButton.setOnClickListener(this);
		downloadCVButton.setTag(position);

		RadioGroup radioButtonGroup;
		radioButtonGroup = (RadioGroup) view.findViewById(R.id.radios);

		RadioButton rbDismiss = (RadioButton) view.findViewById(R.id.radioButton_dismiss);
		rbDismiss.setTag(position);
		rbDismiss.setOnClickListener(this);

		RadioButton rbProcessing = (RadioButton) view.findViewById(R.id.radioButton_processing);
		rbProcessing.setTag(position);
		rbProcessing.setOnClickListener(this);

		RadioButton rbOffer = (RadioButton) view.findViewById(R.id.radioButton_offer);
		rbOffer.setTag(position);
		rbOffer.setOnClickListener(this);

		RadioButton rbAccepted = (RadioButton) view.findViewById(R.id.radioButton_accepted);
		rbAccepted.setTag(position);
		rbAccepted.setOnClickListener(this);

		if (respond.getFragmentType() == RequestConstants.SHOW_MY_RESPONDS) {
			radioButtonGroup.setVisibility(View.GONE);
			switch (respond.getCandidateStatus()) {
			case RequestConstants.RESPOND_STATUS_NEW:
				holder.status.setText(context.getString(R.string.respond_new));
				break;
			case RequestConstants.RESPOND_STATUS_DISMISS:
				holder.status.setText(context.getString(R.string.respond_dismiss));
				break;
			case RequestConstants.RESPOND_STATUS_PROCESSING:
				holder.status.setText(context.getString(R.string.respond_processing));
				break;
			case RequestConstants.RESPOND_STATUS_OFFER:
				holder.status.setText(context.getString(R.string.respond_offer));
				break;
			case RequestConstants.RESPOND_STATUS_ACCEPTED:
				holder.status.setText(context.getString(R.string.respond_accepted));
				break;

			default: {
				rbDismiss.setChecked(false);
				rbProcessing.setChecked(false);
				rbOffer.setChecked(false);
				rbAccepted.setChecked(false);
			}
				break;
			}
		} else {
			View layoutStatus = view.findViewById(R.id.candidate_status_layout);
			layoutStatus.setVisibility(View.GONE);
			switch (respond.getCandidateStatus()) {
			case RequestConstants.RESPOND_STATUS_DISMISS:
				rbDismiss.setChecked(true);
				break;
			case RequestConstants.RESPOND_STATUS_PROCESSING:
				rbProcessing.setChecked(true);
				break;
			case RequestConstants.RESPOND_STATUS_OFFER:
				rbOffer.setChecked(true);
				break;
			case RequestConstants.RESPOND_STATUS_ACCEPTED:
				rbAccepted.setChecked(true);
				break;

			default: {
				rbDismiss.setChecked(false);
				rbProcessing.setChecked(false);
				rbOffer.setChecked(false);
				rbAccepted.setChecked(false);
			}
				break;
			}
		}

		holder.name.setText(respond.getName());
		holder.createdAt.setText(respond.getCreatedAt());
		holder.updatedAt.setText(respond.getUpdatedAt());
		holder.email.setText(respond.getEmail());
		holder.bdate.setText(respond.getBdate());
		holder.lastJob.setText(respond.getLastJob());
		holder.lastPost.setText(respond.getLastPost());
		holder.experience.setText(respond.getExperience());
		holder.comment.setText(respond.getComment());

		holder.user.setText(respond.getUser().getUsername());

		ParseFile candidatePhoto = (ParseFile) respond.getPhoto();
		if (candidatePhoto != null)
			loadImage(candidatePhoto, holder.photo);
		return view;
	}

	private void loadImage(ParseFile imgFile, final ImageView imgView) {

		byte[] imgdata = null;
		try {
			imgdata = imgFile.getData();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 1;
		Bitmap bitmap = BitmapFactory.decodeByteArray(imgdata, 0, imgdata.length, options);
		imgView.setImageBitmap(bitmap);

	}

	@Override
	public int getCount() {
		return respondList.size();
	}

	@Override
	public Object getItem(int position) {
		return respondList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private void loadCVFile(int index) {

		ParseObject pobject = respondList.get(index).getRespondObj();
		final ProgressDialog myProgressDialog = ProgressDialog.show(context, context.getString(R.string.connection),
				context.getString(R.string.connection_cv_file_fetch), true);
		final ParseFile applicantResume = (ParseFile) pobject.get("proof");
		if (applicantResume == null) {
			Toast.makeText(context, context.getString(R.string.error_cv_not_found), Toast.LENGTH_SHORT).show();
			myProgressDialog.dismiss();
		} else {
			applicantResume.getDataInBackground(new GetDataCallback() {
				public void done(byte[] data, ParseException e) {
					if (e == null) {
						myProgressDialog.dismiss();
						saveCVFile(data, applicantResume.getName());
					} else {
						myProgressDialog.dismiss();
						Toast.makeText(context, context.getString(R.string.error_get_cv_file), Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
	}

	private void saveCVFile(byte[] data, String fileName) {

		File directory = new File(context.getExternalCacheDir(), "cv_files");
		if (!directory.exists()) {
			directory.mkdir();
		} else {
			String[] children = directory.list();
			for (int i = 0; i < children.length; i++) {
				new File(directory, children[i]).delete();
			}
		}
		FileOutputStream outFile;
		try {
			path = new File(directory, fileName);
			outFile = new FileOutputStream(path);
			outFile.write(data);
			outFile.close();

			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setAction(Intent.ACTION_VIEW);

			String type = "application/*";

			int i = fileName.lastIndexOf('.');
			if (i > 0) {
				String extension = fileName.substring(i + 1);
				extension.toLowerCase();
				if ("txt".equals(extension))
					type = "text/plain";
				else {
					if ("pdf".equals(extension))
						type = "application/pdf";
					else if ("doc".equals(extension) || "docx".equals(extension))
						type = "application/doc";
				}
			}

			intent.setDataAndType(Uri.fromFile(path), type);
			context.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(context, context.getString(R.string.error_get_cv_file), Toast.LENGTH_SHORT).show();
		}

	}

	private void updateVacacyStatus(final int status, Respond respondObj) {
		final ProgressDialog myProgressDialog = ProgressDialog.show(context, context.getString(R.string.connection),
				context.getString(R.string.respond_set_status), true);

		ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseTable.RESPONDS_TABLE_NAME);
		query.getInBackground(respondObj.getObjectId(), new GetCallback<ParseObject>() {
			public void done(ParseObject gameScore, ParseException e) {
				if (e == null) {
					gameScore.put(ParseConstants.RESPONDS_TYPE, status);
					gameScore.saveInBackground();
					myProgressDialog.dismiss();
					switch (status) {
					case RequestConstants.RESPOND_STATUS_PROCESSING:
						Toast.makeText(context, context.getString(R.string.respond_processing), Toast.LENGTH_SHORT).show();
						break;
					case RequestConstants.RESPOND_STATUS_DISMISS:
						Toast.makeText(context, context.getString(R.string.respond_dismiss), Toast.LENGTH_SHORT).show();
						break;

					case RequestConstants.RESPOND_STATUS_OFFER:
						Toast.makeText(context, context.getString(R.string.respond_offer), Toast.LENGTH_SHORT).show();
						break;

					case RequestConstants.RESPOND_STATUS_ACCEPTED:
						Toast.makeText(context, context.getString(R.string.respond_accepted), Toast.LENGTH_SHORT).show();
						break;

					default:
						break;
					}
				}
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.respondsItem_getCV_button: {
			int position = Integer.parseInt(v.getTag().toString());
			loadCVFile(position);
		}
			break;
		case R.id.radioButton_dismiss: {
			int position = Integer.parseInt(v.getTag().toString());
			updateVacacyStatus(RequestConstants.RESPOND_STATUS_DISMISS, respondList.get(position));
		}
			break;
		case R.id.radioButton_processing: {
			int position = Integer.parseInt(v.getTag().toString());
			updateVacacyStatus(RequestConstants.RESPOND_STATUS_PROCESSING, respondList.get(position));
		}
			break;
		case R.id.radioButton_offer: {

			int position = Integer.parseInt(v.getTag().toString());
			updateVacacyStatus(RequestConstants.RESPOND_STATUS_OFFER, respondList.get(position));
		}
			break;
		case R.id.radioButton_accepted: {
			int position = Integer.parseInt(v.getTag().toString());
			updateVacacyStatus(RequestConstants.RESPOND_STATUS_ACCEPTED, respondList.get(position));
		}
			break;
		default:
			break;
		}

	}
}
