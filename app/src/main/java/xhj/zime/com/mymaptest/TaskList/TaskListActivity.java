package xhj.zime.com.mymaptest.TaskList;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import xhj.zime.com.mymaptest.ActivityCollector.BaseActivity;
import xhj.zime.com.mymaptest.R;
import xhj.zime.com.mymaptest.SUser.TaskStatusString;
import xhj.zime.com.mymaptest.SqliteDatabaseCollector.SQLdm;

public class TaskListActivity extends BaseActivity {
    private List<Task> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private LinearLayoutManager manager;
    public SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        recyclerView = (RecyclerView) findViewById(R.id.task_list_recycler_view);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        adapter = new TaskAdapter(list);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        ImageButton back = findViewById(R.id.setting_back_to_main);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        showTaskList();
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showTaskList();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        showTaskList();
    }

    public void showTaskList() {
        list.clear();
        SQLiteDatabase db = new SQLdm().openDatabase(this);
        int userId = PreferenceManager.getDefaultSharedPreferences(this).getInt("userId", -1);
        Cursor cursor = db.rawQuery("select * from tasklist where user_id=? and task_type=? " +
                "order by task_status asc", new String[]{userId + "", 401 + ""});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int task_status = cursor.getInt(cursor.getColumnIndex("task_status"));
                String taskName = cursor.getString(cursor.getColumnIndex("task_name"));
                String leader = cursor.getString(cursor.getColumnIndex("task_leader"));
                String task_crew = cursor.getString(cursor.getColumnIndex("task_crew"));
                String task_plan_time = cursor.getString(cursor.getColumnIndex("task_plan_time"));
                String task_work_no = cursor.getString(cursor.getColumnIndex("task_work_no"));
                String task_confirm_time = cursor.getString(cursor.getColumnIndex("task_confirm_time"));
                Task task = new Task(task_status, taskName, leader, task_crew, task_plan_time, task_confirm_time, task_work_no);
                list.add(task);
            }
            adapter.notifyDataSetChanged();
        }
        db.close();
        refresh.setRefreshing(false);
    }

}
