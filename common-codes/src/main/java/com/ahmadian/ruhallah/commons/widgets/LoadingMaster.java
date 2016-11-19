package com.ahmadian.ruhallah.commons.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ahmadian.ruhallah.commons.R;

public class LoadingMaster extends FrameLayout implements View.OnClickListener {

    private Context mContext = null;

    private LinearLayout mLoadingLayout = null;
    private NestedScrollView mFailedLayout = null;
    private View mTranslucentView = null;
    private SmartCircularProgressBar circularProgressBar;

    private View mContentView = null;

    private OnRetryListener mOnRetryListener = null;

    private String mTranslucentColor = "#bbffffff";

    public LoadingMaster(Context context) {
        this(context, null);
    }

    public LoadingMaster(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingMaster(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        View.inflate(mContext, R.layout.layout_loading_master, this);
//        mContentView = new View(mContext);

        mLoadingLayout = (LinearLayout) findViewById(R.id.loadingLayout);
        mFailedLayout = (NestedScrollView) findViewById(R.id.failedLayout);
        mTranslucentView = findViewById(R.id.translucentLayout);
        mTranslucentView.setBackgroundColor(Color.parseColor(mTranslucentColor));
        mTranslucentView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        circularProgressBar = (SmartCircularProgressBar) findViewById(R.id.circleProgressBar);

        Button btnRetry = (Button) findViewById(R.id.btnRetry);
        btnRetry.setOnClickListener(this);

        TextView txtLoading = (TextView) findViewById(R.id.loadingMessage);
        TextView txtFailed = (TextView) findViewById(R.id.failedMessage);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingMaster, defStyle, 0);
        int colorsId = a.getResourceId(R.styleable.LoadingMaster_circular_progress_colors, 0);
        if(colorsId != 0) {
            int[] colors = getResources().getIntArray(colorsId);
            circularProgressBar.setColors(colors);
        }
    }

    public void setContentView(int res) {
        setContentView(View.inflate(mContext, res, null));
    }

    public void setContentView(View view) {
        mContentView = view;
        addView(mContentView);
        hideAll();
    }

    public boolean loadingStart() {
        return loadingStart(false, false);
    }

    public boolean loadingStart(boolean showContent, boolean showTranslucent) {
        if (showContent) {
            show(mContentView);
        } else {
            hide(mContentView);
        }

        if (showTranslucent) {
            show(mTranslucentView);
        } else {
            hide(mTranslucentView);
        }

        hide(mFailedLayout);
        show(mLoadingLayout);
        mContentView.setEnabled(false);
        return true;
    }

    public boolean loadingEnd() {
        show(mContentView);
        hide(mFailedLayout);
        hide(mLoadingLayout);
        hide(mTranslucentView);
        mContentView.setEnabled(true);
        return true;
    }

    public boolean loadingFailed(boolean showContent) {
        if (showContent) {
            show(mContentView);
            show(mTranslucentView);
        } else {
            hide(mContentView);
            hide(mTranslucentView);
        }
        show(mFailedLayout);
        hide(mLoadingLayout);
        mContentView.setEnabled(false);
        return true;
    }

    public boolean isLoading() {
        return mLoadingLayout.getVisibility() == View.VISIBLE;
    }

    public void setTranslucentColor(String hexColor) {
        mTranslucentColor = hexColor;
        mTranslucentView.setBackgroundColor(Color.parseColor(mTranslucentColor));
    }

    public void setLoadingMessage(String msg) {
        ((TextView) findViewById(R.id.loadingMessage)).setText(msg);
    }

    public void setFailedMessage(String msg) {
        ((TextView) findViewById(R.id.failedMessage)).setText(msg);
    }

    public void setRetryText(String text) {
        ((Button) findViewById(R.id.btnRetry)).setText(text);
    }

    public void setOnRetryListener(OnRetryListener listener) {
        mOnRetryListener = listener;
    }

    public void setProgressColors(int colorsId) {
        int[] colors = getResources().getIntArray(colorsId);
        circularProgressBar.setColors(colors);
    }

    //========================

    private void hide(View view) {
        view.setVisibility(View.GONE);
    }

    private void show(View view) {
        view.setVisibility(View.VISIBLE);
    }

    private void hideAll() {
//        hide(mContentView);
        hide(mFailedLayout);
        hide(mLoadingLayout);
        hide(mTranslucentView);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRetry && mOnRetryListener != null) {
            mOnRetryListener.onRetry();
        }
    }

    //============================

    public interface OnRetryListener {
        void onRetry();
    }

}
