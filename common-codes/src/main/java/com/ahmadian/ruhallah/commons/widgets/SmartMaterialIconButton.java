package com.ahmadian.ruhallah.commons.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.ahmadian.ruhallah.commons.utils.text.Typefaces;

/**
 * Created by rahmadian on 25/09/2016.
 */
public class SmartMaterialIconButton extends Button {

    private static final String font = "material_icons.ttf";

    public SmartMaterialIconButton(Context context) {
        super(context);
        init();
    }

    public SmartMaterialIconButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartMaterialIconButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (isInEditMode())
            return;
        this.setTypeface(Typefaces.get(getContext(), font));
    }
}
