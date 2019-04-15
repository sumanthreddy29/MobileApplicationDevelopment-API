package com.gsu.yelpsearch;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private RecyclerView recycleView;
    private Button btnRequest;
    private EditText search,location;
    private RequestQueue requestQueue;
    private StringRequest mStringRequest;
    private String url = "";
    ArrayList<YelpModel> yelpModelArrayList;
    private YelpAdapter yelpAdapter;
    private static ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search=(EditText)findViewById(R.id.editText);
        location=(EditText)findViewById(R.id.location);
        btnRequest = (Button) findViewById(R.id.buttonRequest);
        recycleView =(RecyclerView)findViewById(R.id.recycler);
        recycleView.setHasFixedSize(true);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        btnRequest.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v){
                                            String keyword=search.getText().toString();
                                              String loc=location.getText().toString();

                                              sendAndRequestResponse(keyword,loc);

                                          }
                                      }

        );

    }





    private void sendAndRequestResponse(String keyword,String loc) {
         url="https://api.yelp.com/v3/businesses/search?location="+loc+"&term="+keyword;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                            try {
                                JSONObject obj = new JSONObject(response);
                                yelpModelArrayList = new ArrayList<>();
                                JSONArray dataArray  = obj.getJSONArray("businesses");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    YelpModel playerModel = new YelpModel();
                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                    Log.e("obj:",dataobj.toString());
                                    playerModel.setName("Name:"+dataobj.getString("name"));
                                    playerModel.setRating("Rating:"+Integer.toString(dataobj.getInt("rating")));
                                    playerModel.setImageURL(dataobj.getString("image_url"));
                                    playerModel.setPhone("Phone:"+dataobj.getString("phone"));
                                  JSONObject add = dataobj.getJSONObject("location");
                                    Log.e("initial:",add.toString());
                                    JSONArray addArray = add.getJSONArray("display_address");
                                    Log.e("array:",addArray.toString());
                                    String displayAddress="Address:";
                                    for(int x=0;x<addArray.length();x++) {
                                         displayAddress += addArray.get(x).toString();
                                        Log.e("final:", displayAddress.toString());
                                    }
                                    playerModel.setAddress(displayAddress);

                                    yelpModelArrayList.add(playerModel);

                                }

                                setupRecycler();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            /**
             * Passing some request headers*
             */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer XfvdHa1E3T7zrAlB8RXQz6YE1fwkCB-SU2hH13AJNaK875RHsaSiWm8SwDCsVG2lFt4gCKuFSEKGVWpKFQVoxRaT7URiEKhgR-pV80zq7pwlL42_AfyYcWTsvX2mXHYx");
                return headers;
            }
        } ;

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }

    private void setupRecycler(){

        yelpAdapter = new YelpAdapter(this,yelpModelArrayList);
        recycleView.setAdapter(yelpAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }





}




