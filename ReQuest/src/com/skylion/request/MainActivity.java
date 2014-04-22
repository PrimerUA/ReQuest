package com.skylion.request;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.parse.ParseAnalytics;
import com.parse.ParseUser;
import com.skylion.request.fragments.MyProfileFragment;
import com.skylion.request.fragments.NavigationDrawerFragment;
import com.skylion.request.fragments.RespondsFragment;
import com.skylion.request.fragments.VacancyFragment;
import com.skylion.request.views.NewRequestHolder;
import com.skylion.request.views.UserLoginActivity;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ParseAnalytics.trackAppOpened(getIntent());

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));		
		
		if (ParseUser.getCurrentUser() == null) {
			startActivity(new Intent(this, UserLoginActivity.class));
		}
	}
	
	@Override
	public void onNavigationDrawerItemSelected(int position) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		Fragment fragment = null;
		switch (position) {
		case 0:
			getSupportActionBar().setTitle(R.string.title_section_requests);
			fragment = VacancyFragment.newInstance(position + 1);
			break;
		case 1:
			getSupportActionBar().setTitle(R.string.title_section_responds);
			fragment = RespondsFragment.newInstance(position + 1);
			break;
		case 2:
			getSupportActionBar().setTitle(ParseUser.getCurrentUser().getUsername());
			fragment = MyProfileFragment.newInstance(position + 1);
			break;
		}
		fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section_requests);
			break;
		case 2:
			mTitle = getString(R.string.title_section_responds);
			break;
		case 3:
			mTitle = getString(R.string.title_section_profile);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_request:
			startActivity(new Intent(this, NewRequestHolder.class));
		}
		return super.onOptionsItemSelected(item);
	}

}
