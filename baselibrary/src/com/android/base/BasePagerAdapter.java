package com.android.base;

import java.util.List;

import com.android.pojo.Pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * ViewPage数据数据源
 * @author sunjuncai
 *
 */
public class BasePagerAdapter extends FragmentPagerAdapter{
	
	/** 页面列表  */
	private List<Pager> pagerList;

	public BasePagerAdapter(FragmentManager fm, List<Pager> pagerList) {
		super(fm);
		this.pagerList = pagerList;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return pagerList.get(position).getTitle();
	}

	@Override
	public Fragment getItem(int position) {
		return pagerList.get(position).getFragment();
	}
	
	@Override
	public int getCount() {
		return pagerList.size();
	}

}
