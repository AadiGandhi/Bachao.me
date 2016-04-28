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
import com.capstone.aadityagandhi.bachaome.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by aaditya.gandhi on 3/19/16.
 * This handles the network request being made to the Google Direction API
 */

public class NetworkRequest {
    private RequestQueue queue;
    private String url = "https://maps.googleapis.com/maps/api/directions/json?";
    private Context context;
    private JSONObject jsonObject;
    private DataStore dataStore = new DataStore();
    private StringRequest stringRequest;

    public NetworkRequest(Context context){
        queue = Volley.newRequestQueue(context);
        this.context = context;
    }

    public JSONObject requestLocation(String UID){
        Log.d("CustomTag","Making a Network Request here!");
        String url = "http://capstone.bitnamiapp.com/capstone/get_coordinates.php?device_id=";
        url+=UID;
        //main_device
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("CustomTag","JSON fetched from server: "+ response);
                        try {
                            MainActivity.jsonObject = new JSONObject(response);
                            MainActivity.launchActivity(context);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"This didnt work!",Toast.LENGTH_LONG).show();
                Log.d("CustomTag","Request Failed!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
        return jsonObject;
    }
    public void sendRegDevicesToServer(String token,String UID){
        String url = "http://capstone.bitnamiapp.com/capstone/register.php?";
            url+="reg_id="+token;
            url+="&device_id="+UID;
            Log.d("CustomTag: ", url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("CustomTag", "DAFAQ! " + response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "This didnt work!", Toast.LENGTH_LONG).show();
                }
            });
        queue.add(stringRequest);
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
