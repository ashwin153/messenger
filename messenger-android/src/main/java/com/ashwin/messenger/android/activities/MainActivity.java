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

	// These arrays contain the icons for each tab when they are pressed or not
	private static final int[] TAB_NORMAL  = new int[] { R.drawable.ic_action_social_group_normal, R.drawable.ic_action_social_chat_normal, R.drawable.ic_action_action_settings_normal };
	private static final int[] TAB_PRESSED = new int[] { R.drawable.ic_action_social_group_pressed, R.drawable.ic_action_social_chat_pressed, R.drawable.ic_action_action_settings_pressed };
	
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

        _actionBar.addTab(_actionBar.newTab().setIcon(TAB_PRESSED[0]).setTabListener(this));
        for(int i = 1; i < TAB_NORMAL.length; i++)
        	_actionBar.addTab(_actionBar.newTab().setIcon(TAB_NORMAL[i]).setTabListener(this));
        
        _viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            	for(int i = 0; i < TAB_NORMAL.length; i++)
            		if(i == position)
            			_actionBar.getTabAt(i).setIcon(TAB_PRESSED[i]);
            		else
            			_actionBar.getTabAt(i).setIcon(TAB_NORMAL[i]);
            				
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

