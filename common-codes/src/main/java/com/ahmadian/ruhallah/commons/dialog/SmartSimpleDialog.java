package com.ahmadian.ruhallah.commons.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;

import com.ahmadian.ruhallah.commons.R;
import com.ahmadian.ruhallah.commons.utils.text.StringUtils;
import com.ahmadian.ruhallah.commons.utils.text.Typefaces;
import com.ahmadian.ruhallah.commons.widgets.SmartTextView;

/**
 * Created by ruhallah-PC on 11/12/2016.
 */

public class SmartSimpleDialog {

    private static Typeface typeface;
    private static Integer color;

    public SmartSimpleDialog() {
    }

    public SmartSimpleDialog(Typeface typeface) {
        this.typeface = typeface;
    }

    public SmartSimpleDialog(int color) {
        this.color = color;
    }

    public SmartSimpleDialog(Typeface typeface, int color) {
        this.typeface = typeface;
        this.color = color;
    }

    public static AlertDialog createConfirmDialog(Context context,
                                                  String title,
                                                  String message,
                                                  String positiveStr,
                                                  String negativeStr,
                                                  DialogInterface.OnClickListener okListener,
                                                  DialogInterface.OnClickListener cancelListener) {
        if (typeface == null)
            typeface = Typefaces.get(context, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setPositiveButton(positiveStr, okListener)
                .setNegativeButton(negativeStr, cancelListener)
                .setCancelable(true);
        createTitle(builder, title);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        setStyle(alertDialog);
        return alertDialog;
    }

    //should called before create dialog
    private static void createTitle(AlertDialog.Builder builder, CharSequence title) {
        Context context = builder.getContext();

        SmartTextView titleView = new SmartTextView(context);
        if (!StringUtils.isEmpty(title)) {
            titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            titleView.setTextColor(Color.WHITE);
            titleView.setText(title);
            titleView.setPadding(10,10,10,10);
            titleView.setGravity(Gravity.CENTER);
            if (color == null)
                titleView.setBackgroundColor(context.getResources().getColor(R.color.grey_900));
            else
                titleView.setBackgroundColor(color);
            builder.setCustomTitle(titleView);
        }
    }

    private static void setStyle(AlertDialog dialog) {
        Context context = dialog.getContext();

        TextView txtMessage = (TextView) dialog.findViewById(android.R.id.message);

        txtMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        txtMessage.setTypeface(typeface);
        txtMessage.setTextColor(context.getResources().getColor(R.color.grey_900));

        //buttons
        Button btn1 = (Button) dialog.findViewById(android.R.id.button1);
        Button btn2 = (Button) dialog.findViewById(android.R.id.button2);
        Button btn3 = (Button) dialog.findViewById(android.R.id.button3);

        if (btn1 != null) {
            btn1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            btn1.setTypeface(typeface);
            btn1.setTextColor(context.getResources().getColor(R.color.grey_900));
        }
        if (btn2 != null) {
            btn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            btn2.setTypeface(typeface);
            btn2.setTextColor(context.getResources().getColor(R.color.grey_900));
        }
        if (btn3 != null) {
            btn3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            btn3.setTypeface(typeface);
            btn3.setTextColor(context.getResources().getColor(R.color.grey_900));
        }
    }
}
