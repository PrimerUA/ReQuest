package com.skylion.request.views;

import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;
import com.skylion.request.R;
import com.skylion.request.R.id;
import com.skylion.request.R.layout;
import com.skylion.request.R.menu;
import com.skylion.request.R.string;
import com.skylion.request.parse.ParseApi;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class RespondsShowActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_responds_show);

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
			    	Toast.makeText(getActivity(), "Not found", Toast.LENGTH_SHORT).show();
			    }
			  }
			});
		}
		
		private void getRequests(ParseObject object) {
			
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Responds");
			query.whereEqualTo("request", object);
			query.findInBackground(new FindCallback<ParseObject>() {					
			    public void done(List<ParseObject> responds, ParseException e) {
			        if (e == null) 
			        {		        			        				
			            showRequests(responds);
			        }
			        else
			        {
			        	myProgressDialog.dismiss();
			        	Toast.makeText(getActivity(), "Not found", Toast.LENGTH_SHORT).show();			        	
			        }
				}
			});
		}
		
		private void showRequests(List<ParseObject> responds) {
			myProgressDialog.dismiss();
			ParseApi.loadRespondsList(contentList, responds, getActivity());            
		}	
	}

}
