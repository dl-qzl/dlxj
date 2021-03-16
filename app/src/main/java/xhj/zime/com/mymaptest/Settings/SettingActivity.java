package xhj.zime.com.mymaptest.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import xhj.zime.com.mymaptest.ActivityCollector.ActivityCollector;
import xhj.zime.com.mymaptest.ActivityCollector.BaseActivity;
import xhj.zime.com.mymaptest.R;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout mAbout, mFinishAll, mClearCash;
    private ImageButton mBack;
    private TextView mCacheSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        mBack.setOnClickListener(this);
        mFinishAll.setOnClickListener(this);
        mAbout.setOnClickListener(this);
        mClearCash.setOnClickListener(this);
        File cacheDir = getCacheDir();
        long folderSize = getFolderSize(cacheDir);
        showCacheSize(folderSize);

    }

    private long getFolderSize(File file) {
        long size = 0;
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                size += getFolderSize(files[i]);
            } else {
                size += files[i].length();
            }
        }
        return size;
    }

    private void showCacheSize(long size) {
        String result = "";
        double Kbyte = size / 1024;
        double Mbyte = Kbyte / 1024;
        if (Mbyte < 1) {
            result = String.format("%.2f", Kbyte) + "KB";
        } else {
            result = String.format("%.2f", Mbyte) + "MB";
        }
        mCacheSize.setText(result);
    }



    private void initView() {
        mBack = (ImageButton) findViewById(R.id.setting_back_to_main);
        mFinishAll = (LinearLayout) findViewById(R.id.linearLayout5);
        mAbout = (LinearLayout) findViewById(R.id.linearLayout3);
        mClearCash = (LinearLayout) findViewById(R.id.linearLayout4);
        mCacheSize = (TextView) findViewById(R.id.tv_cache_size);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linearLayout5:
                ActivityCollector.finishAll();
                break;
            case R.id.setting_back_to_main:
                finish();
                break;
            case R.id.linearLayout3:
                openAboutUs();
                break;
            case R.id.linearLayout4:
                View view1 = LayoutInflater.from(this).inflate(R.layout.dialog_setting_clear_cache,null);
                AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view1).create();
                alertDialog.show();
                delCache(getCacheDir());
                alertDialog.dismiss();
                Toast.makeText(SettingActivity.this,"清除缓存成功",Toast.LENGTH_SHORT).show();
                mCacheSize.setText("0.00KB");
                break;
        }
    }


    private void delCache(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                delCache(files[i]);
            }
        }else {
            file.delete();
        }
    }

    private void openAboutUs() {
        Intent intent = new Intent(this, SettingAboutUsActivity.class);
        startActivity(intent);
    }
}
