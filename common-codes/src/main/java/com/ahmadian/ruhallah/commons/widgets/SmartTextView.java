package com.ahmadian.ruhallah.commons.widgets.widgets;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.TextView;

import com.ahmadian.ruhallah.commons.R;
import com.ahmadian.ruhallah.commons.application.CommonsConfiguration;

/**
 * Created by ruhallah-PC on 10/3/2016.
 */

public class SmartTextView extends TextView {

    public SmartTextView(Context context) {
        super(context);
        init();
    }

    public SmartTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public SmartTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (isInEditMode())
            return;

        this.setTypeface(CommonsConfiguration.getTypeface(R.string.app_name));
    }

}
