package xhj.zime.com.mymaptest.Util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import xhj.zime.com.mymaptest.bean.AdjunctBean;
import xhj.zime.com.mymaptest.bean.BaseDataBack;
import xhj.zime.com.mymaptest.bean.BaseDataBean;
import xhj.zime.com.mymaptest.bean.DataBean;
import xhj.zime.com.mymaptest.bean.ObjectAttributeBean;
import xhj.zime.com.mymaptest.bean.TaskBeansBean;
import xhj.zime.com.mymaptest.bean.UserBean;

public class Utility {

    public static ObjectAttributeBean handleObjectAttrResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONObject jsonObject = new JSONObject(response);
                String userString  = jsonObject.toString();
                Gson gson = new Gson();
                return gson.fromJson(userString,ObjectAttributeBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static BaseDataBean handleBaseDataResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONObject jsonObject = new JSONObject(response);
                String userString  = jsonObject.toString();
                Gson gson = new Gson();
                return gson.fromJson(userString,BaseDataBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static DataBean handleDataResponse(String response){
        if (!TextUtils.isEmpty(response)){
            Gson gson = new Gson();
            DataBean dataBean = gson.fromJson(response, DataBean.class);
            return dataBean;
        }
        return null;
    }
    public static BaseDataBack handleBaseDataBackResponse(String response) {
        if (!TextUtils.isEmpty(response)){
            try {
                JSONObject jsonObject = new JSONObject(response);
                String userString  = jsonObject.toString();
                Gson gson = new Gson();
                return gson.fromJson(userString,BaseDataBack.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static UserBean handleUserResponse(String response) {
        if (!TextUtils.isEmpty(response)){
            try {
                JSONObject jsonObject = new JSONObject(response);
                String userString  = jsonObject.toString();
                Gson gson = new Gson();
                return gson.fromJson(userString,UserBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
