package xhj.zime.com.mymaptest.TaskList;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xhj.zime.com.mymaptest.Main.MainActivity;
import xhj.zime.com.mymaptest.Model.TaskPoint;
import xhj.zime.com.mymaptest.R;
import xhj.zime.com.mymaptest.SUser.TaskPointStatusString;
import xhj.zime.com.mymaptest.SUser.TaskStatusString;
import xhj.zime.com.mymaptest.SqliteDatabaseCollector.SQLdm;

public class Task2Activity extends AppCompatActivity {
    private ImageButton back;
    private ListView listView;
    private TextView taskName;
    private Button qidong, zanting, wancheng;
    private List<TaskPoint> list = new ArrayList<>();
    private Task2Adapter adapter;
    private String taskNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);
        initView();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Task2Activity.this, TaskDetailActivity.class);
                TaskPoint taskPoint = list.get(i);
                intent.putExtra("taskPoint", taskPoint);
                startActivity(intent);
                adapter.notifyDataSetChanged();
                String taskPointName = taskPoint.getTaskPointName();

                //点击之后改变数据库中的状态
                SQLiteDatabase db = new SQLdm().openDatabase(Task2Activity.this);
                ContentValues values = new ContentValues();
                values.put("is_record", TaskPointStatusString.TASK_POINT_ISREADED);
                db.update("taskpoint", values, "task_point_name = ?", new String[]{taskPointName});
                db.close();
            }
        });
        initData();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SQLiteDatabase db = new SQLdm().openDatabase(Task2Activity.this);
        Cursor cursor = db.rawQuery("select task_status from tasklist where task_name = ?", new String[]{taskNameText});
        int task_status = 0;
        if (cursor.moveToNext()) {
            task_status = cursor.getInt(cursor.getColumnIndex("task_status"));
        }

        if (task_status == TaskStatusString.TASK_STATUS_DANGQIAN) {
            qidong.setEnabled(false);
            zanting.setEnabled(true);
            wancheng.setEnabled(true);
        } else if (task_status == TaskStatusString.TASK_STATUS_WANGCHENG) {
            qidong.setEnabled(false);
            zanting.setEnabled(false);
            wancheng.setEnabled(false);
        } else if (task_status == TaskStatusString.TASK_STATUS_ISPAUSE) {
            qidong.setEnabled(true);
            zanting.setEnabled(false);
            wancheng.setEnabled(true);
        }
        db.close();
        /*
             启动,先把所有已经启动的任务暂停,再启动某一个
         */
        qidong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = new SQLdm().openDatabase(Task2Activity.this);
                ContentValues values = new ContentValues();
                values.put("task_status", TaskStatusString.TASK_STATUS_ISPAUSE);
                db.update("tasklist", values, "task_status = ?", new String[]{TaskStatusString.TASK_STATUS_DANGQIAN + ""});
                values.clear();
                values.put("task_status", TaskStatusString.TASK_STATUS_DANGQIAN);
                db.update("tasklist", values, "task_name = ?", new String[]{taskNameText});
                db.close();
                qidong.setEnabled(false);
                zanting.setEnabled(true);
                wancheng.setEnabled(true);
            }
        });
        zanting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = new SQLdm().openDatabase(Task2Activity.this);
                ContentValues values = new ContentValues();
                values.put("task_status", TaskStatusString.TASK_STATUS_ISPAUSE);
                db.update("tasklist", values, "task_name = ?", new String[]{taskNameText});
                db.close();
                qidong.setEnabled(true);
                zanting.setEnabled(false);
                wancheng.setEnabled(true);
            }
        });
        wancheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = new SQLdm().openDatabase(Task2Activity.this);
                ContentValues values = new ContentValues();
                values.put("task_status", TaskStatusString.TASK_STATUS_WANGCHENG);
                db.update("tasklist", values, "task_name = ?", new String[]{taskNameText});
                db.close();
                qidong.setEnabled(false);
                zanting.setEnabled(false);
                wancheng.setEnabled(false);
            }
        });
    }

    private void initData() {
        list.clear();
        SQLiteDatabase db = new SQLdm().openDatabase(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int userId = preferences.getInt("userId",-1);
        Cursor cursor = db.rawQuery("select * from taskpoint where user_id = ? order by is_record", new String[]{userId+""});
        while (cursor.moveToNext()) {
            String task_point_name = cursor.getString(cursor.getColumnIndex("task_point_name"));
            int status = cursor.getInt(cursor.getColumnIndex("is_record"));
            TaskPoint taskPoint = new TaskPoint(task_point_name, status);
            list.add(taskPoint);
        }
        taskNameText = getIntent().getStringExtra("taskName");
        this.taskName.setText(taskNameText);
        adapter.notifyDataSetChanged();
        db.close();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.list_view);
        taskName = (TextView) findViewById(R.id.task_name);
        qidong = (Button) findViewById(R.id.qidong);
        zanting = (Button) findViewById(R.id.zanting);
        wancheng = (Button) findViewById(R.id.wancheng);
        adapter = new Task2Adapter(this, list);
        back = (ImageButton) findViewById(R.id.back);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    class Task2Adapter extends BaseAdapter {
        private Context mContext;
        private List<TaskPoint> mList = new ArrayList<>();

        public Task2Adapter(Context context, List<TaskPoint> list) {
            mContext = context;
            mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View itemView = View.inflate(mContext, R.layout.item_task_detail, null);
            TextView textView = (TextView) itemView.findViewById(R.id.task_detail_text);
            textView.setText(mList.get(i).getTaskPointName());
            if (TaskPointStatusString.TASK_POINT_ISREADED == mList.get(i).getRecordStatus()) {
                itemView.setBackgroundResource(R.color.color_low_gray);
            } else if (TaskPointStatusString.TASK_POINT_ISSAVED == mList.get(i).getRecordStatus()) {
                itemView.setBackgroundResource(R.color.gray);
            }
            return itemView;
        }
    }
}
