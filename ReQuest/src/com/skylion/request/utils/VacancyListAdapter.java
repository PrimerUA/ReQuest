package com.skylion.request.utils;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.skylion.request.R;

public class VacancyListAdapter extends BaseAdapter {

	private View view;

	private List<ParseObject> requestList;

	private LayoutInflater inflater;

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
		TextView created = (TextView) view.findViewById(R.id.vacancyItem_createdText);
		final ImageView image = (ImageView) view.findViewById(R.id.vacancyItem_imageView);
		// TextView criteriaSize = (TextView)
		// view.findViewById(R.id.CaseListItem_caseCriteriaSize);
		// TextView expireDate = (TextView)
		// view.findViewById(R.id.CaseListItem_caseExpDate);
		final TextView authorText = (TextView) view.findViewById(R.id.vacancyItem_authorText);
		// TextView rateText = (TextView)
		// view.findViewById(R.id.CaseListItem_caseRate);
		// LinearLayout mainLayout = (LinearLayout)
		// view.findViewById(R.id.CaseListItem_mainLayout);

		ParseFile applicantResume = (ParseFile) request.get("image");
		applicantResume.getDataInBackground(new GetDataCallback() {
			public void done(byte[] data, ParseException e) {
				if (e == null) {
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inSampleSize = 1;
					Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
					image.setImageBitmap(bitmap);
				} else {
					image.setVisibility(View.GONE);
				}
			}
		});

		title.setText(request.getString("title"));
		company.setText(request.getString("company"));
		created.setText(request.getCreatedAt().toGMTString());

		// ParseRelation<ParseObject> relation = request.getRelation("user");
		// ParseQuery<ParseObject> query = relation.getQuery();
		// query.findInBackground(new FindCallback<ParseObject>() {
		//
		// @Override
		// public void done(List<ParseObject> authors, ParseException arg1) {
		// authorText.setText(authors.get(0).toString());
		// }
		// });

		// now execute the query

		// expireDate.setText("[" +
		// DateFormatter.getFormatDate(caseBean.getExpireDate()) + "]");
		fund.setText("$" + request.getInt("reward"));

		// criteriaSize.setText("[" + context.getString(R.string.criteria_text)
		// + " " + caseBean.getCriteriaNumber() + "]");
		return view;
	}

}
