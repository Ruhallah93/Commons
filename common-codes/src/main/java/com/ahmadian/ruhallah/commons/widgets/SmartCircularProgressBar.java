package com.ahmadian.ruhallah.commons.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.ahmadian.ruhallah.commons.R;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import fr.castorflex.android.circularprogressbar.CircularProgressDrawable;

/**
 * Created by rahmadian on 16/11/2016.
 */
public class SmartCircularProgressBar extends CircularProgressBar {

    private Context context;

    int color;
    float strokeWidth;
    float sweepSpeed;
    float rotationSpeed;
    int minSweepAngle;
    int maxSweepAngle;

    public SmartCircularProgressBar(Context context) {
        super(context, (AttributeSet)null);
    }

    public SmartCircularProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, fr.castorflex.android.circularprogressbar.R.attr.cpbStyle);
    }

    public SmartCircularProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.context = context;

        Resources res = context.getResources();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircularProgressBar, defStyle, 0);

        color = a.getColor(R.styleable.CircularProgressBar_cpb_color, res.getColor(R.color.cpb_default_color));
        strokeWidth = a.getDimension(R.styleable.CircularProgressBar_cpb_stroke_width, res.getDimension(R.dimen.cpb_default_stroke_width));
        sweepSpeed = a.getFloat(R.styleable.CircularProgressBar_cpb_sweep_speed, Float.parseFloat(res.getString(R.string.cpb_default_sweep_speed)));
        rotationSpeed = a.getFloat(R.styleable.CircularProgressBar_cpb_rotation_speed, Float.parseFloat(res.getString(R.string.cpb_default_rotation_speed)));
        minSweepAngle = a.getInteger(R.styleable.CircularProgressBar_cpb_min_sweep_angle, res.getInteger(R.integer.cpb_default_min_sweep_angle));
        maxSweepAngle = a.getInteger(R.styleable.CircularProgressBar_cpb_max_sweep_angle, res.getInteger(R.integer.cpb_default_max_sweep_angle));
    }

    public void setColors(int[] colors) {
        CircularProgressDrawable.Builder builder = (new CircularProgressDrawable.Builder(context)).sweepSpeed(sweepSpeed).rotationSpeed(rotationSpeed).strokeWidth(strokeWidth).minSweepAngle(minSweepAngle).maxSweepAngle(maxSweepAngle);
        if(colors != null && colors.length > 0) {
            builder.colors(colors);
        } else {
            builder.color(color);
        }

        CircularProgressDrawable indeterminateDrawable = builder.build();
        this.setIndeterminateDrawable(indeterminateDrawable);
    }

}
