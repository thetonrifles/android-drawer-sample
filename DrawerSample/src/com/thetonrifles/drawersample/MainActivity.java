package com.thetonrifles.drawersample;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.thetonrifles.drawersample.controls.NavigationMenu;
import com.thetonrifles.drawersample.fragments.BaseFragment;
import com.thetonrifles.drawersample.fragments.MapFragment;

public class MainActivity extends LocationActivity {

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ActionBar mActionBar;
	private BaseFragment mFragmentCurrent;
	private NavigationMenu mNavigationMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initializing drawer and action bar
		mActionBar = getActionBar();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_navigation_drawer, R.string.drawer_opened, R.string.drawer_closed) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mActionBar.setDisplayOptions(
				ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowTitleEnabled(true);

		// Initializing navigation menu
		mNavigationMenu = (NavigationMenu) findViewById(R.id.navigation_menu);
		mNavigationMenu.setListener(new NavigationMenuListener());
	}

	@Override
	public void onConnected(Bundle dataBundle) {
		super.onConnected(dataBundle);
		// Opening map fragment
		if (mFragmentCurrent == null) {
			mFragmentCurrent = MapFragment.newInstance(getCurrentLocation());
			openCurrentFragment(false, false);
		}
	}

	/**
	 * Shows current fragment within the activity, handling transition through
	 * animations, depending on provided parameter <em>showAnimation</em>
	 * 
	 * @param showAnimation
	 *            Transition should implement animations
	 * @param addToBackStack
	 *            Add fragment to back stack
	 */
	protected void openCurrentFragment(boolean showAnimation, boolean addToBackStack) {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		if (showAnimation) {
			ft.setCustomAnimations(
					android.R.animator.fade_in, android.R.animator.fade_out,
					android.R.animator.fade_in, android.R.animator.fade_out);
		}
		ft.replace(R.id.main_content_frame, mFragmentCurrent);
		if (addToBackStack) {
			ft.addToBackStack(null);
		}
		ft.commit();
	}

	/**
	 * Support method for drawer toggle
	 */
	private void toggleDrawer() {
		if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
			mDrawerLayout.closeDrawer(Gravity.LEFT);
		} else {
			mDrawerLayout.openDrawer(Gravity.LEFT);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggleDrawer();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private class NavigationMenuListener implements NavigationMenu.Listener {

		@Override
		public void onOptionOne() {
			updateFragment(MapFragment.newInstance(getCurrentLocation()));
		}

		@Override
		public void onOptionTwo() {
			Toast.makeText(MainActivity.this, "To Be Implemented", Toast.LENGTH_SHORT).show();
			toggleDrawer();
		}

		@Override
		public void onOptionThree() {
			Toast.makeText(MainActivity.this, "To Be Implemented", Toast.LENGTH_SHORT).show();
			toggleDrawer();
		}

		/**
		 * Support method for updating current fragment
		 */
		private void updateFragment(BaseFragment fragment) {
			if (!mFragmentCurrent.same(fragment)) {
				mFragmentCurrent = fragment;
				openCurrentFragment(true, true);
			}
			toggleDrawer();
		}

	}

}
