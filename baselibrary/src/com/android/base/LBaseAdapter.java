package com.android.base;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.utils.CommonUtil;


/**
 * 公共数据
 * @author sunjuncai
 * @param <T>
 */
public abstract class LBaseAdapter<T> extends BaseAdapter {

	/** 数据列表 */
	protected ArrayList<T> _List;
	
	/** 布局页面 */
	protected int _ResourceId;
	
	/** 当前位置  */
	protected int position = 0;
	
	/** 当前页面 */
	protected Context _context;
	
	/** 用于加载布局文件 */
	protected LayoutInflater inflater;
	
	/** 是否重写 */
	protected boolean override = false;
	
	public LBaseAdapter(Context _context, ArrayList<T> list, int resId) {
		this._List = list;
		this._context = _context;
		this._ResourceId = resId;
		if(null == _List){
			_List = new ArrayList<T>();
		}
		inflater = LayoutInflater.from(_context);
	}
	
	public List<T> get_List() {
		return _List;
	}

	public int getPosition() {
		return position;
	}

	@Override
	public int getCount() {
		if(CommonUtil.isNotNullCollect(_List)){
			return _List.size();
		}
		return 0;
	}
	
	public void deleteItem(int position) {
		if(CommonUtil.isNotNullCollect(_List)){
			_List.remove(position);
			this.notifyDataSetChanged();
		}
	}
	
	public void clearAll() {
		if(CommonUtil.isNotNullCollect(_List)){
			_List.clear();
			this.notifyDataSetChanged();
		}
	}

	public void add(List<T> beans) {
		if(CommonUtil.isNotNullCollect(_List)){
			_List.addAll(beans);
			this.notifyDataSetChanged();
		}
	}
	
	public void set(ArrayList<T> beans) {
		this._List = beans;
		this.notifyDataSetChanged();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public T getItem(int position) {
		if(CommonUtil.isNotNullCollect(_List) && position <= (getCount()-1) ){
			return _List.get(position);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(position > getCount()-1){
			position = 0;
		}
		this.position = position;
		if(override){
			return dealView(_context, _List, _ResourceId, position, convertView,parent);
		}else{
			BaseHolder<T> holder = null;
			if (convertView == null) {
				holder = getBaseHolder();
				convertView = inflater.inflate(_ResourceId, parent, false);;
				holder.initView(convertView);
				holder.bindComponentEvent();
				convertView.setTag(holder);
			} else {
				holder = (BaseHolder<T>) convertView.getTag();
			}
			T t = getItem(position);
			if(null != t){
				holder.initData(t);
			}
			return convertView;
		}
	}
	
	/**
	 * 可重
	 * @param _context 上下
	 * @param list 数据
	 * @param resId 布局文件
	 * @param position 位置
	 * @param convertView 
	 * @return
	 */
	public View dealView(Context _context, ArrayList<T> list, int resId, int position, View convertView,ViewGroup parent){
		return convertView;
	}
	
	public abstract BaseHolder<T> getBaseHolder();

}
