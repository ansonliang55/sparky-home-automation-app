package com.aga.hcp.home_control_prototype.Spark;

import android.content.Context;
import android.util.Log;

import com.aga.hcp.home_control_prototype.OnTaskCompleted;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Master on 10/22/2014.
 */
public class FanToggleTask extends SparkAPITask {
    private final String TAG = "FanToggleTAsk";
    private static String api_path = "toggle2";
    private static HashMap<OnTaskCompleted, Context> statusListeners = new HashMap<OnTaskCompleted, Context>();


    public FanToggleTask(String deviceID, OnTaskCompleted listener) {
        super(deviceID, api_path, listener);
    }
    public FanToggleTask(String deviceID, OnTaskCompleted listener, Context context) {
        super(deviceID, api_path, listener, context);
    }

    @Override
    public void onPostExecute(JSONArray jsonArray){
        Set entries = statusListeners.entrySet();
        Iterator i = entries.iterator();
        while(i.hasNext()){

            Map.Entry entry = (Map.Entry)i.next();
            Log.i(TAG, "onPostExecute() -> Notifying observer that task has completed: " + entry.toString());
            ((OnTaskCompleted)entry.getKey()).onTaskCompleted(jsonArray, (Context)entry.getValue());
        }
    }

    public static void registerForToggleEvent(OnTaskCompleted listener, Context context){
        statusListeners.put(listener, context);
    }
}