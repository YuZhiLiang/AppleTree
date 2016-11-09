package com.sy.appletree.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.sy.appletree.R;

/**
 * Created by 郁智良
 * on 2016/11/8 0008.
 * des
 */

public class GroupDetailItemView extends LinearLayout {

    public GroupDetailItemView(Context context) {
        this(context, null);
    }

    public GroupDetailItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(getContext(), R.layout.group_pager_num_line, this);
        initDate();
    }


    private void initDate() {

    }

}
