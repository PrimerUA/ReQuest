package com.skylion.request.fragments.tabs;

import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.skylion.request.R;
import com.skylion.request.entity.Vacancy;
import com.skylion.request.parse.ParseApi;
import com.skylion.request.utils.DialogsViewer;
import com.skylion.request.utils.ExpandableViewHelper;
import com.skylion.request.utils.adapters.VacancyListAdapter;

abstract class CoreVacancyFragment extends Fragment implements ListView.OnItemClickListener {

	private View rootView;
	private int fragment_type;
	private ListView contentList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_vacancy, container, false);
		contentList = (ListView) rootView.findViewById(R.id.vacancyFragment_contentList);
		contentList.setOnItemClickListener(this);

		loadData();

		return rootView;
	}

	private void loadData() {
		ProgressDialog myProgressDialog = ProgressDialog.show(getActivity(), getString(R.string.connection),
				getString(R.string.connection_requests), true);
		List<Vacancy> result = ParseApi.getAllVacancy(fragment_type, myProgressDialog);
		if (result != null)
			contentList.setAdapter(new VacancyListAdapter(getActivity(), result));
		else
			DialogsViewer.showErrorDialog(rootView.getContext(), getString(R.string.error_loading_requests));
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
