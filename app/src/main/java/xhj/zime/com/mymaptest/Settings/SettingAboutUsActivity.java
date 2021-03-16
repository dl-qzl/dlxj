package xhj.zime.com.mymaptest.Settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import xhj.zime.com.mymaptest.R;

public class SettingAboutUsActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton mBack;
    private WebView mAboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_about_us);
        initView();
        mBack.setOnClickListener(this);
        mAboutUs.loadUrl("http://www.baidu.com");
        mAboutUs.setWebViewClient(new WebViewClient());
    }

    private void initView() {
        mBack = (ImageButton) findViewById(R.id.setting_back_to_main);
        mAboutUs = findViewById(R.id.wv_about_us);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setting_back_to_main:
                finish();
                break;
        }
    }
}
