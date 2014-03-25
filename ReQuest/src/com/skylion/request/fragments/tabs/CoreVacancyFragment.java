package com.skylion.request.fragments.tabs;

import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.skylion.request.R;
import com.skylion.request.utils.ExpandableViewHelper;
import com.skylion.request.utils.RequestConstants;
import com.skylion.request.utils.VacancyListAdapter;

abstract class CoreVacancyFragment extends Fragment implements ListView.OnItemClickListener {

	private int fragment_type;
	private ListView contentList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_vacancy, container, false);
		contentList = (ListView) rootView.findViewById(R.id.vacancyFragment_contentList);
		contentList.setOnItemClickListener(this);

		loadData();

		return rootView;
	}

	private void loadData() {
		final ProgressDialog myProgressDialog = ProgressDialog.show(getActivity(), getString(R.string.connection),
				getString(R.string.connection_requests), true);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Requests");
		switch (fragment_type) {
		case RequestConstants.FRAGMENT_GENERAL_VACANCY:
			query.whereEqualTo("type", RequestConstants.REQUEST_GENERAL);
			break;
		case RequestConstants.FRAGMENT_HOT_VACANCY:
			query.whereEqualTo("type", RequestConstants.REQUEST_HOT);
			break;
		case RequestConstants.FRAGMENT_MY_VACANCY:
			query.whereEqualTo("user", ParseUser.getCurrentUser());
			break;
		}
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> requestsList, ParseException e) {
				if (e == null) {
					contentList.setAdapter(new VacancyListAdapter(getActivity(), requestsList));
				} else {
					Log.d("requests", "Error: " + e.getMessage());
				}
				myProgressDialog.dismiss();
			}
		});
	}

	protected void onCreateVacancyFragment(int fragment_type) {
		this.fragment_type = fragment_type;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		LinearLayout expandableLayout = (LinearLayout) view.findViewById(R.id.vacancyItem_expandableLayout);
		if (expandableLayout.isShown()) {
			ExpandableViewHelper.slideIntoDirection(view.getContext(), expandableLayout,R.anim.item_slide_up);
			expandableLayout.setVisibility(View.GONE);
		} else {
			expandableLayout.setVisibility(View.VISIBLE);
			ExpandableViewHelper.slideIntoDirection(view.getContext(), expandableLayout,R.anim.item_slide_down);
		}
	}
}
