package com.capstone.aadityagandhi.bachaome.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aaditya.gandhi on 3/19/16.
 * This handles the network request being made to the Google Direction API
 */

public class NetworkRequest {
    private RequestQueue queue;
    private String url = "https://maps.googleapis.com/maps/api/directions/json?";
    private Context context;
    private JSONObject jsonObject;

    public NetworkRequest(Context context){
        queue = Volley.newRequestQueue(context);
        this.context = context;
    }
    public JSONObject makeRequest(String origin, String destination,String key,String alternatives){
        url+="origin="+origin+"&";
        url+="destination="+destination+"&";
        url+="key="+key+"&";
        //url+="alternatives="+alternatives;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response.substring(0, 50), Toast.LENGTH_LONG);
                        Log.d("CustomTag","I am here: "+ response.substring(0,500));
                        try {
                            jsonObject = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"This didnt work!",Toast.LENGTH_LONG).show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
        return jsonObject;

    }

}
