package com.skylion.request.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.skylion.request.R;
import com.skylion.request.parse.ParseApi;
import com.skylion.request.utils.adapters.RespondListAdapter;
import com.skylion.request.utils.adapters.VacancyListAdapter;

public class RespondsFragment extends Fragment implements ListView.OnItemClickListener {

	private ListView contentList;
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

		 loadData();
//		Toast.makeText(getActivity(), "Coming soon!", Toast.LENGTH_LONG).show();
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

	private void loadData() {
		final ProgressDialog myProgressDialog = ProgressDialog.show(getActivity(), getString(R.string.connection),
				getString(R.string.connection_requests), true);

//		contentList.setAdapter(new RespondListAdapter(getActivity(), ParseApi.getAllResponds(myProgressDialog)));
//		contentList.setAdapter(new VacancyListAdapter(getActivity(), ParseApi.getAllResponds(myProgressDialog)));
		ParseApi.getAllResponds(myProgressDialog, contentList, getActivity());
	}
}
