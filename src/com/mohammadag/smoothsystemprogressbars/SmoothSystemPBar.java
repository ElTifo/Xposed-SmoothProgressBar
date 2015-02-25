package com.mohammadag.smoothsystemprogressbars;

import android.graphics.drawable.Drawable;
import android.widget.ProgressBar;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class SmoothSystemPBar implements IXposedHookZygoteInit, IXposedHookInitPackageResources, IXposedHookLoadPackage {
	
	public static final String PACKAGE_NAME = SmoothSystemPBar.class.getPackage().getName();
	
	public static String MODULE_PATH = null;
	
	private SettingsHelper mSettingsHelper;

	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {

		MODULE_PATH = startupParam.modulePath;
		
		mSettingsHelper = new SettingsHelper();
		
		SystemWideResources.initResources(); /*Lollipop complete overall*/

		XposedHelpers.findAndHookMethod(ProgressBar.class, "setIndeterminateDrawable", Drawable.class, new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				
				Drawable b = (Drawable) param.args[0];
				if (b == null)
					return;
				
				ProgressBar progressBar = (ProgressBar) param.thisObject;
				if (progressBar == null)
					return;
				
				mSettingsHelper.reloadSettings();

				final float scale = progressBar.getResources().getDisplayMetrics().density;
				
				if ("android.graphics.drawable.AnimationDrawable".equals(b.getClass().getName())) {
					Drawable drawable = new SmoothProgressDrawable.Builder(progressBar.getContext())
					.interpolator(mSettingsHelper.getProgressBarInterpolator())
					.sectionsCount(mSettingsHelper.getSectionsCount())
					.separatorLength(mSettingsHelper.getProgressSeparatorLength())
					.strokeWidth(mSettingsHelper.getStrokeWidth(scale))
					.speed(mSettingsHelper.getSpeed())
					.mirrorMode(mSettingsHelper.getMirrored())
					.reversed(mSettingsHelper.getReversed())
					.colors(mSettingsHelper.getProgressBarColors())
					.build();
					
					progressBar.setIndeterminateDrawable(drawable);

					param.args[0] = drawable;
					
				}
				
				
				if ("android.graphics.drawable.LayerDrawable".equals(b.getClass().getName()) || 
					"android.graphics.drawable.AnimatedRotateDrawable".equals(b.getClass().getName())) {
					Drawable drawable = new CircularProgressDrawable.Builder(progressBar.getContext())
					.colors(mSettingsHelper.getProgressBarColors())
					.strokeWidth(mSettingsHelper.getStrokeWidth(scale))
					.sweepSpeed(mSettingsHelper.getSpeed())
					.rotationSpeed(mSettingsHelper.getSpeed())
					.sweepInterpolator(mSettingsHelper.getProgressBarInterpolator())
					.style(CircularProgressDrawable.Style.ROUNDED)
					.build();
					
					progressBar.setIndeterminateDrawable(drawable);
					
					param.args[0] = drawable;
						
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