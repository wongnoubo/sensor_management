package com.sensor.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONFunction;
import net.sf.json.JSONObject;
import java.util.ArrayList;

public class Tojson{
    public JSONArray javaListAndJsonIntegerChange(ArrayList<Integer> list) {
        // list转JSONArray
        JSONArray jsArr = JSONArray.fromObject(list);
        // 从JSON串到JSONArray
        jsArr = JSONArray.fromObject(jsArr.toString());
        // --从JSONArray里读取
        return jsArr;
    }
}
