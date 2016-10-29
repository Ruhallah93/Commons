package com.ahmadian.ruhallah.commons.utils.text.span;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by rahmadian on 24/10/2016.
 */
public interface IBuilderSpannableString {
    IBuilderSpannableString setText(String text);
    IBuilderSpannableString setText(int textId);
    IBuilderSpannableString setTypeFace(Typeface typeface);
    IBuilderSpannableString setRelativeSize(Float relativeSize);
    IBuilderSpannableString setForegroundColor(Integer foregroundColor);
    IBuilderSpannableString setBackgroundColor(Integer backgroundColor);
    IBuilderSpannableString setUnderline(Boolean underline);
    IBuilderSpannableString setBold(Boolean bold);
    IBuilderSpannableString setItalic(Boolean italic);
    String getText();
    Typeface getTypeface();
    Float getRelativeSize();
    Integer getForegroundColor();
    Integer getBackgroundColor();
    Boolean getUnderline();
    Boolean getBold();
    Boolean getItalic();
    Context getContext();
}