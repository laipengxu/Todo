package com.roc.todo.ui;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.roc.todo.R;
import com.roc.todo.managers.TodoManager;
import com.roc.todo.utils.SPUtils;

/**
 * Created by 赖鹏旭 on 2018/3/13.
 * 结束待办事项界面
 */
public class ClosureTodoActivity extends Activity implements View.OnClickListener {

    public static final String ACTION_CLOSURE = "action_closure";

    private TextView mTv_todo_content;
    private int mRandomID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFinishOnTouchOutside(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_closure_todo);

        initView();
        initListener();
        initData();
    }

    /**
     * 初始化控件
     */
    public void initView() {
        mTv_todo_content = (TextView) findViewById(R.id.tv_todo_content);
    }

    /**
     * 初始化事件
     */
    public void initListener() {
        findViewById(R.id.tv_btn_continue).setOnClickListener(this);
        findViewById(R.id.tv_btn_closure).setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    public void initData() {
        String todoContent = getIntent().getStringExtra(TodoManager.EXTRA_TODO_CONTENT);
        mRandomID = getIntent().getIntExtra(TodoManager.EXTRA_RANDOM_ID, -1);
        mTv_todo_content.setText(todoContent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_btn_continue:
                ClosureTodoActivity.this.onBackPressed();
                break;
            case R.id.tv_btn_closure:
                if (mRandomID > 0) {
                    ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).cancel(mRandomID);
                    SPUtils.remove(String.valueOf(mRandomID));
                    LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_CLOSURE));
                } else {
                    Toast.makeText(ClosureTodoActivity.this, "结束失败", Toast.LENGTH_SHORT).show();
                }
                ClosureTodoActivity.this.onBackPressed();
                break;
        }
    }
}
