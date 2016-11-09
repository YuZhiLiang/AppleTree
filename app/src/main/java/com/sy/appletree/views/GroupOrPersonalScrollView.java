package com.sy.appletree.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.sy.appletree.R;
import com.sy.appletree.base.BaseApplication;

import butterknife.Bind;

/**
 * Created by 郁智良
 * on 2016/11/9 0009.
 * des
 */

public class GroupOrPersonalScrollView extends ScrollView {
    @Bind(R.id.inner_linear_layout_of_scroll)
    LinearLayout mInnerLinearLayoutOfScroll;

    public GroupOrPersonalScrollView(Context context) {
        this(context, null);
    }

    public GroupOrPersonalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(BaseApplication.getContext(), R.layout.group_or_personal_pager, this);
        initDate();
//        mInnerLinearLayoutOfScroll.addView();
    }

    //模拟或初始化数据
    private void initDate() {

    }
}
