package com.mohammadag.smoothsystemprogressbars;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mohammadag.smoothsystemprogressbars.SmoothProgressBar;

public class SettingsActivity extends Fragment {

	private SmoothProgressBar mProgressBar;
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

	public SettingsHelper mSettingsHelper;
	protected int mColor = Color.parseColor("#33b5e5");
	private ListView mColorsListView;
	
	private LinearLayout ll;
	private FragmentActivity fa;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getActivity().setTheme(R.style.AppBaseTheme);
		
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		fa = (FragmentActivity) super.getActivity();
		ll = (LinearLayout) inflater.inflate(R.layout.activity_custom, container, false);
		setHasOptionsMenu(true);
		return ll;
	}
		
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		mSettingsHelper = new SettingsHelper(fa.getApplicationContext());

		mProgressBar = (SmoothProgressBar) ll.findViewById(R.id.progressbar);
		mCheckBoxMirror = (CheckBox) ll.findViewById(R.id.checkbox_mirror);
		mCheckBoxReversed = (CheckBox) ll.findViewById(R.id.checkbox_reversed);
		mCheckBoxGradients = (CheckBox) ll.findViewById(R.id.checkbox_gradients);
		mSpinnerInterpolators = (Spinner) ll.findViewById(R.id.spinner_interpolator);
		mSeekBarSectionsCount = (SeekBar) ll.findViewById(R.id.seekbar_sections_count);
		mSeekBarStrokeWidth = (SeekBar) ll.findViewById(R.id.seekbar_stroke_width);
		mSeekBarSeparatorLength = (SeekBar) ll.findViewById(R.id.seekbar_separator_length);
		mSeekBarSpeed = (SeekBar) ll.findViewById(R.id.seekbar_speed);
		mTextViewSpeed = (TextView) ll.findViewById(R.id.textview_speed);
		mTextViewSectionsCount = (TextView) ll.findViewById(R.id.textview_sections_count);
		mTextViewSeparatorLength = (TextView) ll.findViewById(R.id.textview_separator_length);
		mTextViewStrokeWidth = (TextView) ll.findViewById(R.id.textview_stroke_width);
		mColorsListView = (ListView) ll.findViewById(R.id.colorListView);
		
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

		mSpinnerInterpolators.setAdapter(new ArrayAdapter<String>(fa, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.interpolators)));
		mSpinnerInterpolators.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				setValues();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});

		mColorsListView.setAdapter(new ColorArrayAdapter(fa.getApplicationContext(), 0, mSettingsHelper));
		mColorsListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
				Intent colorIntent = new Intent(fa, ColorPickerActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("title", fa.getTitle().toString());
				bundle.putInt("position", position);
				bundle.putString("color", SettingsHelper.convertToARGB(getItem(position)));
				colorIntent.putExtras(bundle);
				startActivityForResult(colorIntent, position);
			}		
		});

		mColorsListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View arg1, int position, long arg3) {
				
				int[] colorsOld = mSettingsHelper.getProgressBarColors();
				
				if (position >= colorsOld.length)
					return true;

				boolean afterHeldColor = false;

				int[] colors = new int[colorsOld.length-1];
				
				for (int i = 0; i < colorsOld.length; i++) {
					if (i != position) {
						if (!afterHeldColor)
							colors[i] = colorsOld[i];
						else
							colors[i-1] = colorsOld[i];
					} else {
						afterHeldColor = true;
					}
				}
				
				mSettingsHelper.setProgressBarColors(colors);
				
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		
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
	}
	
	public int dpToPx(int dp) {
		Resources r = getResources();
		int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dp, r.getDisplayMetrics());
		return px;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    if (!((NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer)).isDrawerOpen())
	    	inflater.inflate(R.menu.menu, menu);
	    	super.onCreateOptionsMenu(menu, inflater);
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
			Toast.makeText(fa, getString(R.string.item_successfully_saved), Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}