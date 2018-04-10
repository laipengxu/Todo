package com.roc.todo.managers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.roc.todo.R;
import com.roc.todo.ui.ClosureTodoActivity;
import com.roc.todo.utils.SPUtils;

import java.util.Random;

/**
 * Created by 赖鹏旭 on 2018/3/13.
 * 待办事项管理者
 */
public class TodoManager {
    private static TodoManager mInstance;
    public static final String EXTRA_RANDOM_ID = "extra_random_id";
    public static final String EXTRA_TODO_CONTENT = "extra_todo_content";

    private TodoManager() {}

    public static TodoManager getInstance() {
        if (mInstance == null) {
            synchronized (TodoManager.class) {
                if (mInstance == null) {
                    mInstance = new TodoManager();
                }
            }
        }
        return mInstance;
    }

    public void sendTodoNotification(Context context, String todoContent) {
        // 系统通知管理者
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // 随机id
        int randomID = new Random().nextInt(999);

        // 创建通知
        Intent intent = new Intent(context, ClosureTodoActivity.class);
        intent.putExtra(EXTRA_RANDOM_ID, randomID);
        intent.putExtra(EXTRA_TODO_CONTENT, todoContent);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, randomID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification todoNotification = new NotificationCompat
                .Builder(context)
                .setContentTitle(todoContent)
                .setContentText("点击结束待办事项")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();
        todoNotification.flags |= Notification.FLAG_NO_CLEAR;

        // 发送通知
        notificationManager.notify(
                randomID,
                todoNotification
        );

        // 保存记录
        SPUtils.putString(String.valueOf(randomID), todoContent);
    }
}
