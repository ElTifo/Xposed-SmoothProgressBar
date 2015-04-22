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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mohammadag.smoothsystemprogressbars.CircularProgressBar;
import com.mohammadag.smoothsystemprogressbars.CircularProgressDrawable;
import com.mohammadag.smoothsystemprogressbars.CircularProgressDrawable.Style;

public class SettingsActivityC extends Fragment {

	private CircularProgressBar mCircularProgressBar;
	private Spinner mSpinnerStyle;
	private Spinner mSpinnerInterpolators;
	private SeekBar mSeekBarStrokeWidth;
	private SeekBar mSeekBarSpeed;
	private SeekBar mSeekBarFactor;
	private TextView mTextViewFactor;
	private TextView mTextViewSpeed;
	private TextView mTextViewStrokeWidth;

	private Style mCurrentStyle;
	private Interpolator mCurrentInterpolator;
	private int mStrokeWidth = 4;
	private float mSpeed = 1f;
	private float mFactor = 1f;

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
		ll = (LinearLayout) inflater.inflate(R.layout.activity_custom_c, container, false);
		setHasOptionsMenu(true);
		return ll;
	}
		
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		mSettingsHelper = new SettingsHelper(fa.getApplicationContext());

		mCircularProgressBar = (CircularProgressBar) ll.findViewById(R.id.progressbar_circular);
		mSpinnerStyle = (Spinner) ll.findViewById(R.id.spinner_style);
		mSpinnerInterpolators = (Spinner) ll.findViewById(R.id.spinner_interpolator);
		mSeekBarStrokeWidth = (SeekBar) ll.findViewById(R.id.seekbar_stroke_width);
		mTextViewStrokeWidth = (TextView) ll.findViewById(R.id.textview_stroke_width);
		mSeekBarSpeed = (SeekBar) ll.findViewById(R.id.seekbar_speed);
		mTextViewSpeed = (TextView) ll.findViewById(R.id.textview_speed);
		mSeekBarFactor = (SeekBar) ll.findViewById(R.id.seekbar_factor);
		mTextViewFactor = (TextView) ll.findViewById(R.id.textview_factor);
		mColorsListView = (ListView) ll.findViewById(R.id.colorListView);
		
		mSeekBarSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				mSpeed = ((float) progress + 1) / 10;
				mTextViewSpeed.setText(getString(R.string.speed, String.valueOf(mSpeed)));
				updateValues();
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
				updateValues();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
		
		mSeekBarFactor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
		      @Override
		      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		        mFactor = ((float) progress + 1) / 10;
		        mTextViewFactor.setText(getString(R.string.factor, String.valueOf(mFactor)));
		        setInterpolator(mSpinnerInterpolators.getSelectedItemPosition());
		      }

		      @Override
		      public void onStartTrackingTouch(SeekBar seekBar) {

		      }

		      @Override
		      public void onStopTrackingTouch(SeekBar seekBar) {

		      }
		    });
		
		mSeekBarStrokeWidth.setProgress((int)mSettingsHelper.getCStrokeWidth());
		mSpeed = mSettingsHelper.getCSpeed();
		mSeekBarSpeed.setProgress((int)(mSpeed * 10) - 1);
		mTextViewSpeed.setText(getString(R.string.speed, mSpeed));
		mFactor = mSettingsHelper.getCFactor();
		mSeekBarFactor.setProgress((int)(mFactor * 10) - 1);
		mTextViewFactor.setText(getString(R.string.factor, mFactor));
		
		mSpinnerStyle.setAdapter(new ArrayAdapter<String>(fa, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.circular_style)));
		mSpinnerStyle.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				setValues();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
		mSpinnerInterpolators.setAdapter(new ArrayAdapter<String>(fa, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.interpolators)));
		mSpinnerInterpolators.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				setInterpolator(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});

		mColorsListView.setAdapter(new ColorArrayAdapterC(fa.getApplicationContext(), 0, mSettingsHelper));
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
				
				int[] colorsOld = mSettingsHelper.getCircularBarColors();
				
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
				
				mSettingsHelper.setCircularBarColors(colors);
				
				setValues();
				
				return true;
			}
		});
		
		Interpolator interpolator = mSettingsHelper.getCircularBarInterpolator();
		int iposition = 0;
		if (interpolator instanceof AccelerateInterpolator)
			iposition = 0;
		else if (interpolator instanceof LinearInterpolator)
			iposition = 1;
		else if (interpolator instanceof AccelerateDecelerateInterpolator)
			iposition = 2;
		else if (interpolator instanceof DecelerateInterpolator)
			iposition = 3;
		
		mSpinnerInterpolators.setSelection(iposition);
		
		setValues();
		
		Style style = mSettingsHelper.getStyle();
		int sposition = 0;
		switch (style){
		case ROUNDED:
			sposition = 0;
			break;
		case NORMAL:
			sposition = 1;
			break;
		default:
			sposition = 0;
		}
		mSpinnerStyle.setSelection(sposition);
		
		setValues();
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == Activity.RESULT_CANCELED)
			return;
		
		int position = requestCode;

		int[] colors = mSettingsHelper.getCircularBarColors();
		
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
		
		mSettingsHelper.setCircularBarColors(colors);
		setValues();
		
	}

	public Integer getItem(int position) {
		try {
			return mSettingsHelper.getCircularBarColors()[position];
		} catch (ArrayIndexOutOfBoundsException e) {
			return Color.parseColor("#33b5e5");
		}
	}
	
	private void setInterpolator(int position) {
	    switch (position) {
	      case 1:
	        mCurrentInterpolator = new LinearInterpolator();
	        mSeekBarFactor.setEnabled(false);
	        break;
	      case 2:
	        mCurrentInterpolator = new AccelerateDecelerateInterpolator();
	        mSeekBarFactor.setEnabled(false);
	        break;
	      case 3:
	        mCurrentInterpolator = new DecelerateInterpolator(mFactor);
	        mSeekBarFactor.setEnabled(true);
	        break;
	      case 0:
	      default:
	        mCurrentInterpolator = new AccelerateInterpolator(mFactor);
	        mSeekBarFactor.setEnabled(true);
	        break;
	    }
	    updateValues();
	}
	
	private void setValues() {
		((ColorArrayAdapterC) mColorsListView.getAdapter()).notifyDataSetChanged();

		switch (mSpinnerStyle.getSelectedItemPosition()) {
		case 0:
			mCurrentStyle = Style.ROUNDED;
			break;
		case 1:
			mCurrentStyle = Style.NORMAL;
			break;
		}
		
		updateValues();
	}
	
	private void updateValues() {
		CircularProgressDrawable circularProgressDrawable;
		CircularProgressDrawable.Builder b = new CircularProgressDrawable
			.Builder(fa)
			.colors(mSettingsHelper.getCircularBarColors())
			.sweepSpeed(mSpeed)
			.rotationSpeed(mSpeed)
			.strokeWidth(dpToPx(mStrokeWidth));
		if (mCurrentStyle != null)
			b.style(mCurrentStyle);
		if (mCurrentInterpolator != null)
			b.sweepInterpolator(mCurrentInterpolator);
		
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
			mSettingsHelper.setCSpeed(mSpeed).setCStrokeWidth(mStrokeWidth).setCFactor(mFactor);
			mSettingsHelper.setCircularBarColor(mColor);
			mSettingsHelper.setStyle(mCurrentStyle);
			mSettingsHelper.setCircularBarInterpolator(mCurrentInterpolator);
			Toast.makeText(fa, getString(R.string.item_successfully_saved), Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}