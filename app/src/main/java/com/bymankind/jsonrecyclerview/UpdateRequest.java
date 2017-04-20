package com.bymankind.jsonrecyclerview;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Server-Panduit on 4/20/2017.
 */

public class UpdateRequest extends StringRequest {
    private final static String UPDATE_URL = "http://192.168.1.143/laosposapi/api/updateCookProgress";
    private Map<String, String> params;

    public UpdateRequest(int order_id, Response.Listener<String> listener){
        super(Request.Method.POST , UPDATE_URL, listener , null);
        params = new HashMap<>();
        params.put("order_id",order_id+"");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
