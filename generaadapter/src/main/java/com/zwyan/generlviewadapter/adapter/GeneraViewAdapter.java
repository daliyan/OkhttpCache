package com.zwyan.generlviewadapter.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 通用的適配器
 * 
 * @param <T>
 */
public abstract class GeneraViewAdapter<T> extends BaseAdapter {
	private Context mContext;
	/**
	 * 自定义列表布局
	 */
	protected LayoutInflater mInflater;
	/**
	 * 列表布局ID
	 */
	private final int mItemLayoutId;
	/**
	 * 列表数据
	 */
	private List<T> mDataList;

	/**
	 * 初始化适配器数据
	 * 
	 * @param mContext
	 * @param mDataList
	 *            需要加载列表的数据
	 * @param mItemLayoutId
	 *            item自定义列表id
	 */
	public GeneraViewAdapter(Context mContext, List<T> mDataList,
			int mItemLayoutId) {
		this.mContext = mContext;
		this.mDataList = mDataList;
		this.mItemLayoutId = mItemLayoutId;
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mDataList.size();
	}

	@Override
	public T getItem(int position) {
		return mDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder = getViewHolder(position, convertView,
				parent);
		convert(viewHolder, getItem(position));
		return viewHolder.getConvertView();

	}

	private ViewHolder getViewHolder(int position, View convertView,
			ViewGroup parent) {
		return ViewHolder.get(mContext, convertView, parent, mItemLayoutId,
				position);
	}

	/**
	 * 交給子類進行實現，主要是給控件賦值，調用ViewHolder定義好的方法進行賦值
	 * 
	 * @param viewHolder
	 *            當前的ViewHolder
	 * @param item
	 *            當前的子項
	 */
	public abstract void convert(ViewHolder viewHolder, T item);

}
