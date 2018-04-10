package com.roc.todo.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.roc.todo.R;
import com.roc.todo.adapters.TodoAdapter;
import com.roc.todo.entity.TodoBean;
import com.roc.todo.managers.TodoManager;
import com.roc.todo.utils.SPUtils;
import com.roc.todo.widget.ItemDecoration.NormalDividerDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TodoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEt_todo_content;
    private View mV_empty_view;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TodoAdapter mTodoAdapter;
    private RecyclerView mRecyclerView;

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            initData();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        initView();
        initListener();
        initData();
    }

    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mV_empty_view = findViewById(R.id.tv_empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new NormalDividerDecoration(this));
        mTodoAdapter = new TodoAdapter();
        mRecyclerView.setAdapter(mTodoAdapter);
        mEt_todo_content = (EditText) findViewById(R.id.et_todo_content);
    }

    private void initListener() {
        findViewById(R.id.iv_add_todo).setOnClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        LocalBroadcastManager
                .getInstance(this)
                .registerReceiver(mBroadcastReceiver,
                                  new IntentFilter(ClosureTodoActivity.ACTION_CLOSURE)
                );
    }

    private void initData() {
        mSwipeRefreshLayout.setRefreshing(true);
        List<TodoBean> allTodo = getAllTodo();
        if (allTodo.size() > 0) {
            mV_empty_view.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mTodoAdapter.update(allTodo);
        } else {
            mV_empty_view.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 获取所有待办事项
     */
    private List<TodoBean> getAllTodo() {
        ArrayList<TodoBean> todoBeen = new ArrayList<>();
        Map<String, ?> all = SPUtils.getAll();
        for (String id : all.keySet()) {
            String todoContent = SPUtils.getString(id);
            todoBeen.add(new TodoBean(Integer.parseInt(id), todoContent));
        }
        return todoBeen;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add_todo:
                String todoContent = mEt_todo_content.getText().toString().trim();
                if (TextUtils.isEmpty(todoContent)) {
                    Toast.makeText(this, "没有内容输入", Toast.LENGTH_SHORT).show();
                    mEt_todo_content.setText("");
                } else {
                    TodoManager.getInstance().sendTodoNotification(this, todoContent);
                    mEt_todo_content.setText("");
                    initData();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }
}
