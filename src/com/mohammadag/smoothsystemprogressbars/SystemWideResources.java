package com.mohammadag.smoothsystemprogressbars;

import android.content.res.XModuleResources;
import android.content.res.XResources;

import com.mohammadag.smoothsystemprogressbars.PrefFragment;
import com.mohammadag.smoothsystemprogressbars.R;

import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;

public class SystemWideResources {
	
	public static void initResources(final XSharedPreferences prefs) {
        
		try {

            XModuleResources modRes = XModuleResources.createInstance(SmoothSystemPBar.MODULE_PATH, null);
            
            /*Lollipop workaround*/
            boolean horizontal = prefs.getBoolean(PrefFragment.PREF_KEY_ENABLE_HORIZONTAL, true);
            if (horizontal){
            XResources.setSystemWideReplacement("android", "drawable", "progress_indeterminate_horizontal_material", modRes.fwd(R.drawable.progress_indeterminate_horizontal_holo));
            }
            
            boolean circular = prefs.getBoolean(PrefFragment.PREF_KEY_ENABLE_CIRCULAR, true);
            if (circular){
            	XResources.setSystemWideReplacement("android", "drawable", "progress_large_material", modRes.fwd(R.drawable.progress_large_holo));
            	
            	XResources.setSystemWideReplacement("android", "drawable", "progress_medium_material", modRes.fwd(R.drawable.progress_medium_holo));
            	
            	XResources.setSystemWideReplacement("android", "drawable", "progress_small_material", modRes.fwd(R.drawable.progress_small_holo));
            }
            
        } catch (Throwable t) {
            XposedBridge.log(t);
        }
    }

}