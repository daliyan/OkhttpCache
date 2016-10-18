package com.zwyan.generlviewadapter.controls;

import com.zwyan.generlviewadapter.adapter.ViewHolder;

/**
 * 定义text文本类的方法
 * 
 * @author YZW
 * 
 */
public interface TextInterface {
	/**
	 * 设置TextView显示文本
	 * 
	 * @param viewId
	 * @param text
	 *            要设置的文本字符
	 * @return
	 */
	public ViewHolder setTextViewString(int viewId, String text);

	/**
	 * 设置EditText显示文本
	 * 
	 * @param viewId
	 * @param text
	 *            要设置的文本字符
	 * @return
	 */
	public ViewHolder setEditTextString(int viewId, String text);

	/**
	 * 设置TextView的颜色
	 * 
	 * @param viewId
	 * @param colorId
	 *            颜色ID
	 * @return
	 * @throws ClassCastException
	 */
	public ViewHolder setTextViewColor(int viewId, int colorId);
}
