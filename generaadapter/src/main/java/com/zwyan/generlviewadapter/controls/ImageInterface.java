package com.zwyan.generlviewadapter.controls;


import com.zwyan.generlviewadapter.adapter.ViewHolder;

/**
 * imgae类，用来定义Image类型控件的方法
 * 
 * @author zw.yan
 * 
 */
public interface ImageInterface {
	/**
	 * 设置ImageView的图片资源
	 * 
	 * @param viewId
	 *            控件ID
	 * @param drawableId
	 *            资源ID
	 * @return
	 */
	public ViewHolder setImageViewByResource(int viewId, int drawableId);

	/**
	 * 设置ImageButton的图片资源
	 * 
	 * @param viewId
	 *            控件ID
	 * @param drawableId
	 *            资源ID
	 * @return
	 */
	public ViewHolder setImageButtonByResource(int viewId, int drawableId);

	/**
	 * 通过URL设置ImageView的图片资源
	 * 
	 * @param viewId
	 * @param url
	 *            URL地址
	 * @return
	 */
	public ViewHolder setImageViewByUrl(int viewId, String url);
}
