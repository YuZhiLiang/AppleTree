package com.sy.appletree.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.sy.appletree.R;

/**
 * Created by 郁智良
 * on 2016/11/13 0013.
 * des
 */

public class PieView extends RelativeLayout {

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.view_pie_chart, this);
    }
}
