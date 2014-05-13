package com.skylion.request.parse;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.skylion.request.R;
import com.skylion.request.entity.RequestConstants;
import com.skylion.request.entity.Respond;
import com.skylion.request.entity.Vacancy;
import com.skylion.request.utils.DialogsViewer;
import com.skylion.request.utils.adapters.RespondsListAdapter;
import com.skylion.request.utils.adapters.VacancyListAdapter;

public class ParseApi {

	private static Context context;
	private static int fragment_type;	
	
	public ParseApi() {
	}

	public static void init(Context context) {
		ParseApi.context = context;
	}
	
	public static void loadRespondsList(ListView listView, List<ParseObject> responds, Activity activity) {		
		List<Respond>list = getResponds(responds);		
		RespondsListAdapter respondsListAdapter = new RespondsListAdapter(activity, list);					
		listView.setAdapter(respondsListAdapter);
	}
	
	private static List<Respond> getResponds(List<ParseObject> responds) {
		List<Respond> respondsList = new ArrayList<Respond>();
		for(ParseObject obj : responds)
		{
			Respond respond = new Respond();
			respond.toObject(obj);
			respondsList.add(respond);
		}
		return respondsList;	
	}
	
	static private void fragment_general_vacancy(final List<ParseObject> vacancyList, final ListView listView, final ProgressDialog myProgressDialog) { 
		List<Vacancy> result = new ArrayList<Vacancy>();						
		for (ParseObject obj : vacancyList) {
			if (obj != null) {
				Vacancy vacancy = new Vacancy();
				vacancy.toObject(obj);
				vacancy.setFragmentType(fragment_type);
				result.add(vacancy);
			}
		}
		init(listView, result, myProgressDialog);
	}
	
	static private void fragment_my_vacancy(final List<ParseObject> vacancyList, final ListView listView, final ProgressDialog myProgressDialog) { 		
		final List<Vacancy> result = new ArrayList<Vacancy>();				
		final int last_element = vacancyList.size() - 1;
		for (final ParseObject obj : vacancyList) {
			if (obj != null) {																									
				final Vacancy tvacancy = new Vacancy();
				tvacancy.toObject(obj);												
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Responds");								
				query.whereEqualTo("request", obj);																
				query.countInBackground(new CountCallback() {
				  public void done(int count, ParseException e) {
				    if (e == null) {								    	
			            tvacancy.setRespondsCount(count);
			            tvacancy.setFragmentType(fragment_type);
			            result.add(tvacancy);			
			            if(vacancyList.indexOf(obj) == last_element)
			            	init(listView, result, myProgressDialog);
			            }
				    }								    
				  });				  					
			}				
		}		
	}
	
	public static void loadVacancyList(int fragmentType, final ListView listView) {
		fragment_type = fragmentType;
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Requests");
		switch (fragmentType) {
		case RequestConstants.FRAGMENT_GENERAL_VACANCY:
			query.whereEqualTo("type", RequestConstants.REQUEST_GENERAL);
			break;
		// case RequestConstants.FRAGMENT_HOT_VACANCY:
		// query.whereEqualTo("type", RequestConstants.REQUEST_HOT);
		// break;
		case RequestConstants.FRAGMENT_MY_VACANCY:
			query.whereEqualTo("user", ParseUser.getCurrentUser());
			break;
		}

		query.include("user");
		final ProgressDialog myProgressDialog = ProgressDialog.show(context, context.getString(R.string.connection),
				context.getString(R.string.connection_requests), true);
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> vacancyList, ParseException e) {							
				if (e == null && !vacancyList.isEmpty()) {	
					switch (fragment_type) {
					case RequestConstants.FRAGMENT_MY_VACANCY:
						fragment_my_vacancy(vacancyList, listView, myProgressDialog);
						break;
					case RequestConstants.FRAGMENT_GENERAL_VACANCY:
						fragment_general_vacancy(vacancyList, listView, myProgressDialog);
						break;
					default:
						break;
					}
				} 
				else {
					if(vacancyList.isEmpty()) {
						Toast.makeText(context, context.getString(R.string.requests_not_found), Toast.LENGTH_SHORT).show();
					}
					else {
						DialogsViewer.showErrorDialog(context, context.getString(R.string.error_loading_requests));
						Log.d("requests", "Error: " + e.getMessage());
					}
					myProgressDialog.dismiss();
				}		
			}			
		});
	}

	static void init(ListView listView, List<Vacancy> result, final ProgressDialog myProgressDialog) {
		VacancyListAdapter vacancyListAdapter = new VacancyListAdapter(context, result);					
		listView.setAdapter(vacancyListAdapter);
		myProgressDialog.dismiss();
	}
	
	public static void getAllResponds(final ProgressDialog progressDialog, final ListView contentList, final Context contentListContext) {		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Responds");
		query.whereEqualTo("user", ParseUser.getCurrentUser());
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> list, ParseException e) {				
				if (e == null && !list.isEmpty()) {										
					getVacancyList(list, contentList, contentListContext);					
				} 
				else {
					Toast.makeText(context, context.getString(R.string.responses_not_found), Toast.LENGTH_SHORT).show();
				}
				progressDialog.dismiss();
			}			
		});			
	}

	public static void getWallet(final TextView walletText) {
		ParseObject wallet = (ParseObject) ParseUser.getCurrentUser().get("wallet");
		wallet.fetchInBackground(new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject wallet, ParseException arg1) {
				walletText.setText("$" + String.valueOf(wallet.getDouble("total")));
			}
		});
	}
	
	private static void getVacancyList(List<ParseObject>respondList, ListView contentList, Context contentListContext) {					    
		List<Vacancy> result = new ArrayList<Vacancy>();		
		for (ParseObject trespond : respondList) {
			Respond respond = new Respond();
			respond.toObject(trespond);					
			Vacancy vacancy = new Vacancy();
			vacancy.toObject(respond.getRequest());
			vacancy.setFragmentType(RequestConstants.SHOW_MY_RESPONDS);
			if(!result.contains(vacancy))
				result.add(vacancy);							
		}			
		contentList.setAdapter(new VacancyListAdapter(contentListContext, result));
	}

}
