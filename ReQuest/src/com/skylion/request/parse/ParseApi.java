package com.skylion.request.parse;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
	private static boolean initViews = false; 
	
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
				if (e == null) {					
					List<Vacancy> result = new ArrayList<Vacancy>();
					final List<Vacancy> tresult = new ArrayList<Vacancy>();					
					for (ParseObject obj : vacancyList) {
						if (obj != null) {							
							if(fragment_type == RequestConstants.FRAGMENT_MY_VACANCY)
							{
								if(vacancyList.indexOf(obj) == (vacancyList.size() - 1))
									initViews = true;
								final Vacancy tvacancy = new Vacancy();
								tvacancy.toObject(obj);								
								
								ParseQuery<ParseObject> query = ParseQuery.getQuery("Responds");								
								query.whereEqualTo("request", obj);																
								query.countInBackground(new CountCallback() {
								  public void done(int count, ParseException e) {
								    if (e == null) {								    	
								    	myProgressDialog.dismiss();							            
							            tvacancy.setRespondsCount(count);
							            tvacancy.setFragmentType(fragment_type);
							            tresult.add(tvacancy);							            
							            if(initViews)
							            	init(listView, tresult);
								    }
								  }
								  
								});
							}
							else			
							{		
								myProgressDialog.dismiss();
								Vacancy vacancy = new Vacancy();
								vacancy.toObject(obj);
								vacancy.setFragmentType(fragment_type);
								result.add(vacancy);
							}
						}
					}
					if(fragment_type != RequestConstants.FRAGMENT_MY_VACANCY)					
						init(listView, result);	
				} else {
					DialogsViewer.showErrorDialog(context, context.getString(R.string.error_loading_requests));
					Log.d("requests", "Error: " + e.getMessage());
				}
			}
		});
	}

	static void init(ListView listView, List<Vacancy> result) {
		VacancyListAdapter vacancyListAdapter = new VacancyListAdapter(context, result);					
		listView.setAdapter(vacancyListAdapter);
	}
	
	public static List<Respond> getAllResponds(final ProgressDialog progressDialog) {		
		final List<Respond> res = new ArrayList<Respond>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Responds");

		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> list, ParseException e) {
				if (e == null) {
					for (ParseObject trespond : list) {												
						Respond respond = new Respond();
						respond.toObject(trespond);
						res.add(respond);
					}
				} else {
					Log.d("requests", "Error: " + e.getMessage());
				}
				progressDialog.dismiss();
			}
		});

		return res;
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

}
