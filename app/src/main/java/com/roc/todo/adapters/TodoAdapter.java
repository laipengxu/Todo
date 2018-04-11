package com.roc.todo.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.roc.todo.R;
import com.roc.todo.entity.TodoBean;
import com.roc.todo.libs.qmui.widget.dialog.QMUIDialog;
import com.roc.todo.libs.qmui.widget.dialog.QMUIDialogAction;
import com.roc.todo.managers.TodoManager;
import com.roc.todo.ui.ClosureTodoActivity;
import com.roc.todo.ui.TodoActivity;

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
                public boolean onLongClick(final View v) {
                    // 弹出操作对话框
                    new QMUIDialog.MenuDialogBuilder(v.getContext())
                            .addItem("修改事件", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    editTodo(v.getContext());
                                    dialog.dismiss();
                                }
                            })
                            .addItem("结束事件", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteTodo(v.getContext());
                                    dialog.dismiss();
                                }
                            })
                            .show();
                    return true;
                }


            });
        }

        /**
         * 编辑修改
         *
         * @param context
         */
        private void editTodo(Context context) {
            TodoBean todoBean = mData.get(getAdapterPosition());
            String todoContent = todoBean.getTodoContent();
            final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(context)
                    .setTitle("修改")
                    .setDefaultText(todoContent)
                    .setCanceledOnTouchOutside(false);
            builder.addAction("取消", new QMUIDialogAction.ActionListener() {
                @Override
                public void onClick(QMUIDialog dialog, int index) {
                    dialog.dismiss();
                }
            });
            builder.addAction("确定修改", new QMUIDialogAction.ActionListener() {
                @Override
                public void onClick(QMUIDialog dialog, int index) {
                    EditText editText = builder.getEditText();
                    TodoBean todoBean = mData.get(getAdapterPosition());
                    if (dialog.getBaseContext() instanceof TodoActivity) {
                        TodoManager.getInstance().updateTodoNotification(dialog.getBaseContext(), todoBean.getId(), editText.getText().toString().trim());
                        ((TodoActivity) dialog.getBaseContext()).initData();
                        Toast.makeText(dialog.getBaseContext(), "修改完成", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });
            builder.show();
        }

        /**
         * 删除
         *
         * @param context
         */
        private void deleteTodo(Context context) {
            TodoBean todoBean = mData.get(getAdapterPosition());
            int id = todoBean.getId();
            String todoContent = todoBean.getTodoContent();
            Intent intent = new Intent(context, ClosureTodoActivity.class);
            intent.putExtra(TodoManager.EXTRA_RANDOM_ID, id);
            intent.putExtra(TodoManager.EXTRA_TODO_CONTENT, todoContent);
            context.startActivity(intent);
        }

        void setupData(TodoBean todoBean) {
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

