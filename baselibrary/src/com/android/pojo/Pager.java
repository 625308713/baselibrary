package com.android.pojo;

import com.android.base.LFragment;

/**
 * Pager数据对象
 * @author sunjuncai
 *
 */
public class Pager{

	/** 标题 */
	private String title;
	
	/** 页面对象 */
	private LFragment fragment;

	public Pager(String title, LFragment fragment) {
		super();
		this.title = title;
		this.fragment = fragment;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LFragment getFragment() {
		return fragment;
	}

	public void setFragment(LFragment fragment) {
		this.fragment = fragment;
	}
	
}
