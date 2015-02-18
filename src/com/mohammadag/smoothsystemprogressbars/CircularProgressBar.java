package com.mohammadag.smoothsystemprogressbars;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.Interpolator;
import android.widget.ProgressBar;

/**
 * Created by castorflex on 11/10/13.
 */
public class CircularProgressBar extends ProgressBar {

	private SettingsHelper mSettingsHelper;
	
	public CircularProgressBar(Context context) {
		this(context, null);
    }

	public CircularProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CircularProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mSettingsHelper = new SettingsHelper(context);
		
		final float scale = context.getResources().getDisplayMetrics().density;
		final int color = Color.parseColor("#33b5e5");
		final int[] colors = mSettingsHelper.getProgressBarColors();
		final float strokeWidth = mSettingsHelper.getStrokeWidth(scale);
		final float sweepSpeed = mSettingsHelper.getSpeed();
		final float rotationSpeed = mSettingsHelper.getSpeed();
		final int minSweepAngle = mSettingsHelper.getMinSweepAngle();
		final int maxSweepAngle = mSettingsHelper.getMaxSweepAngle();
		final Interpolator interpolator = mSettingsHelper.getProgressBarInterpolator();
		
	    Drawable indeterminateDrawable;
	    CircularProgressDrawable.Builder builder = new CircularProgressDrawable.Builder(context)
	        .sweepSpeed(sweepSpeed)
	        .rotationSpeed(rotationSpeed)
	        .strokeWidth(strokeWidth)
	        .minSweepAngle(minSweepAngle)
	        .maxSweepAngle(maxSweepAngle)
	        .sweepInterpolator(interpolator);

	    if (colors != null && colors.length > 0)
	      builder.colors(colors);
	    else
	      builder.color(color);

	    indeterminateDrawable = builder.build();
	    setIndeterminateDrawable(indeterminateDrawable);
		
	}
	
	public void applyStyle(int styleResId){
		
	}

	private CircularProgressDrawable checkIndeterminateDrawable() {
		Drawable ret = getIndeterminateDrawable();
		if (ret == null || !(ret instanceof CircularProgressDrawable))
			throw new RuntimeException("The drawable is not a CircularProgressDrawable");
		return (CircularProgressDrawable) ret;
	}

	public void progressiveStop() {
		checkIndeterminateDrawable().progressiveStop();
	}

	public void progressiveStop(CircularProgressDrawable.OnEndListener listener) {
		checkIndeterminateDrawable().progressiveStop(listener);
	}
}