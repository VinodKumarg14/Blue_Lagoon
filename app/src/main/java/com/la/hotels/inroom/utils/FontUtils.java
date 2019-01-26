package com.la.hotels.inroom.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by DELL on 2/12/2016.
 */
public class FontUtils
{
    public static final String ROOT = "fonts/";
    public static final String OPENSANS_REGULAR = ROOT + "OpenSans-Regular.ttf";
    public static final String OPENSANS_BOLD = ROOT + "OpenSans-Bold.ttf";
    public static final String OPENSANS_EXTRA_BOLD = ROOT + "OpenSans-ExtraBold.ttf";


    public static Typeface getTypeface(Context context, String font)
    {
        return Typeface.createFromAsset(context.getAssets(), font);
    }

    public static void applyTypeface(View v, Typeface typeface)
    {
        if (v instanceof ViewGroup)
        {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++)
            {
                View child = vg.getChildAt(i);
                applyTypeface(child, typeface);
            }
        }
        else if (v instanceof TextView)
        {
            ((TextView) v).setTypeface(typeface);
        }
    }
}
