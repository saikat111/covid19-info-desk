package com.codingburg.covid19.allcountry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codingburg.covid19.R;
import com.codingburg.covid19.adapter.CounrtyAdapter;
import com.codingburg.covid19.model.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllcountryActivity extends AppCompatActivity {
    private static final String URL_PRODUCTS = "https://disease.sh/v3/covid-19/countries";

    //a list to store all the products
    List<Data> courtyList;

    //the recyclerview
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allcountry);
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        courtyList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadProducts();
    }
    private void loadProducts() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject getCounrtyData = array.getJSONObject(i);
                                JSONObject object = getCounrtyData.getJSONObject("countryInfo");

                                //adding the product to product list
                                courtyList.add(new Data(
                                        getCounrtyData.getString("country"),
                                        getCounrtyData.getString("cases"),
                                        getCounrtyData.getString("deaths"),
                                        getCounrtyData.getString("todayCases"),
                                        getCounrtyData.getString("todayDeaths"),
                                        object.getString("flag")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            CounrtyAdapter adapter = new CounrtyAdapter(getApplicationContext(), courtyList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}