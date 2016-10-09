package com.ahmadian.ruhallah.commons.widgets;

import java.util.List;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.ahmadian.ruhallah.commons.R;
import com.ahmadian.ruhallah.commons.application.CommonsConfiguration;

/**
 * Created by ruhallah-PC on 10/7/2016.
 */

public class SmartSpinner extends Spinner{

    private String font;
    private int entries;
    private int textColor;
    private float textSize;
    private int backgroundTextColor;

    public SmartSpinner(Context context) {
        super(context);
        init();
    }

    public SmartSpinner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public SmartSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(defStyleAttr, R.styleable.SmartSpinner);

        try {
            font = typedArray.getString(R.styleable.SmartSpinner_smart_font);
            entries = typedArray.getResourceId(R.styleable.SmartSpinner_smart_entries, 0);
            textColor = typedArray.getColor(R.styleable.SmartSpinner_smart_textColor, Color.BLACK);
            textSize = typedArray.getDimension(R.styleable.SmartSpinner_smart_textSize, 0.f);
            backgroundTextColor = typedArray.getColor(R.styleable.SmartSpinner_smart_backgroundColor, Color.WHITE);
        }finally {
            typedArray.recycle();
        }
        init();
    }

    private void init() {
        if (isInEditMode())
            return;

        if (entries != 0)
            setEntries(entries);
    }

    public void setEntries(int Id) {
        SpinnerAdapter adapter = new SpinnerAdapter(
                getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(Id));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.setAdapter(adapter);
    }

    public void setEntries(List<StringWithTag> strings) {
        SpinnerAdapterWithTag adapter = new SpinnerAdapterWithTag(
                getContext(), android.R.layout.simple_spinner_item, strings);
        this.setAdapter(adapter);
    }

    public class SpinnerAdapter extends ArrayAdapter<String> {

        public SpinnerAdapter(Context context, int resource, String[] strings) {
            super(context, resource, strings);
        }

        public SpinnerAdapter(Context context, int resource, List<String> strings) {
            super(context, resource, strings);
        }

        // Affects default (closed) state of the spinner
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setGravity(Gravity.CENTER);
            view.setTextColor(textColor);
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            view.setBackgroundColor(backgroundTextColor);
            view.setPadding(5, 5, 5, 5);
            view.setTypeface(CommonsConfiguration.getInstance().getTypeface(font));
            return view;
        }

        // Affects opened state of the spinner
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setGravity(Gravity.CENTER);
            view.setTextColor(textColor);
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            view.setBackgroundColor(backgroundTextColor);
            view.setPadding(5, 5, 5, 5);
            view.setTypeface(CommonsConfiguration.getInstance().getTypeface(font));
            return view;
        }
    }

    public class SpinnerAdapterWithTag extends ArrayAdapter<StringWithTag> {

        public SpinnerAdapterWithTag(Context context, int resource, List<StringWithTag> strings) {
            super(context, resource, strings);
        }

        public SpinnerAdapterWithTag(Context context, int resource, StringWithTag[] strings) {
            super(context, resource, strings);
        }

        // Affects default (closed) state of the spinner
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setGravity(Gravity.CENTER);
            view.setTextColor(textColor);
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            view.setBackgroundColor(backgroundTextColor);
            view.setPadding(5, 5, 5, 5);
            view.setTypeface(CommonsConfiguration.getInstance().getTypeface(font));
            return view;
        }

        // Affects opened state of the spinner
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setGravity(Gravity.CENTER);
            view.setTextColor(textColor);
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            view.setBackgroundColor(backgroundTextColor);
            view.setPadding(5, 5, 5, 5);
            view.setTypeface(CommonsConfiguration.getInstance().getTypeface(font));
            return view;
        }
    }

    public class StringWithTag {
        public String string;
        public Object tag;

        public StringWithTag(String stringPart, Object tagPart) {
            string = stringPart;
            tag = tagPart;
        }

        @Override
        public String toString() {
            return string;
        }
    }
}
