package com.mohammadag.smoothsystemprogressbars;

import android.graphics.drawable.Drawable;
import android.widget.ProgressBar;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class SmoothSystemPBar implements IXposedHookZygoteInit, IXposedHookInitPackageResources, IXposedHookLoadPackage {
	
	public static final String PACKAGE_NAME = SmoothSystemPBar.class.getPackage().getName();
	public static String MODULE_PATH = null;
	private static XSharedPreferences prefs;
	private SettingsHelper mSettingsHelper;

	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {

		MODULE_PATH = startupParam.modulePath;
		prefs = new XSharedPreferences(PACKAGE_NAME);
        prefs.makeWorldReadable();
        mSettingsHelper = new SettingsHelper();
		
		SystemWideResources.initResources(prefs); /*Lollipop complete overall*/

		XposedHelpers.findAndHookMethod(ProgressBar.class, "setIndeterminateDrawable", Drawable.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				
				Drawable b = (Drawable) param.args[0];
				if (b == null)
					return;
				
				ProgressBar progressBar = (ProgressBar) param.thisObject;
				if (progressBar == null)
					return;
				
				mSettingsHelper.reloadSettings();

				final float scale = progressBar.getResources().getDisplayMetrics().density;
				
				boolean horizontal = prefs.getBoolean(PrefFragment.PREF_KEY_ENABLE_HORIZONTAL, true);
	            if (horizontal){
					if ("android.graphics.drawable.AnimationDrawable".equals(b.getClass().getName())) {
						Drawable drawable = new SmoothProgressDrawable.Builder(progressBar.getContext())
						.interpolator(mSettingsHelper.getProgressBarInterpolator())
						.sectionsCount(mSettingsHelper.getSectionsCount())
						.separatorLength(mSettingsHelper.getProgressSeparatorLength())
						.strokeWidth(mSettingsHelper.getStrokeWidth(scale))
						.speed(mSettingsHelper.getSpeed())
						.mirrorMode(mSettingsHelper.getMirrored())
						.reversed(mSettingsHelper.getReversed())
						.gradients(mSettingsHelper.getGradients())
						.colors(mSettingsHelper.getProgressBarColors())
						.build();
						
						progressBar.setIndeterminateDrawable(drawable);
	
						param.args[0] = drawable;
						
					}
				}
				
	            boolean circular = prefs.getBoolean(PrefFragment.PREF_KEY_ENABLE_CIRCULAR, true);
	            if (circular){
					if ("android.graphics.drawable.LayerDrawable".equals(b.getClass().getName()) || 
						"android.graphics.drawable.AnimatedRotateDrawable".equals(b.getClass().getName())) {
							Drawable drawable = new CircularProgressDrawable.Builder(progressBar.getContext())
							.colors(mSettingsHelper.getCircularBarColors())
							.strokeWidth(mSettingsHelper.getCStrokeWidth(scale))
							.sweepSpeed(mSettingsHelper.getCSpeed())
							.rotationSpeed(mSettingsHelper.getCSpeed())
							.sweepInterpolator(mSettingsHelper.getCircularBarInterpolator())
							.style(mSettingsHelper.getStyle())
							.build();
							
							progressBar.setIndeterminateDrawable(drawable);
							
							param.args[0] = drawable;	
					}
				}
			}
		});
	}

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleInitPackageResources(InitPackageResourcesParam resparam)
			throws Throwable {
		// TODO Auto-generated method stub
		
	}
}