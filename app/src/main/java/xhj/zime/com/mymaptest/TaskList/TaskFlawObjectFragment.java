package xhj.zime.com.mymaptest.TaskList;

import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import xhj.zime.com.mymaptest.R;
import xhj.zime.com.mymaptest.SUser.TaskPointStatusString;
import xhj.zime.com.mymaptest.SqliteDatabaseCollector.SQLdm;
import xhj.zime.com.mymaptest.Util.HttpUtil;

public class TaskFlawObjectFragment extends Fragment{
    private ImageView photo1,photo2,photo3;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flaw_object, container, false);
        EditText flawJiLu = (EditText) view.findViewById(R.id.flaw_jilu);
        EditText flawLevel = (EditText) view.findViewById(R.id.flaw_level);
        EditText flawJiLuTime = (EditText) view.findViewById(R.id.flaw_jilu_time);
        photo1 = (ImageView) view.findViewById(R.id.photo1);
        photo2 = (ImageView) view.findViewById(R.id.photo2);
        photo3 = (ImageView) view.findViewById(R.id.photo3);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int userId = preferences.getInt("userId",-1);
        SQLiteDatabase db = new SQLdm().openDatabase(getActivity());
        Cursor cursor1 = db.rawQuery("select * from flawlist where user_id = ?", new String[]{userId+""});
        if (cursor1.moveToNext()){
            flawJiLu.setText(cursor1.getString(cursor1.getColumnIndex("flaw_explain")));
            flawLevel.setText(cursor1.getString(cursor1.getColumnIndex("flaw_level_name")));
            flawJiLuTime.setText(cursor1.getString(cursor1.getColumnIndex("record_time")));
        }

        Cursor cursor = db.rawQuery("select * from adjunctlist where user_id = ?", new String[]{userId + ""});
        int current = 1;
        while(cursor.moveToNext()){
            if (cursor.getInt(cursor.getColumnIndex("file_no")) != 0 && current == 1){
                String filePath = cursor.getString(cursor.getColumnIndex("file_path"));
                Glide.with(getActivity()).load(HttpUtil.baseUrl+filePath).into(photo1);
                current++;
            }else if (cursor.getInt(cursor.getColumnIndex("file_no")) != 0 && current == 2){
                String filePath = cursor.getString(cursor.getColumnIndex("file_path"));
                Glide.with(getActivity()).load(HttpUtil.baseUrl+filePath).into(photo2);
                current++;
            }else {
                if (cursor.getInt(cursor.getColumnIndex("file_no")) == 0 && current == 1){
                    String uri = cursor.getString(cursor.getColumnIndex("file_uri"));
                    Glide.with(getActivity()).load(uri).into(photo1);
                    current++;
                }else if (cursor.getInt(cursor.getColumnIndex("file_no")) == 0 && current == 2){
                    String uri = cursor.getString(cursor.getColumnIndex("file_uri"));
                    Glide.with(getActivity()).load(uri).into(photo2);
                    current++;
                }else if (cursor.getInt(cursor.getColumnIndex("file_no")) == 0 && current == 3){
                    String uri = cursor.getString(cursor.getColumnIndex("file_uri"));
                    Glide.with(getActivity()).load(uri).into(photo3);
                    current++;
                }
            }
        }
        db.close();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}



