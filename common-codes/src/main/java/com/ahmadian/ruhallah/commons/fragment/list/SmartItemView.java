package com.ahmadian.ruhallah.commons.fragment.list;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ahmadian.ruhallah.commons.R;
import com.ahmadian.ruhallah.commons.utils.text.StringUtils;
import com.ahmadian.ruhallah.commons.utils.text.TextUtils;

/**
 * Created by hsiami on 19/11/2016.
 */
public class SmartItemView<T> extends FrameLayout implements View.OnClickListener {

    protected T item;
    protected String searchPhrase = "";

    public String getSearchPhrase() {
        return searchPhrase;
    }

    public void setSearchPhrase(String searchPhrase) {
        this.searchPhrase = searchPhrase;
    }

    private OnItemClickListener<T> onItemClickListener;

    public SmartItemView(Context context) {
        super(context);
        setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setItem(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if(onItemClickListener != null) {
            onItemClickListener.click(item);
        }
    }


    protected void highlight(TextView txtView, CharSequence text) {
        if(!StringUtils.isEmpty(searchPhrase)) {
            String[] splits = searchPhrase.split(" ");
            Spanned highlightedSpan = new SpannableString(text);
            for (String phrase : splits) {
                highlightedSpan = TextUtils.find(highlightedSpan, phrase,
                        ContextCompat.getColor(getContext(), R.color.yellow_400));
            }
            txtView.setText(highlightedSpan);
        } else {
            txtView.setText(text);
        }
    }

    public interface OnItemClickListener<T> {
        void click(T item);
    }
}
