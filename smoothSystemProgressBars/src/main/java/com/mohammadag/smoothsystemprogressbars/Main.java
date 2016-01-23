package com.mohammadag.smoothsystemprogressbars;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class Main extends AppCompatActivity
        implements NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer
        mNavigationDrawerFragment.setUserData("Typhus_", "tifo.el@gmail.com", BitmapFactory.decodeResource(getResources(), R.drawable.avatar));
        
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
    	Intent intent;
    	Fragment fr;
    	switch (position) {
    	case 0:
    		fr = new SettingsActivity();
    		getFragmentManager().beginTransaction().replace(R.id.container, fr, "Horizontal MOD").commit();
    		break;
    	case 1:
    		fr = new SettingsActivityC();
    		getFragmentManager().beginTransaction().replace(R.id.container, fr, "Circular MOD").commit();
    		break;
    	case 2:
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://forum.xda-developers.com/showthread.php?t=2556752"));
            startActivity(intent);
            break;
    	case 3:
    		intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=3HGQY9Q5VGKG6"));
    		startActivity(intent);
    		break;
    	case 4:
    		fr = new SmoothSystemPrefs();
            getFragmentManager().beginTransaction().replace(R.id.container, fr, "Settings").commit();
    		break;
    	default:
    		break;
        }
    }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }
    
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.prefs) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
