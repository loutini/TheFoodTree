package com.fwa.thefoodtree;

import android.app.Fragment;
import android.app.FragmentBreadCrumbs;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fwa.thefoodtree.activity.common.FTActivity;
import com.fwa.thefoodtree.fragments.DailyReportFragment;
import com.fwa.thefoodtree.fragments.LogIngredientsFragment;
import com.fwa.thefoodtree.fragments.LogMenuFragment;

/* This is our Main Activity, launch point for the app */

public class App extends FTActivity {
	
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private String mDrawerTitle;
    private String mTitle;
    private String[] mMenuItemTitles;
    
    private FragmentBreadCrumbs mFragmentBreadCrumbs;   

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app);
		
		mTitle = mDrawerTitle = getTitle().toString();
        mMenuItemTitles = getResources().getStringArray(R.array.menu_item_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.navigation_drawer_list_item, mMenuItemTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
            	setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        mFragmentBreadCrumbs = (FragmentBreadCrumbs) findViewById(R.id.breadcrumbs);
        mFragmentBreadCrumbs.setActivity(this);  

        if (savedInstanceState == null) {
            selectItem(0);
        }
        
              
	}
	
	public void setTitle(String title) {
		this.setActionBarTitle(title);
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	clearBackStack();
            selectItem(position);
        }
    }

    private void selectItem(int position) {
    	
    	/* What kind of fragment do we want to display? */
    	Fragment fragment = this.chooseFragment(position);
    	FragmentManager fragmentManager = getFragmentManager();
    	fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    	
        /* update selected item and title, then close the drawer */
    	String title = mMenuItemTitles[position];
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
        this.setTitle(title);
        
        /* update the breadcrumbs */
        this.updateBreadCrumbs(mFragmentBreadCrumbs, title);
    }
   
    
    private Fragment chooseFragment(int pos) {

	    Fragment fragment = null;
	    Bundle args = new Bundle();
	    args.putInt(LogIngredientsFragment.ARG_MENU_ITEM, pos);	    
	    if(pos == 0) {
	    	//Log ingredients
	    	fragment = new LogIngredientsFragment();
	    }
	    else if(pos == 1) {
	    	//Log from menu
	    	 fragment = new LogMenuFragment();
	    }
	    else if(pos == 2) {
	    	//Daily report
	    	fragment = new DailyReportFragment();
	    }
	    args.putInt(DailyReportFragment.ARG_MENU_ITEM, pos);
	    fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}
