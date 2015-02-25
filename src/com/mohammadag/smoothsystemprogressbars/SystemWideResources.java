package com.mohammadag.smoothsystemprogressbars;

import android.content.res.XModuleResources;
import android.content.res.XResources;
import com.mohammadag.smoothsystemprogressbars.R;
import de.robv.android.xposed.XposedBridge;

public class SystemWideResources {
	
	public static void initResources() {
        
		try {

            XModuleResources modRes = XModuleResources.createInstance(SmoothSystemPBar.MODULE_PATH, null);
            
            /*Lollipop workaround*/
            
            XResources.setSystemWideReplacement("android", "drawable", "progress_indeterminate_horizontal_material", modRes.fwd(R.drawable.progress_indeterminate_horizontal_holo));
            
            XResources.setSystemWideReplacement("android", "drawable", "progress_large_material", modRes.fwd(R.drawable.progress_large_holo));
            
            XResources.setSystemWideReplacement("android", "drawable", "progress_medium_material", modRes.fwd(R.drawable.progress_medium_holo));
            
            XResources.setSystemWideReplacement("android", "drawable", "progress_small_material", modRes.fwd(R.drawable.progress_small_holo));
            
        } catch (Throwable t) {
            XposedBridge.log(t);
        }
    }

}