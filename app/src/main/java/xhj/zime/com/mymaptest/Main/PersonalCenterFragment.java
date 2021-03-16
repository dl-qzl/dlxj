package xhj.zime.com.mymaptest.Main;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import xhj.zime.com.mymaptest.ActivityCollector.ActivityCollector;
import xhj.zime.com.mymaptest.Login.LoginActivity;
import xhj.zime.com.mymaptest.R;
import xhj.zime.com.mymaptest.SUser.TaskStatusString;
import xhj.zime.com.mymaptest.SqliteDatabaseCollector.SQLdm;
import xhj.zime.com.mymaptest.TaskList.TaskListActivity;
import xhj.zime.com.mymaptest.TaskList.TaskListFlawActivity;
import xhj.zime.com.mymaptest.Util.HttpUtil;
import xhj.zime.com.mymaptest.Util.Utility;
import xhj.zime.com.mymaptest.bean.BaseDataBack;
import xhj.zime.com.mymaptest.bean.DataBean;
import xhj.zime.com.mymaptest.bean.TaskBeansBean;
import xhj.zime.com.mymaptest.bean.TaskPointBeansBean;

import static android.app.Activity.RESULT_OK;

public class PersonalCenterFragment extends Fragment implements View.OnClickListener {
    private TextView list, download, upload, powerOffLogin, userName, userClassName;
    private ImageView userImage;
    private static final String TAG = "-----------------";

    //定义意图返回的请求码,是拍摄照片还是去系统相册选择照片
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    //会用到数据共享器,拍摄的照片用uri作为定位共享出去
    private static Uri imageUri;

    private String imgPath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nav_header, container, false);
        list = (TextView) view.findViewById(R.id.list);
        download = (TextView) view.findViewById(R.id.download);
        upload = (TextView) view.findViewById(R.id.upload);
        powerOffLogin = (TextView) view.findViewById(R.id.power_off_login);
        userName = (TextView) view.findViewById(R.id.name_text);
        userClassName = (TextView) view.findViewById(R.id.class_text);
        userImage = (ImageView) view.findViewById(R.id.user_image);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list.setOnClickListener(this);
        download.setOnClickListener(this);
        upload.setOnClickListener(this);
        powerOffLogin.setOnClickListener(this);
        userImage.setOnClickListener(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        userName.setText(preferences.getString("userName", null));
        userClassName.setText(preferences.getString("userClassName", null));
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadImg();
            }
        }).start();
    }

    private void loadImg() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        int userId = preferences.getInt("userId", -1);
        Log.i(TAG, "loadImg: " + userId);
        SQLiteDatabase db = new SQLdm().openDatabase(getContext());
        db.isOpen();
        String sqlStr = "select * from express where user_id=?";
        Cursor cursor = db.rawQuery(sqlStr, new String[]{userId + ""});
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            byte[] bytes = cursor.getBlob(cursor.getColumnIndex("user_img"));
            final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(getActivity()).load(bitmap).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(userImage);
                }
            });

        }
        cursor.close();
        db.close();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.list:
                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.alert_dialog, null);
                final AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setView(view1)
                        .create();
                dialog.show();
                TextView task, flaw;
                task = (TextView) view1.findViewById(R.id.task);
                flaw = (TextView) view1.findViewById(R.id.flaw);
                task.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), TaskListActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                flaw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), TaskListFlawActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.download:
                Intent intent1 = new Intent(getContext(), TaskDownLoadActivity.class);
                getActivity().startActivity(intent1);
                break;
            case R.id.upload:
                View view2 = LayoutInflater.from(getActivity()).inflate(R.layout.alert_dialog_upload, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                        .setView(view2)
                        .setCancelable(true)
                        .create();
                alertDialog.show();
                String[] uris = new String[3];
                int index = 0;
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                int userId = preferences.getInt("userId", -1);
                String address = HttpUtil.baseUrl + "picture/upload";
                SQLiteDatabase db = new SQLdm().openDatabase(getContext());
                Cursor cursor = db.rawQuery("select file_uri from adjunctlist where user_id = ?", new String[]{userId + ""});
                while (cursor.moveToNext()) {
                    String uri = cursor.getString(cursor.getColumnIndex("file_uri"));
                    if (uri != null) {
                        uris[index] = uri;
                        index++;
                    }
                }
                db.close();
                File[] files = new File[3];
                for (int i = 0; i < uris.length; i++) {
                    if (uris[i] != null) {
                        files[i] = new File(uris[i]);
                    }
                }
                final int[] count = {0};
                for (int i = 0; i < uris.length; i++) {

                    HttpUtil.sendPostHttpRequest(address, files[i].getPath(), new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.i(TAG, "onFailure: ");
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    alertDialog.dismiss();
                                    Toast.makeText(getContext(),"任务上传失败!",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseText = response.body().string();
                            BaseDataBack baseDataBack = Utility.handleBaseDataBackResponse(responseText);
                            Object data = baseDataBack.getData();
                            Log.i(TAG, "onResponse: " + data.toString());
                            count[0]++;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (count[0] == 3){
                                        alertDialog.dismiss();
                                        Toast.makeText(getContext(),"任务上传成功!",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });

                }
                break;
            case R.id.power_off_login:
                ActivityCollector.finishAll();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.user_image:
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
                break;
            default:
                break;
        }
    }

    private void saveImg() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
                int byteCount = bitmap.getByteCount();
                int maxSize = 1024 * 1024 * 3;
                int mQuality = 80;
                if (byteCount > maxSize) {
                    mQuality = 20;
                }
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, mQuality, outputStream);
                byte[] bytes = outputStream.toByteArray();
                SQLiteDatabase db = new SQLdm().openDatabase(getContext());
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

                int userId = preferences.getInt("userId", -1);
                String sqlStr = "select * from express where user_id=?";
                Cursor cursor = db.rawQuery(sqlStr, new String[]{userId + ""});
                if (cursor.getCount() > 0) {
                    ContentValues values = new ContentValues();
                    values.put("user_img", bytes);
                    db.update("express", values, "user_id = ?", new String[]{userId + ""});
                    Log.i("---------------", userId + "更新头像");
                } else {
                    ContentValues values = new ContentValues();
                    values.put("user_id", userId);
                    values.put("user_img", bytes);
                    db.insert("express", null, values);
                    Log.i("------------", userId + "设置头像");
                }
                cursor.close();
                db.close();
            }
        }).start();
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    handleImageOnKitKat(data);
                }
                break;
            default:
                break;
        }
    }

    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(getContext(), uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.media.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        imgPath = imagePath;
        displayImage(imagePath);
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            saveImg();
            Glide.with(this).load(imagePath).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(userImage);
        } else {
            Toast.makeText(getContext(), "加载图片失败!", Toast.LENGTH_SHORT).show();
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

}





