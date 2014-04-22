package com.skylion.request.parse;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.skylion.request.R;
import com.skylion.request.entity.RequestConstants;
import com.skylion.request.entity.Respond;
import com.skylion.request.entity.Vacancy;
import com.skylion.request.utils.DialogsViewer;
import com.skylion.request.utils.adapters.VacancyListAdapter;

public class ParseApi {

	private ListView listView;

	private static ParseApi instance;

	public static ParseApi getInstance() {
		if (instance == null) {
			instance = new ParseApi();
		}
		return instance;
	}

	public ParseApi() {
	}

	public ListView getListView() {
		return listView;
	}

	public ParseApi setListView(ListView listView) {
		this.listView = listView;
		return this;
	}
	
	public void loadVacancyList(int fragmentType, final Context context) {
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
			public void done(List<ParseObject> vacansyList, ParseException e) {
				myProgressDialog.dismiss();
				if (e == null) {
					List<Vacancy> result = new ArrayList<Vacancy>();
					for (ParseObject obj : vacansyList) {
						if (obj != null) {
							Vacancy vacancy = new Vacancy();
							vacancy.toObject(obj);
							result.add(vacancy);
						}
					}
					listView.setAdapter(new VacancyListAdapter(context, result));					
					
				} else {
					DialogsViewer.showErrorDialog(context, context.getString(R.string.error_loading_requests));
					Log.d("requests", "Error: " + e.getMessage());
				}
			}
		});
	}

	public static List<Respond> getAllResponds(final ProgressDialog progressDialog) {
		final List<Respond> res = new ArrayList<Respond>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Responds");
		// TODO: user query.whereEqualTo("user", ParseUser.getCurrentUser());

		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> list, ParseException e) {
				if (e == null) {
					for (ParseObject respond : list) {
						res.add((Respond) respond);
					}
				} else {
					Log.d("requests", "Error: " + e.getMessage());
				}
				progressDialog.dismiss();
			}
		});

		return res;
	}

}
