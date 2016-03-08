package com.android.utils;

import java.io.Serializable;

import android.text.TextUtils;

/**
 * 分页工具
 * @author sunjuncai
 *
 */
public class PageUtil implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** 总条数 */
	private int totalCount  = 0;
	
	/** 当前页码 */
	private int currentPage = 1;
	
	/** 分页条数 */
	private int limit = 0;
	
	/** 总页数 */
	private int pageCount = 0;
	
	/** 是否到达最后一页 */
	private boolean isLastPage = false;
	
	/** 开始记录数 */
	private int start = 0;
	
	/** 结束记录数 */
	private int end = 0;
	
	public PageUtil() {
		super();
	}

	public PageUtil(int totalCount, String currentPage, int limit) {
		super();
		this.totalCount = totalCount;
		if(TextUtils.isEmpty(currentPage)){
			this.currentPage = 1;
		}else{
			this.currentPage = Integer.parseInt(currentPage);
		}
		this.limit = limit;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	/**
	 * 是否为最后一页
	 */
	public boolean isLastPage() {
		boolean isLastPage = false;
		if(currentPage == pageCount-1 && totalCount != 0){
			isLastPage = true;
		}
		return isLastPage;
	}
	
	/**
	 * 初始化
	 */
	public void init() {
		totalCount = 0;
		currentPage = 0;
		pageCount = 0;
		start = 0;
		end = 0;
		isLastPage = false;
	}

	/**
	 * 翻页
	 */
	public void nextPage(){
		//最后一页不加载
		//总数为0不加载
		if(!isLastPage && totalCount != 0){
			currentPage++;
		}
	}
	
	/**
	 * 重置统计总页数
	 */
	public void reset(){
		if(limit != 0){
			int pageCount = totalCount/limit;
			if(totalCount%limit != 0){
				pageCount++;
			}
			setPageCount(pageCount);
			int start = (currentPage-1) * limit;
			int end = start + limit;
			setStart(start);
			setEnd(end);
		}
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

}
