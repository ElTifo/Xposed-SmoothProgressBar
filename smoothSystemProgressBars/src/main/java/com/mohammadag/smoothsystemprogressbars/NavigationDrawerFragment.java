package com.mohammadag.smoothsystemprogressbars;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment implements NavigationDrawerCallbacks {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private RecyclerView mDrawerList;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    @SuppressWarnings("unused")
	private boolean mFromSavedInstanceState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
    	View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    	
    	mDrawerList = (RecyclerView) view.findViewById(R.id.drawerList);
    	mDrawerList.setHasFixedSize(true);
    	
    	LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    	
        mDrawerList.setLayoutManager(layoutManager);
        mDrawerList.setItemAnimator(new DefaultItemAnimator());
        
        final List<NavigationItem> navigationItems = getMenu();
        
        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(navigationItems);
        
        adapter.setNavigationDrawerCallbacks(this);
        
        mDrawerList.setAdapter(adapter);
        
        mDrawerList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
        .color(Color.LTGRAY)
        .visibilityProvider(adapter)
        .build());
        
        selectItem(mCurrentSelectedPosition);
        
        return view;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public ActionBarDrawerToggle getActionBarDrawerToggle() {
        return mActionBarDrawerToggle;
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
    	selectItem(position);
    }
    
	@SuppressWarnings("deprecation")
	public List<NavigationItem> getMenu() {
        List<NavigationItem> items = new ArrayList<NavigationItem>();
        items.add(new NavigationItem(getString(R.string.pref_enable_horizontal_title), getResources().getDrawable(R.drawable.ic_menu_h)));
        items.add(new NavigationItem(getString(R.string.pref_enable_circular_title), getResources().getDrawable(R.drawable.ic_menu_c)));
        items.add(new NavigationItem(getString(R.string.support), getResources().getDrawable(R.drawable.ic_menu_xda)));
        items.add(new NavigationItem(getString(R.string.donate), getResources().getDrawable(R.drawable.ic_menu_pp)));
        items.add(new NavigationItem(getString(R.string.prefs), getResources().getDrawable(R.drawable.ic_menu_prefs)));
        return items;
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     * @param toolbar      The Toolbar of the activity.
     */
    @SuppressWarnings("deprecation")
	public void setup(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.ColorPrimaryDark));

        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) return;

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) return;

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
        ((NavigationDrawerAdapter) mDrawerList.getAdapter()).selectPosition(position);
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(mFragmentContainerView);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }

    @SuppressWarnings("deprecation")
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void setUserData(String user, String email, Bitmap avatar) {
        ImageView avatarContainer = (ImageView) mFragmentContainerView.findViewById(R.id.imgAvatar);
        ((TextView) mFragmentContainerView.findViewById(R.id.txtUserEmail)).setText(email);
        ((TextView) mFragmentContainerView.findViewById(R.id.txtUsername)).setText(user);
        avatarContainer.setImageDrawable(new RoundImage(avatar));
    }

    public View getGoogleDrawer() {
        return mFragmentContainerView.findViewById(R.id.googleDrawer);
    }

    public static class RoundImage extends Drawable {
        private final Bitmap mBitmap;
        private final Paint mPaint;
        private final RectF mRectF;
        private final int mBitmapWidth;
        private final int mBitmapHeight;

        public RoundImage(Bitmap bitmap) {
            mBitmap = bitmap;
            mRectF = new RectF();
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
            final BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mPaint.setShader(shader);

            mBitmapWidth = mBitmap.getWidth();
            mBitmapHeight = mBitmap.getHeight();
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawOval(mRectF, mPaint);
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            mRectF.set(bounds);
        }

        @Override
        public void setAlpha(int alpha) {
            if (mPaint.getAlpha() != alpha) {
                mPaint.setAlpha(alpha);
                invalidateSelf();
            }
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            mPaint.setColorFilter(cf);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        @Override
        public int getIntrinsicWidth() {
            return mBitmapWidth;
        }

        @Override
        public int getIntrinsicHeight() {
            return mBitmapHeight;
        }

        public void setAntiAlias(boolean aa) {
            mPaint.setAntiAlias(aa);
            invalidateSelf();
        }

        @Override
        public void setFilterBitmap(boolean filter) {
            mPaint.setFilterBitmap(filter);
            invalidateSelf();
        }

        @Override
        public void setDither(boolean dither) {
            mPaint.setDither(dither);
            invalidateSelf();
        }

        public Bitmap getBitmap() {
            return mBitmap;
        }

    }
}
