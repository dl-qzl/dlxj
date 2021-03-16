package xhj.zime.com.mymaptest.TaskList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 封装一个基础的SpinnerAdapter，便于外界调用时进行扩展
 *
 * @author zuo
 * @date 2018/7/23 15:24
 */
public abstract class BaseSpinnerAdapter<T extends RecyclerView.ViewHolder, E> extends RecyclerView.Adapter<T> {
    private static OnItemClickListener onItemClickListener;

    public static interface OnItemClickListener<E> {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    protected Context context;
    protected List<E> datas;
    protected int layoutID;

    public BaseSpinnerAdapter(Context context, List<E> datas, int layoutID) {
        this.context = context;
        this.datas = datas;
        this.layoutID = layoutID;
    }

    @NonNull
    @Override
    public T onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, layoutID, null);
        return getViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public void onBindViewHolder(final T holder, final int position) {
        setValues(holder, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder.itemView, position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemLongClick(holder.itemView, position);
                }
                return true;
            }
        });
    }

    /**
     * 返回viewholder
     *
     * @param itemView
     * @return
     */
    protected abstract T getViewHolder(View itemView);

    /**
     * 设置控件数据
     *
     * @param holder
     * @param position
     */
    protected abstract void setValues(T holder, int position);

    /**
     * 获取Spinner展示的数据
     */
    protected abstract List<E> getData();
}
