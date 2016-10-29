package com.ahmadian.ruhallah.commons.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.support.v7.widget.Toolbar;

import com.ahmadian.ruhallah.commons.R;
import com.ahmadian.ruhallah.commons.utils.text.span.CustomSpannableStringConstructor;

/**
 * Created by ruhallah-PC on 10/3/2016.
 */

public class SmartToolbar extends Toolbar {

    public SmartToolbar(Context context) {
        super(context);
    }

    public SmartToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTitle(@StringRes int resId) {
        setTitle(getResources().getString(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        if (title instanceof SpannableString) {
            super.setTitle(title);
        }else {
            SpannableString spannableString = new CustomSpannableStringConstructor(getContext())
                    .setText(title.toString())
                    .setRelativeSize(0.8f)
                    .build();
            super.setTitle(spannableString);
        }
    }

    @Override
    public void setSubtitle(@StringRes int resId) {
        setSubtitle(getResources().getString(resId));
    }

    @Override
    public void setSubtitle(CharSequence subtitle) {
        if (subtitle instanceof SpannableString) {
            super.setSubtitle(subtitle);
        }else {
            SpannableString spannableString = new CustomSpannableStringConstructor(getContext())
                    .setText(subtitle.toString())
                    .setRelativeSize(0.8f)
                    .setForegroundColor(getResources().getColor(android.R.color.tertiary_text_light))
                    .build();
            super.setSubtitle(spannableString);
        }
    }
}
