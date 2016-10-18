package com.zwyan.generlviewadapter.adapter;


import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwyan.generlviewadapter.controls.ImageInterface;
import com.zwyan.generlviewadapter.controls.TextInterface;
import com.zwyan.util.ImageLoaderTool;

/**
 * ViewHolder對象能夠適配ListView和Grid對象
 * 
 * @author zw.yan
 * 
 */
public class ViewHolder implements ImageInterface, TextInterface {
	/**
	 * 存儲Item的控件對象
	 */
	private final SparseArray<View> mViews;
	/**
	 * 當前的view對象，也就是每個位置的item對象
	 */
	private View mConvertView;
	/**
	 * 加在網絡圖片的工具
	 */
	private ImageLoaderTool imageLoaderTool;
	Context mContext;

	/**
	 * 初始化ViewHolder對象信息
	 * 
	 * @param context
	 * @param parent
	 * @param layoutId
	 * @param position
	 */
	public ViewHolder(Context context, ViewGroup parent, int layoutId,
			int position) {
		this.mContext = context;
		imageLoaderTool = new ImageLoaderTool(context);
		mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		mConvertView.setTag(this);
	}

	/**
	 * 返回當前位置的ViewHolder對象
	 * 
	 * @param context
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @param position
	 * @return
	 */
	public static ViewHolder get(Context context, View convertView,
			ViewGroup parent, int layoutId, int position) {
		if (convertView == null) {
			return new ViewHolder(context, parent, layoutId, position);
		}
		return (ViewHolder) convertView.getTag();
	}

	/**
	 * 得到對應id的控件
	 * 
	 * @param viewId
	 * @return <T extends View>對象
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		// 判斷是否添加過該控件對象
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	/**
	 * 返回mConvertView對象
	 * 
	 * @return view
	 */
	public View getConvertView() {
		return mConvertView;
	}

	@Override
	public ViewHolder setTextViewString(int viewId, String text) {
		View a = getView(viewId);
		if (a instanceof TextView) {
			TextView view = (TextView) a;
			view.setText(text);
			return this;
		} else {
			throw new ClassCastException(viewId
					+ " is not a TextView so can't setTextViewString");
		}

	}

	@Override
	public ViewHolder setImageViewByResource(int viewId, int drawableId) {
		View a = getView(viewId);
		if (a instanceof ImageView) {
			ImageView view = (ImageView) a;
			view.setImageResource(drawableId);
			return this;
		} else {
			throw new ClassCastException(viewId
					+ " is not a ImageView so can't setImageViewByResource");
		}

	}

	@Override
	public ViewHolder setImageViewByUrl(int viewId, String url) {
		View a = getView(viewId);
		if (a instanceof ImageView) {
			imageLoaderTool.loadImageView(url, (ImageView) a);
			return this;
		} else {
			throw new ClassCastException(
					viewId
							+ " is not a setImageViewByResource so can't setImageViewByUrl");
		}
	}

	@Override
	public ViewHolder setEditTextString(int viewId, String text) {
		View a = getView(viewId);
		if (a instanceof EditText) {
			EditText view = (EditText) a;
			view.setText(text);
			return this;
		} else {
			throw new ClassCastException(viewId
					+ " is not a EditText so can't setEditTextString");
		}

	}

	@Override
	public ViewHolder setImageButtonByResource(int viewId, int drawableId) {
		View a = getView(viewId);
		if (a instanceof ImageButton) {
			ImageButton view = (ImageButton) a;
			view.setImageResource(drawableId);
			return this;
		} else {
			throw new ClassCastException(viewId
					+ " is not a ImageButton so can't setImageButtonByResource");
		}

	}

	@Override
	public ViewHolder setTextViewColor(int viewId, int colorId) {
		View a = getView(viewId);
		if (a instanceof TextView) {
			TextView view = (TextView) a;
			view.setTextColor(mContext.getResources().getColor(colorId));
			return this;
		} else {
			throw new ClassCastException(viewId
					+ " is not a TextView so can't setTextViewColor");
		}

	}

}
