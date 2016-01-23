package com.mohammadag.smoothsystemprogressbars;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SmoothSystemPrefs extends Fragment {
	
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTheme(R.style.Theme_Prefs);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.prefs, container, false);
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getFragmentManager().beginTransaction().replace(R.id.pref_frame, new PrefFragment()).commit();
    }
	
}
