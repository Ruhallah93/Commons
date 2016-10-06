package com.ahmadian.ruhallah.commons.utils.text;

import android.text.Spannable;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;

/**
 * Created by rahmadian on 06/10/2016.
 */
public class TextUtils {

    public static Spanned find(CharSequence input, String search, int highlightColor) {
        Spannable sp = Spannable.Factory.getInstance().newSpannable(StringUtils.isEmpty(input) ? "" : input);
        if (sp.length() == 0 || StringUtils.isEmpty(search)) {
            return sp;
        }

        char[] inputChars = input.toString().toCharArray();
        char[] searchChars = search.toString().toCharArray();
        int inputCharsLength = inputChars.length;
        int searchCharsLength = searchChars.length;
        int index = 0;
        int matchOffset = 0;

        int start = 0;
        if (inputCharsLength >= searchCharsLength && searchCharsLength != 0) {
            while (index < inputCharsLength) {
                if (inputChars[index] == searchChars[matchOffset]) {
                    if (matchOffset == 0) {
                        start = index;
                    }
                    matchOffset++;
                } else {
                    if (matchOffset > 0) {
                        index = start;
                    }
                    matchOffset = 0;
                }

                index++;

                if (matchOffset >= searchCharsLength) {
                    sp.setSpan(new BackgroundColorSpan(highlightColor),
                            start, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    matchOffset = 0;
                }
            }
        }
        return sp;
    }

}
