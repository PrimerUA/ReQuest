package com.skylion.request.parse;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.skylion.request.entity.RequestConstants;
import com.skylion.request.entity.Respond;
import com.skylion.request.entity.Vacancy;

public class ParseApi {

	public static List<Vacancy> getAllVacancy(int fragmentType, final ProgressDialog progressDialog) {
		final List<Vacancy> res = new ArrayList<Vacancy>();

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Requests");

		switch (fragmentType) {
		case RequestConstants.FRAGMENT_GENERAL_VACANCY:
			query.whereEqualTo("type", RequestConstants.REQUEST_GENERAL);
			break;
//		case RequestConstants.FRAGMENT_HOT_VACANCY:
//			query.whereEqualTo("type", RequestConstants.REQUEST_HOT);
//			break;
		case RequestConstants.FRAGMENT_MY_VACANCY:
			query.whereEqualTo("user", ParseUser.getCurrentUser());
			break;
		}

		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> vacansyList, ParseException e) {
				if (e == null) {
					for (ParseObject obj : vacansyList) {
						if (obj != null) {
							Vacancy vacancy = new Vacancy();
							vacancy.toObject(obj);
							res.add(vacancy);
						}
					}
				} else {
					Log.d("requests", "Error: " + e.getMessage());
				}
				progressDialog.dismiss();
			}
		});

		return res;
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
