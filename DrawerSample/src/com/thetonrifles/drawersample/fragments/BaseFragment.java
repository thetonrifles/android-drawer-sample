package com.thetonrifles.drawersample.fragments;

import android.app.Fragment;

public class BaseFragment extends Fragment {

	/**
	 * Returns <em>true</em> if provided fragment is same as current,
	 * <em>false</em> otherwise.
	 * 
	 * @param fragment
	 *            BaseFragment to check.
	 * @return <em>true</em> if provided fragment is same as current,
	 *         <em>false</em> otherwise.
	 */
	public boolean same(BaseFragment fragment) {
		Class<?> clazz = fragment.getClass();
		return getClass().equals(clazz);
	}

}
