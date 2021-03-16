package xhj.zime.com.mymaptest.TaskList;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import xhj.zime.com.mymaptest.R;
import xhj.zime.com.mymaptest.SqliteDatabaseCollector.SQLdm;
import xhj.zime.com.mymaptest.Util.Utility;
import xhj.zime.com.mymaptest.bean.ObjectAttributeBean;

public class TaskDetailActivity extends AppCompatActivity {
    TextView mtext1, mtext2, mtext3, mtext4, mtext5, mtext6, mtext7, mtext8, mtext9, mtext10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        final FrameLayout layout = (FrameLayout) findViewById(R.id.layout);

        ImageButton backToTaskList = (ImageButton) findViewById(R.id.setting_back_to_main);
        backToTaskList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        final Button flawJiLu = (Button) findViewById(R.id.flaw_jilu);
        final Button flawLuru = (Button) findViewById(R.id.flaw_luru);
        initView();
        SQLiteDatabase db = new SQLdm().openDatabase(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int userId = preferences.getInt("userId",-1);
        Cursor cursor = db.rawQuery("select * from taskpoint where user_id = ?", new String[]{userId+""});
        ObjectAttributeBean objectAttributeBean;
        if (cursor.moveToNext()) {
            String attr_json = cursor.getString(cursor.getColumnIndex("attr_json"));
            objectAttributeBean = Utility.handleObjectAttrResponse(attr_json);
            String attributeType = objectAttributeBean.getAttributeType();
            String attributeNo = objectAttributeBean.getAttributeNo();
            String attributeLength = objectAttributeBean.getAttributeLength();
            String attributeDepth = objectAttributeBean.getAttributeDepth();
            String attributeWidth = objectAttributeBean.getAttributeWidth();
            String attributeHeight = objectAttributeBean.getAttributeHeight();
            String attributeCompletionTime = objectAttributeBean.getAttributeCompletionTime();
            String attributeUseTime = objectAttributeBean.getAttributeUseTime();
            String attributeDescription = objectAttributeBean.getAttributeDescription();
            String attributeUnit = objectAttributeBean.getAttributeUnit();
            ContentValues values = new ContentValues();
            values.put("att_info0",attributeType);
            values.put("att_info1",attributeNo);
            values.put("att_info10",attributeLength);
            values.put("att_info11",attributeDepth);
            values.put("att_info2",attributeWidth);
            values.put("att_info3",attributeHeight);
            values.put("att_info4",attributeUnit);
            values.put("att_info5",attributeCompletionTime);
            values.put("att_info6",attributeUseTime);
            values.put("att_info7",attributeDescription);
            values.put("user_id",userId);
            db.insert("objectattribute",null,values);
            mtext1.setText(attributeType);
            mtext2.setText(attributeNo);
            mtext3.setText(attributeLength);
            mtext4.setText(attributeDepth);
            mtext5.setText(attributeWidth);
            mtext6.setText(attributeHeight);
            mtext7.setText(attributeUnit);
            mtext8.setText(attributeCompletionTime);
            mtext9.setText(attributeUseTime);
            mtext10.setText(attributeDescription);
        }
        db.close();

        flawJiLu.setEnabled(false);
        flawLuru.setEnabled(true);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.layout, new TaskFlawObjectFragment());
        transaction.commit();
        
        flawJiLu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flawJiLu.setEnabled(false);
                flawLuru.setEnabled(true);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.layout, new TaskFlawObjectFragment());
                transaction.commit();
            }
        });
        flawLuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flawJiLu.setEnabled(true);
                flawLuru.setEnabled(false);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.layout, new TaskFlawObjectFragment2());
                transaction.commit();
            }
        });
    }

    private void initView() {
        mtext1 = (TextView) findViewById(R.id.text1);
        mtext2 = (TextView) findViewById(R.id.text2);
        mtext3 = (TextView) findViewById(R.id.text3);
        mtext4 = (TextView) findViewById(R.id.text4);
        mtext5 = (TextView) findViewById(R.id.text5);
        mtext6 = (TextView) findViewById(R.id.text6);
        mtext7 = (TextView) findViewById(R.id.text7);
        mtext8 = (TextView) findViewById(R.id.text8);
        mtext9 = (TextView) findViewById(R.id.text9);
        mtext10 = (TextView) findViewById(R.id.text10);
    }

}
