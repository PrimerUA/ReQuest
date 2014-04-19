package com.skylion.request.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.parse.ParseUser;
import com.skylion.request.R;

public class MyProfileFragment extends Fragment implements View.OnClickListener {
	private static final String ARG_SECTION_NUMBER = "section_number";
	
	private DisplayImageOptions options;
	private ImageView avatarView;
	
	private TextView emailText;
	private TextView walletText;

	private EditText withdrawEdit;

	private Button buyFiveButton;
	private Button buyTenButton;
	private Button buyFiveteenButton;
	private Button buyTwentyButton;

	private Button withdrawButton;

	private View view;

	public static MyProfileFragment newInstance(int sectionNumber) {
		MyProfileFragment fragment = new MyProfileFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public MyProfileFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_profile, container, false);

		initScreen();

		return view;
	}

	private void initScreen() {
		avatarView = (ImageView) view.findViewById(R.id.profile_avatarView);
		
		emailText = (TextView) view.findViewById(R.id.profile_emailText);
		walletText = (TextView) view.findViewById(R.id.profile_walletText);

		withdrawEdit = (EditText) view.findViewById(R.id.profile_withdrawEdit);

		buyFiveButton = (Button) view.findViewById(R.id.profile_buyFive);
		buyTenButton = (Button) view.findViewById(R.id.profile_buyTen);
		buyFiveteenButton = (Button) view.findViewById(R.id.profile_buyFiveteen);
		buyTwentyButton = (Button) view.findViewById(R.id.profile_buyTwenty);

		withdrawButton = (Button) view.findViewById(R.id.profile_withdrawButton);

		avatarView.setOnClickListener(this);
		
		buyFiveButton.setOnClickListener(this);
		buyTenButton.setOnClickListener(this);
		buyFiveteenButton.setOnClickListener(this);
		buyTwentyButton.setOnClickListener(this);

		withdrawButton.setOnClickListener(this);
		
		ParseUser user = ParseUser.getCurrentUser();
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_launcher).showImageForEmptyUri(R.drawable.ic_launcher)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).resetViewBeforeLoading(true).cacheInMemory(true).cacheOnDisc(true)
                .displayer(new RoundedBitmapDisplayer(Integer.MAX_VALUE)).build();
		ImageLoader.getInstance().displayImage(user.getString("avatar"), avatarView, options);
		
		emailText.setText(user.getEmail());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.profile_avatarView:
			Toast.makeText(v.getContext(), "Change avatar!", Toast.LENGTH_SHORT).show();
			break;
		case R.id.profile_buyFive:
			Toast.makeText(v.getContext(), "Buy five!", Toast.LENGTH_SHORT).show();
			break;
		case R.id.profile_buyTen:
			Toast.makeText(v.getContext(), "But ten!", Toast.LENGTH_SHORT).show();
			break;
		case R.id.profile_buyFiveteen:
			Toast.makeText(v.getContext(), "Buy fiveteen!", Toast.LENGTH_SHORT).show();
			break;
		case R.id.profile_buyTwenty:
			Toast.makeText(v.getContext(), "But twenty!", Toast.LENGTH_SHORT).show();
			break;
		case R.id.profile_withdrawButton:
			Toast.makeText(v.getContext(), "Withdraw money!", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
	
}
