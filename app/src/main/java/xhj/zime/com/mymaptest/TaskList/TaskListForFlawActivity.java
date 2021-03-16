package xhj.zime.com.mymaptest.TaskList;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import xhj.zime.com.mymaptest.ActivityCollector.BaseActivity;
import xhj.zime.com.mymaptest.Main.MainActivity;
import xhj.zime.com.mymaptest.R;
import xhj.zime.com.mymaptest.SqliteDatabaseCollector.SQLdm;

public class TaskListForFlawActivity extends BaseActivity {
    private List<Task> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_task_list_flaw);
        initData();
        recyclerView = (RecyclerView) findViewById(R.id.task_list_recycler_view);
        adapter = new TaskAdapter(list);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        ImageButton back = findViewById(R.id.setting_back_to_main);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskListForFlawActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        SQLdm dm = new SQLdm();
        SQLiteDatabase database = dm.openDatabase(this);
        String name = getIntent().getStringExtra("name");
        Cursor cursor = database.rawQuery("select * from tasklist where task_leader = ?",new String[]{name});
        if (cursor !=null){
            while (cursor.moveToNext()){
                String taskName = cursor.getString(cursor.getColumnIndex("task_name"));
                String leader = cursor.getString(cursor.getColumnIndex("task_leader"));
                String team_type = cursor.getString(cursor.getColumnIndex("team_type"));
                String task_plan_time = cursor.getString(cursor.getColumnIndex("task_plan_time"));
                String task_end_time = cursor.getString(cursor.getColumnIndex("task_end_time"));
                int task_status = cursor.getInt(cursor.getColumnIndex("task_status"));
                Task task = new Task(task_status,taskName,leader,team_type,task_plan_time,task_end_time,"");
                list.add(task);
            }
        }
        database.close();
    }
}
