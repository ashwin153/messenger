package com.ashwin.messenger.android.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.ashwin.messenger.android.R;
import com.ashwin.messenger.android.adapters.TabPagerAdapter;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	private TabPagerAdapter _tabPagerAdapter;
	private ViewPager _viewPager;
	private ActionBar _actionBar;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        _tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        _viewPager = (ViewPager) findViewById(R.id.MainActivity_pager);
        _actionBar = getActionBar();
        
        _viewPager.setAdapter(_tabPagerAdapter);
        _actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        _actionBar.setDisplayShowTitleEnabled(false);
        _actionBar.setDisplayShowHomeEnabled(false);

        // tab.setIcon(R.drawable.tab_icon);
        String[] tabNames = new String[] {"Directory", "Messenger", "Profile"};
        for(String tabName : tabNames)
        	_actionBar.addTab(_actionBar.newTab().setText(tabName).setTabListener(this));
        
       _viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                _actionBar.setSelectedNavigationItem(position);
            }
 
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}
 
            @Override
            public void onPageScrollStateChanged(int arg0) {}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(com.ashwin.messenger.android.R.menu.main, menu);
		return true;
    }

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		_viewPager.setCurrentItem(tab.getPosition());	
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {}
	
}

