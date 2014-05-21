package com.skylion.request.views;

import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.skylion.request.R;
import com.skylion.request.parse.ParseApi;

public class RespondsShowActivity extends ActionBarActivity {

	private static int fragmentType = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_responds_show);
		
		getSupportActionBar().setTitle(R.string.title_activity_responds_show);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.responds_show, menu);
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
public static class PlaceholderFragment extends Fragment {
		
		private ProgressDialog myProgressDialog;
		private ListView contentList;
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			View rootView = inflater.inflate(R.layout.fragment_responds_show, container, false);
			contentList = (ListView) rootView.findViewById(R.id.responds_ListView);
			myProgressDialog = ProgressDialog.show(getActivity(), getActivity().getString(R.string.connection),
					getActivity().getString(R.string.connection_responds), true);						
			
			getVacancyObject();
			return rootView;
		}
		
		private void getVacancyObject(){
			fragmentType = Integer.parseInt(getActivity().getIntent().getStringExtra("fragment_type"));
			String objectId =  getActivity().getIntent().getStringExtra("request");					
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Requests");		
			query.getInBackground(objectId, new GetCallback<ParseObject>() {
			  public void done(ParseObject object, ParseException e) {
			    if (e == null) 
			    {		
			    	getRequests(object);
			    }
			    else
			    {
			    	myProgressDialog.dismiss();
			    	Toast.makeText(getActivity(), getActivity().getString(R.string.requests_not_found), Toast.LENGTH_SHORT).show();
			    }
			  }
			});
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			default: {
				getActivity().finish();
			}
			}
			return true;
		}
		
		private void getRequests(ParseObject object) {
			
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Responds");
			query.whereEqualTo("request", object);
			query.whereEqualTo("user", ParseUser.getCurrentUser());
			query.findInBackground(new FindCallback<ParseObject>() {					
			    public void done(List<ParseObject> responds, ParseException e) {
			        if (e == null && !responds.isEmpty()) 
			        {		        			        				
			            showRequests(responds);
			        }
			        else
			        {
			        	myProgressDialog.dismiss();
			        	Toast.makeText(getActivity(), getActivity().getString(R.string.responses_not_found), Toast.LENGTH_SHORT).show();			        	
			        }
				}
			});
		}
		
		private void showRequests(List<ParseObject> responds) {
			myProgressDialog.dismiss();
			ParseApi.loadRespondsList(contentList, responds, getActivity(), fragmentType);            
		}
		
	}

}
