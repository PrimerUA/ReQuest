package com.skylion.request.utils.adapters;

import java.util.Date;
import java.util.List;

import com.google.android.gms.internal.ew;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.skylion.request.R;
import com.skylion.request.entity.Respond;
import com.skylion.request.entity.Vacancy;
import com.skylion.request.utils.FileDialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
		private ImageView avatar;
	}		
	
	private View view;
	private ViewGroup container;
	private LayoutInflater inflater;
	private Context context = null;
	private List<Respond> respondList;
	private String m_chosen;
	
	public RespondsListAdapter(Context context, List<Respond> result) {
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		respondList = result;
		this.context = context;		
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		container = parent;
		view = convertView;
		if (view == null)
			view = inflater.inflate(R.layout.respond_list_item, null);
		
		ViewHolder holder = new ViewHolder();		
		
		holder.name = (TextView)view.findViewById(R.id.respondsItem_name_textView);
		holder.createdAt = (TextView)view.findViewById(R.id.respondsItem_createdAt_textView);
		holder.updatedAt = (TextView)view.findViewById(R.id.respond_item_updatedAt_textView);
		holder.photo = (ImageView)view.findViewById(R.id.respondsItem_imageView);
		holder.email = (TextView)view.findViewById(R.id.respondsItem_email_editText);		
		holder.bdate = (TextView)view.findViewById(R.id.respondsItem_bdateText);
		holder.lastJob = (TextView)view.findViewById(R.id.respondsItem_lastJob_Text);
		holder.lastPost = (TextView)view.findViewById(R.id.respondsItem_lastPost_Text);
		holder.experience = (TextView)view.findViewById(R.id.respondsItem_experience_editText);
		holder.comment = (TextView)view.findViewById(R.id.respodsItem_comment_editText);		
		holder.user = (TextView)view.findViewById(R.id.respondsItem_authorText);
		holder.avatar = (ImageView)view.findViewById(R.id.respondsItem_avatarText);
		
		Respond respond = respondList.get(position);
		
		DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher).imageScaleType(ImageScaleType.EXACTLY_STRETCHED).resetViewBeforeLoading(true)
				.cacheInMemory(true).cacheOnDisc(true).displayer(new RoundedBitmapDisplayer(Integer.MAX_VALUE)).build();
		ImageLoader.getInstance().displayImage(respond.getUser().getString("avatar"), holder.avatar, options);
		
		Button downloadCVButton = (Button)view.findViewById(R.id.respondsItem_getCV_button);
		downloadCVButton.setOnClickListener(this);
		downloadCVButton.setTag(position);
		
		
		
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
		return 	respondList.size();
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
//		Toast.makeText(context, pobject.getString("name"), Toast.LENGTH_SHORT).show();
		final ProgressDialog myProgressDialog = ProgressDialog.show(context, context.getString(R.string.connection),
				context.getString(R.string.connection_cv_file_fetch), true);
		final ParseFile applicantResume = (ParseFile)pobject.get("proof");		
		if(applicantResume == null)
			Toast.makeText(context, "CV not found!", Toast.LENGTH_SHORT).show();
		else
		{
			applicantResume.getDataInBackground(new GetDataCallback() {
			  public void done(byte[] data, ParseException e) {
			    if (e == null) {
			    	myProgressDialog.dismiss();
			    	saveCVFile(data, applicantResume.getName());
			    } else {
			    	myProgressDialog.dismiss();
			    	Toast.makeText(context, "Error get CV!", Toast.LENGTH_SHORT).show();
			    }
			  }
			});
		}
	}

	private void saveCVFile (byte[] data, String fileName) {				
		Toast.makeText(context, fileName + ";size : " + ((Integer)data.length).toString() , Toast.LENGTH_SHORT).show();		
//		FileDialog FileSaveDialog =  new FileDialog(context, "FileSave", new FileDialog.FileDialogLitener()
//		{
//			@Override
//			public void onChosenDir(String chosenDir) 
//			{
//				// The code in this function will be executed when the dialog OK button is pushed
//				m_chosen = chosenDir;
//				Toast.makeText(context, "Chosen FileOpenDialog File: " + m_chosen, Toast.LENGTH_LONG).show();
//			}
//		});
//		
//		//You can change the default filename using the public variable "Default_File_Name"
//		FileSaveDialog.Default_File_Name = fileName;
//		FileSaveDialog.chooseFile_or_Dir();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.respondsItem_getCV_button:
		{				
			int position = Integer.parseInt(v.getTag().toString());
			loadCVFile(position);
			break;
		}
		default:
			break;
		}
		
	}
}
