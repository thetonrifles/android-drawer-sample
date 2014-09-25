package com.thetonrifles.drawersample.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.thetonrifles.drawersample.R;

public class NavigationMenu extends FrameLayout {

	private Listener mListener;

	public NavigationMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public NavigationMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public NavigationMenu(Context context) {
		super(context);
		init(context, null);
	}

	private void init(Context context, AttributeSet attrs) {
		final View layout = inflate(context, R.layout.view_drawer, null);

		(layout.findViewById(R.id.btn_drawer_1)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onOptionOne();
				}
			}
		});

		(layout.findViewById(R.id.btn_drawer_2)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onOptionTwo();
				}
			}
		});

		(layout.findViewById(R.id.btn_drawer_3)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onOptionThree();
				}
			}
		});

		addView(layout);
	}

	public void setListener(Listener listener) {
		mListener = listener;
	}

	public static interface Listener {

		public void onOptionOne();

		public void onOptionTwo();

		public void onOptionThree();

	}

}
