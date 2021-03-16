package xhj.zime.com.mymaptest.TaskList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import xhj.zime.com.mymaptest.R;

/**
 * Spinner工具类
 * <p>
 * author: zuo
 * date: 2017/11/29 15:05
 */

public class SpinnerUtils {
    private Context mContext;
    private TextView mTextView;
    private BaseSpinnerAdapter mAdapter;
    private PopupWindow popupWindow;
    private boolean noData;
    @DrawableRes
    private int mArrowDown = R.mipmap.arrow_down;
    @DrawableRes
    private int mArrowUp = R.mipmap.arrow_top;

    public SpinnerUtils(Context context, TextView textView, @NonNull BaseSpinnerAdapter adapter) {
        this.mContext = context;
        this.mTextView = textView;
        this.mAdapter = adapter;
    }

    /**
     * 设置箭头，如果不想使用系统的箭头图标，可以在这个方法中设置
     * 需要注意的时，这个方法需要在初始化方法init()之前调用
     * @param arrowDown
     * @param arrowUp
     */
    public void setArrows(@DrawableRes int arrowDown, @DrawableRes int arrowUp) {
        this.mArrowDown = arrowDown;
        this.mArrowUp = arrowUp;
    }

    public void init() {
        if (mAdapter != null && mAdapter.getData() != null && mAdapter.getData().size() > 0) {
            mTextView.setText("----请选择---");
            tvSetImg(mTextView, mArrowDown);
            noData = false;
        } else {
            mTextView.setText("----无数据---");
            noData = true;
        }
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noData) {
                    Toast.makeText(mContext, "无数据!", Toast.LENGTH_SHORT).show();
                    return;
                }
                showPopupWindow();
            }
        });
    }

    private void showPopupWindow() {
        tvSetImg(mTextView, mArrowUp);
        View view = LayoutInflater.from(mContext).inflate(R.layout.choose_pop_rv, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.shape_popup_view));
        popupWindow.showAsDropDown(mTextView);
        popupWindow.setOnDismissListener(new PopupDismissListener());
        RecyclerView recyclerView = view.findViewById(R.id.rv_choose_pop);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
    }

    /**
     * 关闭popupWindow
     */
    public void closeSpinner() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    /**
     * 弹窗消失的时候让箭头换回来
     */
    class PopupDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            tvSetImg(mTextView, mArrowDown);
        }

    }

    /**
     * 设置textView右侧的图像
     *
     * @param textView
     * @param img
     */
    private void tvSetImg(TextView textView, int img) {
        Drawable nav_up = mContext.getResources().getDrawable(img);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        textView.setCompoundDrawables(null, null, nav_up, null);
    }

}
