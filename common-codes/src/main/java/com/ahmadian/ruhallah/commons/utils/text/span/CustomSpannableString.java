package com.ahmadian.ruhallah.commons.utils.text.span;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.*;

import com.ahmadian.ruhallah.commons.utils.text.Typefaces;

/**
 * Created by rahmadian on 24/10/2016.
 */
public class CustomSpannableString extends SpannableString {

    public static CustomSpannableString getInstance(CustomSpannableStringConstructor builder) {
        return new CustomSpannableString(builder);
    }

    public CustomSpannableString(CustomSpannableStringConstructor builder) {
        super(builder.getText());
        if (builder.getTypeface() != null)
            this.setSpan(new CustomTypefaceSpan("", builder.getTypeface()), 0, this.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        else
            this.setSpan(new CustomTypefaceSpan("", Typefaces.get(builder.getContext(), null)), 0, this.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (builder.getRelativeSize() != null)
            this.setSpan(new RelativeSizeSpan(builder.getRelativeSize()), 0, this.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (builder.getForegroundColor() != null)
            this.setSpan(new ForegroundColorSpan(builder.getForegroundColor()), 0, this.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (builder.getBackgroundColor() != null)
            setSpan(new BackgroundColorSpan(builder.getBackgroundColor()), 0, this.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (builder.getUnderline())
            setSpan(new UnderlineSpan(), 0, this.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (builder.getBold())
            setSpan(new StyleSpan(Typeface.BOLD), 0, this.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (builder.getItalic())
            setSpan(new StyleSpan(Typeface.ITALIC), 0, this.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }


}
