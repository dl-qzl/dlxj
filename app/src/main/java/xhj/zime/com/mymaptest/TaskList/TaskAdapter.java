package xhj.zime.com.mymaptest.TaskList;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xhj.zime.com.mymaptest.Main.MainActivity;
import xhj.zime.com.mymaptest.R;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<Task> list = new ArrayList<>();

    public TaskAdapter(List<Task> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_activity_task_list, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final Task task = list.get(i);
        viewHolder.text1.setText(task.getText1());
        viewHolder.text2.setText(task.getText2());
        viewHolder.text3.setText(task.getText3());
        viewHolder.text4.setText(task.getText4());
        viewHolder.title.setText(task.getTitle());
        viewHolder.text5.setText(task.getText5());
        if (task.getTaskStatus() == 1){
            viewHolder.taskStatus.setText("已暂停");
        }else if (task.getTaskStatus() == 0){
            viewHolder.taskStatus.setText("当前任务");
        }else if (task.getTaskStatus() == 2){
            viewHolder.taskStatus.setText("未启动");
        }else if (task.getTaskStatus() == 3){
            viewHolder.taskStatus.setText("已完成");
        }else if (task.getTaskStatus() == 4){
            viewHolder.taskStatus.setText("已上传");
        }else if (task.getTaskStatus() == 5){
            viewHolder.taskStatus.setText("上传失败");
        }

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Task2Activity.class);
                intent.putExtra("taskName",list.get(i).getTitle());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2, text3, text4, title, taskStatus, text5;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            taskStatus = (TextView) itemView.findViewById(R.id.task_status);
            text1 = (TextView) itemView.findViewById(R.id.text1);
            text2 = (TextView) itemView.findViewById(R.id.text2);
            text3 = (TextView) itemView.findViewById(R.id.text3);
            text4 = (TextView) itemView.findViewById(R.id.text4);
            text5 = (TextView) itemView.findViewById(R.id.text9);
            title = (TextView) itemView.findViewById(R.id.task_title);
        }
    }
}
