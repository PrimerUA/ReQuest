package com.skylion.request.fragments.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.skylion.request.R;
import com.skylion.request.parse.ParseApi;
import com.skylion.request.utils.ExpandableViewHelper;



abstract class CoreVacancyFragment extends Fragment implements ListView.OnItemClickListener {

	private View rootView;
	private int fragment_type;
	private ListView contentList;		
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_vacancy, container, false);
		contentList = (ListView) rootView.findViewById(R.id.vacancyFragment_contentList);
		contentList.setOnItemClickListener(this);
		
		ParseApi.getInstance().setListView(contentList).loadVacancyList(fragment_type, getActivity());					
		return rootView;
	}

	protected void onCreateVacancyFragment(int fragment_type) {
		this.fragment_type = fragment_type;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		LinearLayout expandableLayout = (LinearLayout) view.findViewById(R.id.vacancyItem_expandableLayout);
		if (expandableLayout.isShown()) {
			ExpandableViewHelper.slideIntoDirection(view.getContext(), expandableLayout, R.anim.item_slide_up);
			expandableLayout.setVisibility(View.GONE);
		} else {
			expandableLayout.setVisibility(View.VISIBLE);
			ExpandableViewHelper.slideIntoDirection(view.getContext(), expandableLayout, R.anim.item_slide_down);
		}
	}
}
