package com.skylion.request.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.skylion.request.R;
import com.skylion.request.billing.IabHelper;
import com.skylion.request.billing.IabHelper.OnIabPurchaseFinishedListener;
import com.skylion.request.billing.IabHelper.QueryInventoryFinishedListener;
import com.skylion.request.billing.IabResult;
import com.skylion.request.billing.Inventory;
import com.skylion.request.billing.Purchase;
import com.skylion.request.parse.ParseApi;

public class MyProfileFragment extends Fragment implements View.OnClickListener, QueryInventoryFinishedListener,
		OnIabPurchaseFinishedListener {
	private static final String ARG_SECTION_NUMBER = "section_number";
	private static final String SKU_1 = "fund_5";
	private static final String SKU_2 = "fund_10";
	private static final String SKU_3 = "fund_15";
	private static final String SKU_4 = "fund_20";

	private static int BUY_1 = 5;
	private static int BUY_2 = 10;
	private static int BUY_3 = 15;
	private static int BUY_4 = 20;

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

//	private IabHelper mHelper;

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

//		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
//			public void onIabSetupFinished(IabResult result) {
//				if (!result.isSuccess()) {
//					Toast.makeText(getActivity(), "Error loading billing: " + result, Toast.LENGTH_SHORT).show();
//					Log.d("billing", "Problem setting up In-app Billing: " + result);
//				} else {
//					Toast.makeText(getActivity(), "All set!", Toast.LENGTH_SHORT).show();
//					initScreen();
//					Log.d("billing", "Success!");
//				}
//			}
//		});
		
		initScreen();
		
		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
//		if (mHelper != null)
//			mHelper.dispose();
//		mHelper = null;
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
		withdrawButton.setEnabled(false);

		ParseUser user = ParseUser.getCurrentUser();
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_launcher).showImageForEmptyUri(R.drawable.ic_launcher)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED).resetViewBeforeLoading(true).cacheInMemory(true).cacheOnDisc(true)
				.displayer(new RoundedBitmapDisplayer(Integer.MAX_VALUE)).build();
		ImageLoader.getInstance().displayImage(user.getString("avatar"), avatarView, options);

		emailText.setText(user.getEmail());
		ParseApi.getWallet(walletText);
//		mHelper.queryInventoryAsync(new QueryInventoryFinishedListener() {
//
//			@Override
//			public void onQueryInventoryFinished(IabResult result, Inventory inv) {
//				if (result.isFailure()) {
//					Toast.makeText(getActivity(), "Error loading billing: " + result, Toast.LENGTH_SHORT).show();
//				} else if (inv.hasPurchase(SKU_1)) {
//					buyFiveButton.setEnabled(false);
//				} else if (inv.hasPurchase(SKU_2)) {
//					buyTenButton.setEnabled(false);
//				} else if (inv.hasPurchase(SKU_3)) {
//					buyFiveteenButton.setEnabled(false);
//				} else if (inv.hasPurchase(SKU_4)) {
//					buyTwentyButton.setEnabled(false);
//				}
//			}
//		});
	}

	@Override
	public void onClick(View v) {
		List additionalSkuList = new ArrayList();
		additionalSkuList.add(SKU_1);
		// additionalSkuList.add(SKU_2);
		// additionalSkuList.add(SKU_3);
		// additionalSkuList.add(SKU_4);
		//mHelper.queryInventoryAsync(true, additionalSkuList, this);
		switch (v.getId()) {
		case R.id.profile_avatarView:
			Toast.makeText(v.getContext(), "Change avatar!", Toast.LENGTH_SHORT).show();
			break;
		case R.id.profile_buyFive:
//			mHelper.launchPurchaseFlow(getActivity(), SKU_1, BUY_1, this, "1");
			Toast.makeText(v.getContext(), "Comming soon!", Toast.LENGTH_SHORT).show();
			break;
		case R.id.profile_buyTen:
//			mHelper.launchPurchaseFlow(getActivity(), SKU_2, BUY_2, this, "2");
			Toast.makeText(v.getContext(), "Comming soon!", Toast.LENGTH_SHORT).show();
			break;
		case R.id.profile_buyFiveteen:
//			mHelper.launchPurchaseFlow(getActivity(), SKU_3, BUY_3, this, "3");
			Toast.makeText(v.getContext(), "Comming soon!", Toast.LENGTH_SHORT).show();
			break;
		case R.id.profile_buyTwenty:
//			mHelper.launchPurchaseFlow(getActivity(), SKU_4, BUY_4, this, "4");
			Toast.makeText(v.getContext(), "Comming soon!", Toast.LENGTH_SHORT).show();
			break;
		case R.id.profile_withdrawButton:
			Toast.makeText(v.getContext(), "Comming soon!", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	@Override
	public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
		if (result.isFailure()) {
			Toast.makeText(getActivity(), "Error loading billing: " + result, Toast.LENGTH_SHORT).show();
			return;
		}

		buyFiveButton.setText(inventory.getSkuDetails(SKU_1).getPrice());
		// buyTenButton.setText(inventory.getSkuDetails(SKU_2).getPrice());
		// buyFiveteenButton.setText(inventory.getSkuDetails(SKU_3).getPrice());
		// buyTwentyButton.setText(inventory.getSkuDetails(SKU_4).getPrice());
	}

	@Override
	public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
		double ammount = 0;
		if (result.isFailure()) {
			Toast.makeText(getActivity(), "Error purchasing item!", Toast.LENGTH_SHORT).show();
			Log.d("billing", "Error purchasing: " + result);
			return;
		} else if (purchase.getSku().equals(SKU_1)) {
			ammount = BUY_1;
			double current = Integer.valueOf(walletText.getText().toString());
			double res = current + ammount;
			walletText.setText("$" + String.valueOf(res));
		} else if (purchase.getSku().equals(SKU_2)) {
			ammount = BUY_2;
			double current = Integer.valueOf(walletText.getText().toString());
			double res = current + ammount;
			walletText.setText("$" + String.valueOf(res));
		} else if (purchase.getSku().equals(SKU_3)) {
			ammount = BUY_3;
			double current = Integer.valueOf(walletText.getText().toString());
			double res = current + ammount;
			walletText.setText("$" + String.valueOf(res));
		} else if (purchase.getSku().equals(SKU_4)) {
			ammount = BUY_4;
			double current = Integer.valueOf(walletText.getText().toString());
			double res = current + ammount;
			walletText.setText("$" + String.valueOf(res));
		}
		ParseObject wallet = (ParseObject) ParseUser.getCurrentUser().get("wallet");
		wallet.increment("total", ammount);		
		wallet.saveInBackground();
	}

}
