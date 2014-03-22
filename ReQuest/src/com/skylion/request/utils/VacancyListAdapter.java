package com.skylion.request.utils;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.skylion.request.R;

public class VacancyListAdapter extends BaseAdapter {

	private View view;

	private List<ParseObject> requestList;

	private LayoutInflater inflater = null;

	public VacancyListAdapter(Context context, List<ParseObject> requestList) {
		this.requestList = requestList;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return requestList.size();
	}

	@Override
	public Object getItem(int position) {
		return requestList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ParseObject request = requestList.get(position);
		view = convertView;
		if (view == null)
			view = inflater.inflate(R.layout.vacancy_list_item, null);

		TextView title = (TextView) view.findViewById(R.id.vacancyItem_titleText);
		TextView fund = (TextView) view.findViewById(R.id.vacancyItem_prizeText);
		TextView company = (TextView) view.findViewById(R.id.vacancyItem_companyText);
		// TextView criteriaSize = (TextView)
		// view.findViewById(R.id.CaseListItem_caseCriteriaSize);
		// TextView expireDate = (TextView)
		// view.findViewById(R.id.CaseListItem_caseExpDate);
		final TextView authorText = (TextView) view.findViewById(R.id.vacancyItem_authorText);
		// TextView rateText = (TextView)
		// view.findViewById(R.id.CaseListItem_caseRate);
		// LinearLayout mainLayout = (LinearLayout)
		// view.findViewById(R.id.CaseListItem_mainLayout);

		title.setText(request.getString("title"));
		
		ParseRelation<ParseObject> relation = request.getRelation("user");
		ParseQuery<ParseObject> query = relation.getQuery();
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> authors, ParseException arg1) {
				authorText.setText(authors.get(0).toString());
			}
		});
		 
		// now execute the query
		
		
		// expireDate.setText("[" +
		// DateFormatter.getFormatDate(caseBean.getExpireDate()) + "]");
		fund.setText("$" + request.getInt("reward"));

		// criteriaSize.setText("[" + context.getString(R.string.criteria_text)
		// + " " + caseBean.getCriteriaNumber() + "]");
		return view;
	}

}
