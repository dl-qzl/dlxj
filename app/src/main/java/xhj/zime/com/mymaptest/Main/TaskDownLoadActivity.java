package xhj.zime.com.mymaptest.Main;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import xhj.zime.com.mymaptest.R;
import xhj.zime.com.mymaptest.SUser.TaskPointStatusString;
import xhj.zime.com.mymaptest.SUser.TaskStatusString;
import xhj.zime.com.mymaptest.SqliteDatabaseCollector.SQLdm;
import xhj.zime.com.mymaptest.TaskList.BaseSpinnerAdapter;
import xhj.zime.com.mymaptest.TaskList.SpinnerChooseAdapter;
import xhj.zime.com.mymaptest.TaskList.SpinnerUtils;
import xhj.zime.com.mymaptest.Util.HttpUtil;
import xhj.zime.com.mymaptest.Util.Utility;
import xhj.zime.com.mymaptest.bean.AdjunctBean;
import xhj.zime.com.mymaptest.bean.BaseDataBack;
import xhj.zime.com.mymaptest.bean.DataBean;
import xhj.zime.com.mymaptest.bean.TaskBeansBean;
import xhj.zime.com.mymaptest.bean.TaskPointBeansBean;

public class TaskDownLoadActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton mBack;
    private ImageButton mTimeStart, mTimeEnd;
    private Button mDownload;
    private TextView mMarqueeText;
    private TextView mTextStart, mTextEnd;
    private TextView mTimeStartToEnd;
    private SpinnerChooseAdapter mAdapter;
    private List<String> mList = new ArrayList<>();
    private SpinnerUtils mSpinnerUtils;
    private int mTaskDownLoadCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_down_load);
        initView();
        mBack.setOnClickListener(this);
        mTimeStart.setOnClickListener(this);
        mTimeEnd.setOnClickListener(this);
        mDownload.setOnClickListener(this);
        mMarqueeText.setSelected(true);

        initData();
        mAdapter = new SpinnerChooseAdapter(this, mList, new BaseSpinnerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mTimeStartToEnd.setText(mList.get(position));
                if (mSpinnerUtils != null) {
                    mSpinnerUtils.closeSpinner();
                }

                if ("本周".equals(mTimeStartToEnd.getText())) {
                    long l = System.currentTimeMillis();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar calendar = Calendar.getInstance();
                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                    long beginTime = l - (dayOfWeek - 1) * (24 * 60 * 60 * 1000);
                    long endTime = l + (7 - dayOfWeek) * (24 * 60 * 60 * 1000);
                    String dateBegin = format.format(beginTime);
                    String dateEnd = format.format(endTime);
                    mTextStart.setText(dateBegin);
                    mTextEnd.setText(dateEnd);
                } else if ("本月".equals(mTimeStartToEnd.getText())) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.DAY_OF_MONTH,1);
                    Date time = calendar.getTime();
                    String dateBegin = format.format(time);
                    mTextStart.setText(dateBegin);

                    Calendar calendar2 = Calendar.getInstance();
                    int actualMaximum = calendar2.getActualMaximum(Calendar.DATE);
                    calendar2.set(Calendar.DAY_OF_MONTH,actualMaximum);
                    Date time2 = calendar2.getTime();
                    String dateEnd = format.format(time2);
                    mTextEnd.setText(dateEnd);
                } else if ("自定义".equals(mTimeStartToEnd.getText())) {
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                mTimeStartToEnd.setText(mList.get(position));
            }
        });
        mSpinnerUtils = new SpinnerUtils(this, mTimeStartToEnd, mAdapter);
        mSpinnerUtils.init();

    }

    private void initData() {
        mList.add("本周");
        mList.add("本月");
        mList.add("自定义");
    }

    private void initView() {
        mBack = (ImageButton) findViewById(R.id.setting_back_to_main);
        mTimeEnd = (ImageButton) findViewById(R.id.btn_time_end);
        mTimeStart = (ImageButton) findViewById(R.id.btn_time_start);
        mDownload = (Button) findViewById(R.id.btn_download);
        mTimeStartToEnd = findViewById(R.id.tv_time_start_to_end);
        mTextStart = (TextView) findViewById(R.id.tv_start);
        mTextEnd = (TextView) findViewById(R.id.tv_end);
        mMarqueeText = (TextView) findViewById(R.id.tv_marquee);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_back_to_main:
                finish();
                break;
            case R.id.btn_time_start:
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(this, null, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        StringBuffer sb = new StringBuffer();
                        sb.append(i + "-" + (i1 + 1) + "-" + i2);
                        mTextStart.setText(sb.toString());
                    }
                });
                dialog.show();
                break;
            case R.id.btn_time_end:
                Calendar calendar2 = Calendar.getInstance();
                DatePickerDialog dialog2 = new DatePickerDialog(this, null, calendar2.get(Calendar.YEAR),
                        calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH));
                dialog2.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        StringBuffer sb = new StringBuffer();
                        sb.append(i + "-" + (i1 + 1) + "-" + i2);
                        String start = mTextStart.getText().toString();
                        String end = sb.toString();
                        boolean isAllowShowEndTime = compareToBegin(start, end);
                        if (!isAllowShowEndTime){
                            mTextEnd.setText("");
                            Toast.makeText(TaskDownLoadActivity.this,"结束日期不能在开始日期之前",Toast.LENGTH_SHORT).show();
                        }else {
                            mTextEnd.setText(end);
                        }
                    }
                });
                dialog2.show();
                break;
            case R.id.btn_download:
                View view1 = LayoutInflater.from(this).inflate(R.layout.alert_dialog_download, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setView(view1)
                        .setCancelable(true)
                        .create();
                alertDialog.show();

                String startTime = mTextStart.getText().toString();
                String endTime = mTextEnd.getText().toString();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(TaskDownLoadActivity.this);
                int userId = preferences.getInt("userId",-1);
                String address = HttpUtil.baseUrl + "task/data/download?userid="+userId+"&pageSize=10&pageNo=1"+"&startTime=20161011&endTime=20191011";
                downloadTask(address,startTime,endTime);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                TextView taskDownLoadCount = view1.findViewById(R.id.text_download_count);
                taskDownLoadCount.setText("您已经同步了"+mTaskDownLoadCount+"条任务数据");
                ProgressBar progressBar = view1.findViewById(R.id.progress);
                progressBar.setVisibility(View.GONE);

                Button mBtnSure = view1.findViewById(R.id.btn_sure);
                mBtnSure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                break;
        }
    }

    private void downloadTask(String address, final String startTime, final String endTime) {
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TaskDownLoadActivity.this, "任务同步失败!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                BaseDataBack baseDataBack = Utility.handleBaseDataBackResponse(responseText);
                Gson gson = new Gson();
                int backCode = baseDataBack.getCode();
                if (backCode > 0) {
                    SQLiteDatabase db = new SQLdm().openDatabase(TaskDownLoadActivity.this);
                    Object data = baseDataBack.getData();
                    DataBean dataBean = Utility.handleDataResponse(gson.toJson(data));
                    List<TaskBeansBean> taskBeans = dataBean.getTaskBeans();
                    ContentValues values = new ContentValues();
                    int user_id = PreferenceManager.getDefaultSharedPreferences(TaskDownLoadActivity.this).getInt("userId", -1);
                    for (TaskBeansBean taskBeansBean : taskBeans) {
                        String task_crew = taskBeansBean.getTask_crew();
                        String task_id = taskBeansBean.getTask_id();
                        String task_leader = taskBeansBean.getTask_leader();
                        String task_name = taskBeansBean.getTask_name();
                        String task_plan_tiam = taskBeansBean.getTask_plan_time().split(" ")[0];
                        String task_type = taskBeansBean.getTask_type();
                        String task_work_no = taskBeansBean.getTask_work_no();
                        String team_type = taskBeansBean.getTeam_type();
                        Date date = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String time = dateFormat.format(date);
                        String real = time.split(" ")[0];
                        if (compareToBegin(startTime,task_plan_tiam) && compareToBegin(task_plan_tiam,endTime)){
                            values.clear();
                            values.put("task_crew", task_crew);
                            values.put("task_id", task_id);
                            values.put("task_leader", task_leader);
                            values.put("task_name", task_name);
                            values.put("task_plan_time", task_plan_tiam);
                            values.put("task_type", task_type);
                            values.put("task_work_no", task_work_no);
                            values.put("team_type", team_type);
                            values.put("user_id", user_id);
                            values.put("task_confirm_time", real);
                            values.put("task_status", TaskStatusString.TASK_STATUS_WEIQIDONG);
                            db.insert("tasklist", null, values);
                            mTaskDownLoadCount++;
                        }
                    }
                    List<TaskPointBeansBean> taskPointBeans = dataBean.getTaskPointBeans();
                    for (TaskPointBeansBean x : taskPointBeans) {
                        values.clear();
                        values.put("hasflaw", (String) x.getHasflaw());
                        values.put("object_type_id", x.getTask_id());
                        values.put("task_id", x.getTask_id());
                        values.put("task_point_id", x.getTask_point_id());
                        values.put("task_point_location", x.getTask_point_location());
                        values.put("task_point_name", x.getTask_point_name());
                        values.put("task_type", x.getTask_type());
                        values.put("attr_json",x.getAttr_json());
                        values.put("user_id",user_id);
                        values.put("is_record", TaskPointStatusString.TASK_POINT_NOTREADED);
                        db.insert("taskpoint", null, values);
                    }
                    List<AdjunctBean> adjunctBeans = dataBean.getAdjunctBeans();
                    for (AdjunctBean x: adjunctBeans){
                        values.clear();
                        values.put("user_id",user_id);
                        values.put("add_time",x.getAdd_time());
                        values.put("file_name",x.getFile_name());
                        values.put("file_no",x.getFile_no());
                        values.put("file_path",x.getFile_path());
                        values.put("flaw_id",x.getFlaw_id());
                        db.insert("adjunctlist",null,values);
                    }
                    db.close();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TaskDownLoadActivity.this, "任务同步失败!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    /*
        符合结束时间大于开始时间,返回true,否则返回false
     */
    private boolean compareToBegin(String start, String end) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (start == null && end == null){
            return true;
        }
        if ("".equals(start) && "".equals(end)){
            return true;
        }
        try {
            Date startDate = format.parse(start);
            Date endDate = format.parse(end);
            int status = startDate.compareTo(endDate);
            if (status > 0) {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }
}
