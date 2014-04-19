package com.skylion.request.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skylion.request.R;
import com.skylion.request.fragments.tabs.GeneralVacancyFragment;
import com.skylion.request.fragments.tabs.HotVacancyFragment;
import com.skylion.request.fragments.tabs.MyVacancyFragment;

public class VacancyFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";
	private FragmentTabHost mTabHost;
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static VacancyFragment newInstance(int sectionNumber) {
		VacancyFragment fragment = new VacancyFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public VacancyFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mTabHost = new FragmentTabHost(getActivity());
	    mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.fragment_container);

	    mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.title_tabs_general)).setIndicator(getString(R.string.title_tabs_general)),
	    		GeneralVacancyFragment.class, null);
//	    mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.title_tabs_hot)).setIndicator(getString(R.string.title_tabs_hot)),
//	    		HotVacancyFragment.class, null);
	    mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.title_tabs_my)).setIndicator(getString(R.string.title_tabs_my)),
	    		MyVacancyFragment.class, null);

	    //mTabHost.setCurrentTab(1);
	    return mTabHost;
	}

	@Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabHost = null;
    }
}