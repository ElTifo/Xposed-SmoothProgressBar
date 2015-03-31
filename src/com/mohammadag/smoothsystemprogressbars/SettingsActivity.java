package com.mohammadag.smoothsystemprogressbars;

import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/*castorflex used these as well*/
import com.mohammadag.smoothsystemprogressbars.CircularProgressBar;
import com.mohammadag.smoothsystemprogressbars.CircularProgressDrawable;
import com.mohammadag.smoothsystemprogressbars.SmoothProgressBar;

public class SettingsActivity extends ActionBarActivity {

	private SmoothProgressBar mProgressBar;
	private CircularProgressBar mCircularProgressBar;
	private CheckBox mCheckBoxMirror;
	private CheckBox mCheckBoxReversed;
	private CheckBox mCheckBoxGradients;
	private Spinner mSpinnerInterpolators;
	private SeekBar mSeekBarSectionsCount;
	private SeekBar mSeekBarStrokeWidth;
	private SeekBar mSeekBarSeparatorLength;
	private SeekBar mSeekBarSpeed;
	private TextView mTextViewSpeed;
	private TextView mTextViewStrokeWidth;
	private TextView mTextViewSeparatorLength;
	private TextView mTextViewSectionsCount;

	private Interpolator mCurrentInterpolator;
	private int mStrokeWidth = 4;
	private int mSeparatorLength;
	private int mSectionsCount;
	private float mSpeed = 1f;

	private SettingsHelper mSettingsHelper;
	protected int mColor = Color.parseColor("#33b5e5");
	private ListView mColorsListView;
	
	private Toolbar toolbar; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_custom);
		
		toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

		mSettingsHelper = new SettingsHelper(getApplicationContext());

		mProgressBar = (SmoothProgressBar) findViewById(R.id.progressbar);
		mCircularProgressBar = (CircularProgressBar) findViewById(R.id.progressbar_circular);
		mCheckBoxMirror = (CheckBox) findViewById(R.id.checkbox_mirror);
		mCheckBoxReversed = (CheckBox) findViewById(R.id.checkbox_reversed);
		mCheckBoxGradients = (CheckBox) findViewById(R.id.checkbox_gradients);
		mSpinnerInterpolators = (Spinner) findViewById(R.id.spinner_interpolator);
		mSeekBarSectionsCount = (SeekBar) findViewById(R.id.seekbar_sections_count);
		mSeekBarStrokeWidth = (SeekBar) findViewById(R.id.seekbar_stroke_width);
		mSeekBarSeparatorLength = (SeekBar) findViewById(R.id.seekbar_separator_length);
		mSeekBarSpeed = (SeekBar) findViewById(R.id.seekbar_speed);
		mTextViewSpeed = (TextView) findViewById(R.id.textview_speed);
		mTextViewSectionsCount = (TextView) findViewById(R.id.textview_sections_count);
		mTextViewSeparatorLength = (TextView) findViewById(R.id.textview_separator_length);
		mTextViewStrokeWidth = (TextView) findViewById(R.id.textview_stroke_width);
		mColorsListView = (ListView) findViewById(R.id.colorListView);
		
		OnClickListener checkboxListener = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setValues();
			}
		};
		
		mSectionsCount = mSettingsHelper.getSectionsCount();
		
		mCheckBoxMirror.setOnClickListener(checkboxListener);
		mCheckBoxReversed.setOnClickListener(checkboxListener);
		mCheckBoxGradients.setOnClickListener(checkboxListener);
		
		mSeekBarSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				mSpeed = ((float) progress + 1) / 10;
				mTextViewSpeed.setText(getString(R.string.speed, String.valueOf(mSpeed)));
				mProgressBar.setSmoothProgressDrawableSpeed(mSpeed);
				updateValues();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		mSeekBarSectionsCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				mTextViewSectionsCount.setText(getString(R.string.sections_count, progress+1));
				if (fromUser) {
					mSectionsCount = progress + 1;
					mProgressBar.setSmoothProgressDrawableSectionsCount(mSectionsCount);
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		mSeekBarSeparatorLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				mSeparatorLength = progress;
				mTextViewSeparatorLength.setText(getString(R.string.separator_length, mSeparatorLength));
				mProgressBar.setSmoothProgressDrawableSeparatorLength(dpToPx(mSeparatorLength));
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		mSeekBarStrokeWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				mStrokeWidth = progress;
				mTextViewStrokeWidth.setText(getString(R.string.stroke_width, mStrokeWidth));
				mProgressBar.setSmoothProgressDrawableStrokeWidth(dpToPx(mStrokeWidth));
				updateValues();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
		
		mSeekBarSeparatorLength.setProgress(mSettingsHelper.getProgressSeparatorLength());
		mSeekBarSectionsCount.setProgress(mSectionsCount);
		mSeekBarStrokeWidth.setProgress((int)mSettingsHelper.getStrokeWidth());
		mCheckBoxMirror.setChecked(mSettingsHelper.getMirrored());
		mCheckBoxReversed.setChecked(mSettingsHelper.getReversed());
		mCheckBoxGradients.setChecked(mSettingsHelper.getGradients());
		mSpeed = mSettingsHelper.getSpeed();
		mSeekBarSpeed.setProgress((int)(mSpeed * 10) - 1);
		mTextViewSpeed.setText(getString(R.string.speed, mSpeed));
		mTextViewSeparatorLength.setText(getString(R.string.separator_length, mSeparatorLength));

		mSpinnerInterpolators.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.interpolators)));
		mSpinnerInterpolators.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				setValues();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
	    updateValues();

		mColorsListView.setAdapter(new ColorArrayAdapter(getApplicationContext(), 0, mSettingsHelper));
		mColorsListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
				Intent colorIntent = new Intent(SettingsActivity.this, ColorPickerActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("title", getTitle().toString());
				bundle.putInt("key", position);
				bundle.putString("color", SettingsHelper.convertToARGB(getItem(position)));
				colorIntent.putExtras(bundle);
				startActivityForResult(colorIntent, position);
			}		
		});

		mColorsListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View arg1, int position, long arg3) {
				int[] colors = mSettingsHelper.getProgressBarColors();
				if (position >= colors.length)
					return true;

				boolean afterHeldColor = false;

				int[] colorsNew = new int[colors.length-1];
				for (int i = 0; i < colors.length; i++) {
					if (i != position) {
						if (!afterHeldColor)
							colorsNew[i] = colors[i];
						else
							colorsNew[i-1] = colors[i];
					} else {
						afterHeldColor = true;
					}
				}

				mSettingsHelper.setProgressBarColors(colorsNew);
				
				setValues();
				
				return true;
			}
		});
		
		Interpolator interpolator = mSettingsHelper.getProgressBarInterpolator();
		int position = 0;
		if (interpolator instanceof AccelerateInterpolator)
			position = 0;
		else if (interpolator instanceof LinearInterpolator)
			position = 1;
		else if (interpolator instanceof AccelerateDecelerateInterpolator)
			position = 2;
		else if (interpolator instanceof DecelerateInterpolator)
			position = 3;
		
		mSpinnerInterpolators.setSelection(position);
		setValues();
		mTextViewSectionsCount.setText(getString(R.string.sections_count,
				String.valueOf(mSectionsCount)));
		mSeekBarSectionsCount.setProgress(mSectionsCount-1);
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (resultCode == Activity.RESULT_CANCELED)
			return;
		
		int position = requestCode;

		int[] colors = mSettingsHelper.getProgressBarColors();
		
		String color = "#" + data.getStringExtra("color");

		if (position >= colors.length) {
			int[] colorsNew = new int[position+1];
			for (int i = 0; i < colors.length; i++) {
				colorsNew[i] = colors[i];
			}
			colorsNew[position] = Color.parseColor(color);
			colors = colorsNew;
		} else {
			colors[position] = Color.parseColor(color);
		}

		mSettingsHelper.setProgressBarColors(colors);
		
		setValues();
		
		super.onActivityResult(requestCode, resultCode, data);
		
	}

	public Integer getItem(int position) {
		try {
			return mSettingsHelper.getProgressBarColors()[position];
		} catch (ArrayIndexOutOfBoundsException e) {
			return Color.parseColor("#33b5e5");
		}
	}
	
	private void setValues() {
		((ColorArrayAdapter) mColorsListView.getAdapter()).notifyDataSetChanged();
		mProgressBar.setSmoothProgressDrawableReversed(mCheckBoxReversed.isChecked());
		mProgressBar.setSmoothProgressDrawableMirrorMode(mCheckBoxMirror.isChecked());
		mProgressBar.setSmoothProgressDrawableUseGradients(mCheckBoxGradients.isChecked());

		switch (mSpinnerInterpolators.getSelectedItemPosition()) {
		case 1:
			mCurrentInterpolator = new LinearInterpolator();
			break;
		case 2:
			mCurrentInterpolator = new AccelerateDecelerateInterpolator();
			break;
		case 3:
			mCurrentInterpolator = new DecelerateInterpolator();
			break;
		case 0:
		default:
			mCurrentInterpolator = new AccelerateInterpolator();
			break;
		}

		mProgressBar.setSmoothProgressDrawableInterpolator(mCurrentInterpolator);
		mProgressBar.setSmoothProgressDrawableColors(mSettingsHelper.getProgressBarColors());
		updateValues();
	}
	
	private void updateValues() {
		CircularProgressDrawable circularProgressDrawable;
		CircularProgressDrawable.Builder b = new CircularProgressDrawable
			.Builder(this)
			.colors(mSettingsHelper.getProgressBarColors())
			.sweepSpeed(mSpeed)
			.rotationSpeed(mSpeed)
			.strokeWidth(dpToPx(mStrokeWidth))
			.style(CircularProgressDrawable.Style.ROUNDED);
		if (mCurrentInterpolator != null) {
		  b.sweepInterpolator(mCurrentInterpolator);
		}
		mCircularProgressBar.setIndeterminateDrawable(circularProgressDrawable = b.build());

		// /!\ Terrible hack, do not do this at home!
		circularProgressDrawable.setBounds(0,
			0,
			mCircularProgressBar.getWidth(),
			mCircularProgressBar.getHeight());
		mCircularProgressBar.setVisibility(View.INVISIBLE);
		mCircularProgressBar.setVisibility(View.VISIBLE);
	  }
	
	public int dpToPx(int dp) {
		Resources r = getResources();
		int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dp, r.getDisplayMetrics());
		return px;
	}
	
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.save_item:
			setValues();
			mSettingsHelper.setSpeed(mSpeed).setSectionCount(mSectionsCount).setStrokeWidth(mStrokeWidth);
			mSettingsHelper.setProgressSeparatorLength(mSeparatorLength).setMirrored(mCheckBoxMirror.isChecked());
			mSettingsHelper.setReversed(mCheckBoxReversed.isChecked()).setProgressBarColor(mColor);
			mSettingsHelper.setGradients(mCheckBoxGradients.isChecked());
			mSettingsHelper.setProgressBarInterpolator(mCurrentInterpolator);
			Toast.makeText(this, getString(R.string.item_successfully_saved), Toast.LENGTH_SHORT).show();
			return true;
		case R.id.prefs:
			Intent intent = new Intent(this, SmoothSystemPrefs.class);
            startActivity(intent);
            return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/* Show overflow menu icons*/
	@Override
	public boolean onMenuOpened(int featureId, android.view.Menu menu)
	{
	    if(featureId == Window.FEATURE_ACTION_BAR && menu != null){
	        if(menu.getClass().getSimpleName().equals("MenuBuilder")){
	            try{
	                Method m = menu.getClass().getDeclaredMethod(
	                    "setOptionalIconsVisible", Boolean.TYPE);
	                m.setAccessible(true);
	                m.invoke(menu, true);
	            }
	            catch(NoSuchMethodException e){
	                String TAG = null;
					Log.e(TAG, "onMenuOpened", e);
	            }
	            catch(Exception e){
	                throw new RuntimeException(e);
	            }
	        }
	    }
	    return super.onMenuOpened(featureId, menu);
	}
}