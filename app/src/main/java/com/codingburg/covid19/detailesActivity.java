package com.codingburg.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class detailesActivity extends AppCompatActivity {
    private TextView todayCase, todayDeath, todayRecovered, totalTest, totalCase, totalDeath, totalRecovered, activeCase;
    private SimpleArcLoader simpleArcLoader;
    private PieChart pieChart;
    private Spinner spinner;
    private ImageView imageView;
    private ProgressDialog progressDialog;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    private BarChart mBarChart;
    private String country;
    private ImageView flag;
    private  TextView counrtyName;
    private TextView criticalPerOneMillion, recoveredPerOneMillion, activePerOneMillion, deathsPerOneMillion, casesPerOneMillion, critical;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailes);
        loadData();
        flag = findViewById(R.id.flag);
        casesPerOneMillion = findViewById(R.id.casesPerOneMillion);
        counrtyName = findViewById(R.id.countryname);
        recoveredPerOneMillion = findViewById(R.id.recoveredPerOneMillion);
        deathsPerOneMillion = findViewById(R.id.deathsPerOneMillion);
        casesPerOneMillion = findViewById(R.id.casesPerOneMillion);
        activePerOneMillion = findViewById(R.id.activePerOneMillion);
        criticalPerOneMillion = findViewById(R.id.criticalPerOneMillion);
        critical = findViewById(R.id.critical);
        todayCase = findViewById(R.id.textView5);
        todayDeath = findViewById(R.id.textView7);
        todayRecovered = findViewById(R.id.textView6);
        totalTest = findViewById(R.id.textView8);
        totalCase = findViewById(R.id.textView57);
        totalDeath = findViewById(R.id.textView723);
        totalRecovered = findViewById(R.id.textView61);
        activeCase = findViewById(R.id.textView81);
        simpleArcLoader = findViewById(R.id.loader);
        pieChart = findViewById(R.id.piechart);
        mBarChart = (BarChart) findViewById(R.id.barchart);
        progressDialog = new ProgressDialog(this);
        imageView = findViewById(R.id.submit);
        progressDialog.setMessage("updating");
        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryName.countryNames));

        fetchData();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String country = CountryName.countryNames[spinner.getSelectedItemPosition()];
                saveData(country);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("country", country);
                startActivity(intent);
                finish();
            }
        });
    }

    private void fetchData() {

        String url1  = "https://disease.sh/v3/covid-19/countries/";
        String url = url1 + country;

        simpleArcLoader.start();

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            todayCase.setText(jsonObject.getString("todayCases"));
                            todayDeath.setText(jsonObject.getString("todayDeaths"));
                            todayRecovered.setText(jsonObject.getString("todayRecovered"));
                            totalTest.setText(jsonObject.getString("tests"));
                            totalCase.setText(jsonObject.getString("cases"));
                            totalDeath.setText(jsonObject.getString("deaths"));
                            totalRecovered.setText(jsonObject.getString("recovered"));
                            activeCase.setText(jsonObject.getString("active"));
                            criticalPerOneMillion.setText(jsonObject.getString("criticalPerOneMillion"));
                            recoveredPerOneMillion.setText(jsonObject.getString("recoveredPerOneMillion"));
                            deathsPerOneMillion.setText(jsonObject.getString("deathsPerOneMillion"));
                            activePerOneMillion.setText(jsonObject.getString("activePerOneMillion"));
                            casesPerOneMillion.setText(jsonObject.getString("casesPerOneMillion"));
                            critical.setText(jsonObject.getString("critical"));
                            counrtyName.setText(jsonObject.getString("country"));


                            JSONObject object = jsonObject.getJSONObject("countryInfo");
                            String flagUrl = object.getString("flag");
                            Glide.with(getApplicationContext()).load(flagUrl).into(flag);

                            pieChart.addPieSlice(new PieModel("Cases", Integer.parseInt(totalCase.getText().toString()), Color.parseColor("#FFA726")));
                            pieChart.addPieSlice(new PieModel("Recoverd", Integer.parseInt(totalRecovered.getText().toString()), Color.parseColor("#66BB6A")));
                            pieChart.addPieSlice(new PieModel("Deaths", Integer.parseInt(totalDeath.getText().toString()), Color.parseColor("#EF5350")));
                            pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(activeCase.getText().toString()), Color.parseColor("#29B6F6")));

                            mBarChart.addBar(new BarModel(Integer.parseInt(totalCase.getText().toString()), 0xFF123456));
                            mBarChart.addBar(new BarModel(Integer.parseInt(totalRecovered.getText().toString()), 0xFF343456));
                            mBarChart.addBar(new BarModel(Integer.parseInt(totalDeath.getText().toString()), 0xFF563456));
                            mBarChart.addBar(new BarModel(Integer.parseInt(activeCase.getText().toString()), 0xFF873F56));


                            mBarChart.startAnimation();
                            pieChart.startAnimation();
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void saveData(String data) {
        progressDialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT, data);
        editor.apply();
        Toast.makeText(this, "Updating", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String text = sharedPreferences.getString(TEXT, "");
        country = text;
        if(country == ""){
            Intent homeIntent=new Intent(getApplicationContext(), YourCountryActivity.class);
            startActivity(homeIntent);
            finish();
        }

    }
}