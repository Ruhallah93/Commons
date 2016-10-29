package com.ahmadian.ruhallah.commons.utils.text.span;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by rahmadian on 24/10/2016.
 */
public class CustomSpannableStringConstructor implements IBuilderSpannableString{

    private Context context;
    private String text = "";
    private Typeface typeface;
    private Float relativeSize = null;
    private Integer foregroundColor = null;
    private Integer backgroundColor = null;
    private Boolean underline = false;
    private Boolean bold = false;
    private Boolean italic = false;

    public CustomSpannableStringConstructor(Context context) {
        this.context = context;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Typeface getTypeface() {
        return typeface;
    }

    @Override
    public Float getRelativeSize() {
        return relativeSize;
    }

    @Override
    public Integer getForegroundColor() {
        return foregroundColor;
    }

    @Override
    public Integer getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public Boolean getUnderline() {
        return underline;
    }

    @Override
    public Boolean getBold() {
        return bold;
    }

    @Override
    public Boolean getItalic() {
        return italic;
    }

    @Override
    public CustomSpannableStringConstructor setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public CustomSpannableStringConstructor setText(int textId) {
        this.text = context.getResources().getString(textId);
        return this;
    }

    @Override
    public CustomSpannableStringConstructor setTypeFace(Typeface typeface) {
        this.typeface = typeface;
        return this;
    }

    @Override
    public CustomSpannableStringConstructor setRelativeSize(Float relativeSize) {
        this.relativeSize = relativeSize;
        return this;
    }

    @Override
    public CustomSpannableStringConstructor setForegroundColor(Integer foregroundColor) {
        this.foregroundColor = foregroundColor;
        return this;
    }

    @Override
    public CustomSpannableStringConstructor setBackgroundColor(Integer backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    @Override
    public CustomSpannableStringConstructor setUnderline(Boolean underline) {
        this.underline = underline;
        return this;
    }

    @Override
    public CustomSpannableStringConstructor setBold(Boolean bold) {
        this.bold = bold;
        return this;
    }

    @Override
    public CustomSpannableStringConstructor setItalic(Boolean italic) {
        this.italic = italic;
        return this;
    }

    @Override
    public Context getContext() {
        return context;
    }

    public CustomSpannableString build() {
        return CustomSpannableString.getInstance(this);
    }
}
