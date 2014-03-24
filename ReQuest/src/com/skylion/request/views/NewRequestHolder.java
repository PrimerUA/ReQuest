package com.skylion.request.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.skylion.request.R;
import com.skylion.request.fragments.FirstStepFragment;
import com.skylion.request.fragments.SecondStepFragment;

public class NewRequestHolder extends FragmentActivity {
	
	private final int STEPS = 2;
	private Fragment[] fragments = new Fragment[STEPS];

	private int currentFragment;
	
	private String title;
	private String description;
	private String company;
	private byte [] image;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.holder_new_request);

		FragmentManager fm = getSupportFragmentManager();
		FirstStepFragment startFragment = (FirstStepFragment) fm.findFragmentById(R.id.firstFragment);
		fragments[0] = startFragment;
		fragments[1] = (SecondStepFragment) fm.findFragmentById(R.id.secondFragment);

		FragmentTransaction transaction = fm.beginTransaction();
		for (int i = 0; i < fragments.length; i++) {
			transaction.hide(fragments[i]);
		}
		transaction.commit();
		showFragment(currentFragment = 0, true);

		fm.addOnBackStackChangedListener(new OnBackStackChangedListener() {

			@Override
			public void onBackStackChanged() {
				if (getSupportFragmentManager().getBackStackEntryCount() == 0)
					finish();
			}
		});
	}

	public void showFragment(int fragmentIndex, boolean addToBackStack) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		for (int i = 0; i < fragments.length; i++) {
			if (i == fragmentIndex) {
				transaction.show(fragments[i]);
			} else {
				transaction.hide(fragments[i]);
			}
		}

		if (addToBackStack) {
			transaction.addToBackStack(null);
		}
		currentFragment = fragmentIndex;
		transaction.commit();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		default: {
			backButton();
		}
		}
		return true;
	}

	private void backButton() {
		if (currentFragment != 0) {
			currentFragment--;
			showFragment(currentFragment, false);
		} else {
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		backButton();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case 0:
			setResult(0);
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public String getVacancyTitle() {
		return title;
	}

	public void setVacancyTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public byte [] getImage() {
		return image;
	}

	public void setImage(byte [] image) {
		this.image = image;
	}
}

	
	
