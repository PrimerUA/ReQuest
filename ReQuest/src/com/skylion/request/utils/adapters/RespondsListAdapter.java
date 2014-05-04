package com.skylion.request.utils.adapters;

import java.util.List;

import com.skylion.request.R;
import com.skylion.request.entity.Respond;
import com.skylion.request.entity.Vacancy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RespondsListAdapter extends BaseAdapter {
	
	private static class ViewHolder {
		public TextView title;
		public TextView reward;
		public TextView companyName;
		public TextView created;
		public TextView expire;
		public TextView terms;
		public TextView demands;
		public ImageView image;
		public TextView salary;
		public TextView city;
		public TextView companyDescription;
		public TextView companyAddress;
		private TextView author;
		private ImageView avatar;
		private TextView respondsCount;		
	}	
	
	
	
	private View view;
	private ViewGroup container;
	private LayoutInflater inflater;
	private Context context = null;
	private List<Respond> respondList;
	
	public RespondsListAdapter(Context context, List<Respond> result) {
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		respondList = result;
		this.context = context;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
	
		container = parent;
		view = convertView;
		if (view == null)
			view = inflater.inflate(R.layout.respond_list_item, null);
		
		return view;
	}

	@Override
	public int getCount() {	
		return 	respondList.size();
	}

	@Override
	public Object getItem(int position) {
		return respondList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
