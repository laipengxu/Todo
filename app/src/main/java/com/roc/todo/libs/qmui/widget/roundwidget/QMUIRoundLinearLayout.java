package com.roc.todo.libs.qmui.widget.roundwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.roc.todo.R;
import com.roc.todo.libs.qmui.util.QMUIViewHelper;

/**
 * 见 {@link QMUIRoundButton} 与 {@link QMUIRoundButtonDrawable}
 */
public class QMUIRoundLinearLayout extends LinearLayout {

    public QMUIRoundLinearLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public QMUIRoundLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, R.attr.QMUIButtonStyle);
    }

    public QMUIRoundLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        QMUIRoundButtonDrawable bg = QMUIRoundButtonDrawable.fromAttributeSet(context, attrs, 0);
        QMUIViewHelper.setBackgroundKeepingPadding(this, bg);
    }
}
