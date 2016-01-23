package com.mohammadag.smoothsystemprogressbars;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.Interpolator;
import android.widget.ProgressBar;

/**
 * Created by castorflex on 11/10/13.
 */
public class SmoothProgressBar extends ProgressBar {

	private SettingsHelper mSettingsHelper;

	public SmoothProgressBar(Context context) {
		this(context, null);
	}

	public SmoothProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SmoothProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mSettingsHelper = new SettingsHelper(context);

		final float scale = context.getResources().getDisplayMetrics().density;
		final int color = Color.parseColor("#33b5e5");
		final int sectionsCount = mSettingsHelper.getSectionsCount();
		final int separatorLength = mSettingsHelper.getProgressSeparatorLength();
		final float strokeWidth = mSettingsHelper.getStrokeWidth(scale);
		final float speed = mSettingsHelper.getSpeed();
		final Interpolator interpolator = mSettingsHelper.getProgressBarInterpolator();
		final boolean reversed = false;
		final boolean mirrorMode = false;
		final boolean gradients = false;

		SmoothProgressDrawable.Builder builder = new SmoothProgressDrawable.Builder(context)
		.speed(speed)
		.interpolator(interpolator)
		.sectionsCount(sectionsCount)
		.separatorLength(separatorLength)
		.strokeWidth(strokeWidth)
		.reversed(reversed)
		.mirrorMode(mirrorMode)
		.gradients(gradients);

		builder.color(color);

		setIndeterminateDrawable(builder.build());
	}

	public void applyStyle(int styleResId){
		
	}

	@Override
	  protected synchronized void onDraw(Canvas canvas) {
	    super.onDraw(canvas);
	    if (isIndeterminate() && getIndeterminateDrawable() instanceof SmoothProgressDrawable &&
	        !((SmoothProgressDrawable) getIndeterminateDrawable()).isRunning()) {
	      getIndeterminateDrawable().draw(canvas);
	    }
	  }

	  private SmoothProgressDrawable checkIndeterminateDrawable() {
	    Drawable ret = getIndeterminateDrawable();
	    if (ret == null || !(ret instanceof SmoothProgressDrawable))
	      throw new RuntimeException("The drawable is not a SmoothProgressDrawable");
	    return (SmoothProgressDrawable) ret;
	  }

	  @Override
	  public void setInterpolator(Interpolator interpolator) {
	    super.setInterpolator(interpolator);
	    Drawable ret = getIndeterminateDrawable();
	    if (ret != null && (ret instanceof SmoothProgressDrawable))
	      ((SmoothProgressDrawable) ret).setInterpolator(interpolator);
	  }

	  public void setSmoothProgressDrawableInterpolator(Interpolator interpolator) {
	    checkIndeterminateDrawable().setInterpolator(interpolator);
	  }

	  public void setSmoothProgressDrawableColors(int[] colors) {
	    checkIndeterminateDrawable().setColors(colors);
	  }

	  public void setSmoothProgressDrawableColor(int color) {
	    checkIndeterminateDrawable().setColor(color);
	  }

	  public void setSmoothProgressDrawableSpeed(float speed) {
	    checkIndeterminateDrawable().setSpeed(speed);
	  }

	  public void setSmoothProgressDrawableProgressiveStartSpeed(float speed) {
	    checkIndeterminateDrawable().setProgressiveStartSpeed(speed);
	  }

	  public void setSmoothProgressDrawableProgressiveStopSpeed(float speed) {
	    checkIndeterminateDrawable().setProgressiveStopSpeed(speed);
	  }

	  public void setSmoothProgressDrawableSectionsCount(int sectionsCount) {
	    checkIndeterminateDrawable().setSectionsCount(sectionsCount);
	  }

	  public void setSmoothProgressDrawableSeparatorLength(int separatorLength) {
	    checkIndeterminateDrawable().setSeparatorLength(separatorLength);
	  }

	  public void setSmoothProgressDrawableStrokeWidth(float strokeWidth) {
	    checkIndeterminateDrawable().setStrokeWidth(strokeWidth);
	  }

	  public void setSmoothProgressDrawableReversed(boolean reversed) {
	    checkIndeterminateDrawable().setReversed(reversed);
	  }

	  public void setSmoothProgressDrawableMirrorMode(boolean mirrorMode) {
	    checkIndeterminateDrawable().setMirrorMode(mirrorMode);
	  }

	  public void setProgressiveStartActivated(boolean progressiveStartActivated) {
	    checkIndeterminateDrawable().setProgressiveStartActivated(progressiveStartActivated);
	  }

	  public void setSmoothProgressDrawableCallbacks(SmoothProgressDrawable.Callbacks listener) {
	    checkIndeterminateDrawable().setCallbacks(listener);
	  }

	  public void setSmoothProgressDrawableBackgroundDrawable(Drawable drawable) {
	    checkIndeterminateDrawable().setBackgroundDrawable(drawable);
	  }

	  public void setSmoothProgressDrawableUseGradients(boolean useGradients) {
	    checkIndeterminateDrawable().setUseGradients(useGradients);
	  }

	  public void progressiveStart() {
	    checkIndeterminateDrawable().progressiveStart();
	  }

	  public void progressiveStop() {
	    checkIndeterminateDrawable().progressiveStop();
	  }
	}