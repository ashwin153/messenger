package com.ashwin.messenger.android.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ashwin.messenger.android.fragments.DirectoryFragment;
import com.ashwin.messenger.android.fragments.MessengerFragment;
import com.ashwin.messenger.android.fragments.ProfileFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

	public TabPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	@Override
	public Fragment getItem(int index) {
		switch(index) {
			case 0: return new DirectoryFragment();
			case 1: return new MessengerFragment();
			case 2: return new ProfileFragment();
		}
		
		return null;
	}

	@Override
	public int getCount() {
		return 3;
	}

}
