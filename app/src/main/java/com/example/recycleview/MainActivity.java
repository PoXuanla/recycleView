package com.example.recycleview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String POST_URL = "http://10.0.2.2/qrfood/public/api/posts";
    private String accesstoken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9sb2NhbGhvc3RcL3FyZm9vZFwvcHVibGljXC9hcGlcL2xvZ2luIiwiaWF0IjoxNTk4ODQ5Nzc0LCJleHAiOjE1OTk0NTQ1NzQsIm5iZiI6MTU5ODg0OTc3NCwianRpIjoiRU1vYkhGTElXWUZvdEpISiIsInN1YiI6MywicHJ2IjoiODdlMGFmMWVmOWZkMTU4MTJmZGVjOTcxNTNhMTRlMGIwNDc1NDZhYSJ9.GL4e4vhRK48wwm87iep3_7L86rSN2TnT7whHOpt2MfI";
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setView();

        setRecycleViewManager(); //設置RecyclerView Manager屬性

        getPosts();


    }
    private void setView(){
        mRecyclerView = findViewById(R.id.view);

    }
    private void setRecycleViewManager(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

    }
    private void getPosts(){
        StringRequest request = new StringRequest(Request.Method.GET, POST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //解析JSON檔傳入array

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    ArrayList<HashMap<String, String>> array = new ArrayList<HashMap<String, String>>();

                    for(int i=0;i<jsonArray.length();i++){
                        HashMap<String,String> data = new HashMap<String,String>();
                        JSONObject object = jsonArray.getJSONObject(i);
                        data.put("name",object.getString("name"));
                        data.put("subject",object.getString("subject"));
                        array.add(data);
                    }
                    Log.v("data",array.toString());
                    setRecycleView(array); //設定RecycleView

                }catch (Exception e){

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error is ", "" + error);
            }
        }) {

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("Authorization", "Bearer " + accesstoken);
                return headerMap;
            }

            //Pass Your Parameters here
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("User", UserName);
//                params.put("Pass", PassWord);
//                return params;
//            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }
    private void setRecycleView(ArrayList<HashMap<String, String>> data){
        MainActivity_Adapter mAdapter = new MainActivity_Adapter(MainActivity.this, data);
        mRecyclerView.setAdapter(mAdapter);
    }
}
