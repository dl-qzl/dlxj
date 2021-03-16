package xhj.zime.com.mymaptest.TaskList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import xhj.zime.com.mymaptest.R;

/**
 * 展示String类型数据的SpinnerAdapter
 *
 * @author zuo
 * @date 2017/11/23 15:25
 */

public class SpinnerChooseAdapter extends BaseSpinnerAdapter<RecyclerView.ViewHolder, String> {
    private List<String> mData;

    public SpinnerChooseAdapter(Context context, List<String> list, OnItemClickListener itemClickListener) {
        super(context, list, R.layout.item_choose_time);
        this.mData = list;
        setOnItemClickListener(itemClickListener);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View itemView) {
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ItemViewHolder(itemView);
    }

    @Override
    protected void setValues(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).textView.setText(mData.get(position));
    }

    @Override
    protected List<String> getData() {
        return mData;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.choose_item);
        }
    }
}
