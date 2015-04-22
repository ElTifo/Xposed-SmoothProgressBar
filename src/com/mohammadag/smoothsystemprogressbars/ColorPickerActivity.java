package com.mohammadag.smoothsystemprogressbars;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.ColorPicker.OnColorChangedListener;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;
import com.mohammadag.smoothsystemprogressbars.MaterialRippleLayout;

public class ColorPickerActivity extends ActionBarActivity implements OnColorChangedListener, NavigationDrawerCallbacks {

	private EditText editText;
	private String prefColor;
	private int mPosition;

	private Toolbar toolbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_color_picker);
		
		toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		Bundle bundle = getIntent().getExtras();
		String prefTitle = bundle.getString("title");
		mPosition = bundle.getInt("position");
		prefColor = bundle.getString("color");

		setTitle(prefTitle);

		final ColorPicker picker = (ColorPicker) findViewById(R.id.picker);
		OpacityBar opacityBar = (OpacityBar) findViewById(R.id.opacitybar);
		SaturationBar saturationBar = (SaturationBar) findViewById(R.id.saturationbar);
		ValueBar valueBar = (ValueBar) findViewById(R.id.valuebar);

		picker.addOpacityBar(opacityBar);
		picker.addSaturationBar(saturationBar);
		picker.addValueBar(valueBar);

		editText = (EditText) findViewById(R.id.edittext);

		picker.setOldCenterColor(Color.parseColor("#" + prefColor));
		picker.setOnColorChangedListener(this);
		picker.setColor(Color.parseColor("#" + prefColor));

		Button bPreview = (Button) findViewById(R.id.bPreviewColor);
		
		MaterialRippleLayout.on(bPreview)
		.rippleBackground(Color.parseColor("#ff5a595b"))
    	.rippleColor(Color.parseColor("#" + prefColor))	// color of ripple
    	.rippleAlpha(0.5f)
    	.rippleHover(true)
    	.rippleOverlay(false)
    	.create();
		
		bPreview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					String textEditString = editText.getText().toString();
					int colourHex;
					if (isFullyTransparent(textEditString)) {
						colourHex = Color.parseColor("#00000000");
					} else {
						colourHex = Color.parseColor("#" + textEditString);
						picker.setColor(colourHex);
					}

					ColorDrawable previewDrawable = new ColorDrawable(colourHex);

					getSupportActionBar().setBackgroundDrawable(previewDrawable);

					/* Workaround, there's no invalidate() method that would redraw the
					 * action bar, and setting the drawable at runtime simply does nothing.
					 */
					getSupportActionBar().setDisplayShowTitleEnabled(false);
					getSupportActionBar().setDisplayShowTitleEnabled(true);

				} catch (IllegalArgumentException e) {
					Toast.makeText(getApplicationContext(), R.string.invalid_color, Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		Button bApply = (Button) findViewById(R.id.bApplyColor);
		MaterialRippleLayout.on(bApply)
		.rippleBackground(Color.parseColor("#ff5a595b"))
    	.rippleColor(Color.parseColor("#" + prefColor))
    	.rippleAlpha(0.5f)
    	.rippleHover(true)
    	.rippleOverlay(false)
    	.create();
		bApply.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					String textEditString = editText.getText().toString();
					int colourHex;
					if (isFullyTransparent(textEditString)) {
						colourHex = Color.parseColor("#00000000");
					} else {
						colourHex = Color.parseColor("#" + textEditString);
						picker.setColor(colourHex);
					}

					returnResults();

				} catch (IllegalArgumentException e) {
					Toast.makeText(getApplicationContext(), R.string.invalid_color, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private boolean isFullyTransparent(String colorCode) {
		return "0".equals(colorCode) || colorCode.startsWith("00");
	}

	private void returnResults() {
		String text = editText.getText().toString();
		
		Intent intent = new Intent();
		intent.putExtra("position", mPosition);
		if (isFullyTransparent(text)) {
			text = "00000000";
		}
		intent.putExtra("color", text);
		//intent.putExtra("enabled", enabledSwitch.isChecked());
		
		super.setResult(ActionBarActivity.RESULT_OK, intent);
		finish();
	}

	private void updateEdittext(String color) {
		if (isFullyTransparent(color)) {
			editText.setText("00000000");
			return;
		}
		editText.setText(color);
	}

	@Override
	public void onColorChanged(int color) {
		updateEdittext(Integer.toHexString(color));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		setResult(ActionBarActivity.RESULT_CANCELED);
		super.onBackPressed();
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// TODO Auto-generated method stub
		
	}
}
