package com.skylion.request.fragments.tabs;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

import com.skylion.request.R;
import com.skylion.request.entity.RequestConstants;
import com.skylion.request.entity.Vacancy;
import com.skylion.request.parse.ParseApi;
import com.skylion.request.utils.ExpandableViewHelper;

abstract class CoreVacancyFragment extends Fragment implements ListView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

	private View rootView;
	private int fragment_type;
	private ListView contentList;
	private SwipeRefreshLayout refreshLayout;
	public List<Vacancy> result = new ArrayList<Vacancy>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		final List<Vacancy> result = new ArrayList<Vacancy>();
		result.clear();
		rootView = inflater.inflate(R.layout.fragment_vacancy, container, false);
		refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.navigation_fragment_container);
		refreshLayout.setOnRefreshListener(this);
	    refreshLayout.setColorScheme(android.R.color.holo_red_light,
		            android.R.color.black,
		            android.R.color.white,
		            android.R.color.black);
		
		contentList = (ListView) rootView.findViewById(R.id.vacancyFragment_contentList);
		contentList.setOnItemClickListener(this);		
		contentList.setOnScrollListener(new OnScrollListener() {
			int count = RequestConstants.LIST_ITEMS_LOAD;
			int currentVisibleItemCount = 0;
			int currentFirstVisibleItem = 0;
			int currentScrollState = 0;
			boolean isload = true;
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				this.currentScrollState = scrollState;
				this.isScrollCompleted();
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				this.currentFirstVisibleItem = firstVisibleItem;
				this.currentVisibleItemCount = view.getFirstVisiblePosition();				
			}
			
			private void isScrollCompleted() {
				if (this.currentVisibleItemCount >= (result.size() - 1) && this.currentScrollState == SCROLL_STATE_IDLE && isload) {
					ParseApi.loadVacancyList(fragment_type, contentList, count, result, null);
					if(count > result.size() )
						isload = false;
					else
						count += RequestConstants.LIST_ITEMS_LOAD;
				}
			}
			
		});
		ParseApi.loadVacancyList(fragment_type, contentList, 0, result, null);			
		
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
	
	@Override
	public void onRefresh() {
		result.clear();
		ParseApi.loadVacancyList(fragment_type, contentList, 0, result, refreshLayout);
	}
	
}
