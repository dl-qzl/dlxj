package xhj.zime.com.mymaptest.Util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {

    public static final String baseUrl = "http://192.168.1.233:8080/";

    public static void sendOkHttpRequest(String address, Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendPostHttpRequest(String address,String filePath,Callback callback){
        OkHttpClient client = new OkHttpClient();
        File file = new File(filePath);
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png/jpg"), file);
        Date date = new Date();
        String strDateFormat = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String format = sdf.format(date);
        RequestBody requestBody = null;
        requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", format+".png", fileBody)
                .build();

        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
