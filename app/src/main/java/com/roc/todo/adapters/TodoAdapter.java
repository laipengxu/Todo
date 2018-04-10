package com.roc.todo.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roc.todo.R;
import com.roc.todo.entity.TodoBean;
import com.roc.todo.managers.TodoManager;
import com.roc.todo.ui.ClosureTodoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 赖鹏旭 on 2018/3/14.
 * 待办事项列表适配器
 */
public class TodoAdapter extends RecyclerView.Adapter {

    private List<TodoBean> mData = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).setupData(mData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_todo_content;

        ViewHolder(View itemView) {
            super(itemView);
            tv_todo_content = (TextView) itemView.findViewById(R.id.tv_todo_content);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    TodoBean todoBean = mData.get(getAdapterPosition());
                    int id = todoBean.getId();
                    String todoContent = todoBean.getTodoContent();
                    Intent intent = new Intent(v.getContext(), ClosureTodoActivity.class);
                    intent.putExtra(TodoManager.EXTRA_RANDOM_ID, id);
                    intent.putExtra(TodoManager.EXTRA_TODO_CONTENT, todoContent);
                    v.getContext().startActivity(intent);
                    return true;
                }
            });
        }

        public void setupData(TodoBean todoBean) {
            if (todoBean != null) {
                tv_todo_content.setText(todoBean.getTodoContent());
            }
        }
    }

    public void update(List<TodoBean> data) {
        if (data != null && data.size() > 0) {
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }
}

