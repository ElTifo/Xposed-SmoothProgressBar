package com.mohammadag.smoothsystemprogressbars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.widget.Toast;

public class PrefFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
	
	private SharedPreferences mPrefs;
	
	public static final String PREF_KEY_HIDE_LAUNCHER_ICON = "pref_hide_launcher_icon";
	public static final String PREF_KEY_ENABLE_HORIZONTAL = "pref_enable_horizontal";
	public static final String PREF_KEY_ENABLE_CIRCULAR = "pref_enable_circular";
	
	private static final List<String> rebootKeys = new ArrayList<String>(Arrays.asList(
    		PREF_KEY_ENABLE_HORIZONTAL,
    		PREF_KEY_ENABLE_CIRCULAR
    ));
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getPreferenceManager().setSharedPreferencesMode(Context.MODE_WORLD_READABLE);
		addPreferencesFromResource(R.xml.preferences);
		mPrefs = getPreferenceScreen().getSharedPreferences();
	}
	
	@Override
    public void onStart() {
        super.onStart();
        mPrefs.registerOnSharedPreferenceChangeListener(this);
    }
	
	@Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
    	
    	/* Taken from Peter Gregus's GravityBox Project (C3C076@xda)*/ 
    	if (key.equals(PREF_KEY_HIDE_LAUNCHER_ICON)) {
    		int mode = prefs.getBoolean(key, false) ?
    			PackageManager.COMPONENT_ENABLED_STATE_DISABLED :
    				PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
    			getActivity().getPackageManager().setComponentEnabledSetting(
    				new ComponentName(getActivity(), "com.mohammadag.smoothsystemprogressbars.MainAlias"),
	                mode, PackageManager.DONT_KILL_APP);
			}
    	if (rebootKeys.contains(key))
            Toast.makeText(getActivity(), getString(R.string.reboot_required), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        mPrefs.unregisterOnSharedPreferenceChangeListener(this);
        super.onStop();
    }
    
}
