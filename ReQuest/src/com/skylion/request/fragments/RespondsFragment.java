package com.skylion.request.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v4.widget.SwipeRefreshLayout;

import com.skylion.request.R;
import com.skylion.request.RequestConstants;
import com.skylion.request.parse.ParseApi;

public class RespondsFragment extends Fragment implements ListView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

	private ListView contentList;
	private SwipeRefreshLayout refreshLayout;
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static RespondsFragment newInstance(int sectionNumber) {
		RespondsFragment fragment = new RespondsFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);				
		return fragment;
	}

	public RespondsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_responds, container, false);
		contentList = (ListView) rootView.findViewById(R.id.requestFragment_contentList);
		contentList.setOnItemClickListener(this);

		refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.navigation_fragment_container);		 
	    refreshLayout.setOnRefreshListener(this);
	    refreshLayout.setColorScheme(android.R.color.holo_red_light,
		            android.R.color.black,
		            android.R.color.white,
		            android.R.color.black);
		loadData(RequestConstants.RESPOND_LOAD);
		
		return rootView;
	}		

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
		
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub

	}	

	private void loadData(int select) {
		
		switch (select) {
		case RequestConstants.RESPOND_LOAD: {
			final ProgressDialog myProgressDialog = ProgressDialog.show(getActivity(), getString(R.string.connection),
					getString(R.string.connection_requests), true);
			ParseApi.getAllResponds(myProgressDialog, contentList, getActivity(), null);
			break;
		}
		case RequestConstants.RESPOND_REFRESH: {			
			ParseApi.getAllResponds(null, contentList, getActivity(), refreshLayout);
			break;
		}
		default:
			break;
		}
		
		
	}

	@Override
	public void onRefresh() {			
		loadData(RequestConstants.RESPOND_REFRESH);
	}
}
