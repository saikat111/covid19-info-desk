package com.codingburg.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.codingburg.covid19.allcountry.AllcountryActivity;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import static com.codingburg.covid19.YourCountryActivity.SHARED_PREFS;

public class MainActivity extends AppCompatActivity {
    private TextView todayCase, todayDeath, todayRecovered, totalTest, totalCase, totalDeath, totalRecovered,activeCase, toolbar_title;
    private   Toolbar toolbar;
    private SimpleArcLoader simpleArcLoader;
    private String country;
    private ImageView flag;
    private Spinner spinner;
    private ImageView imageView;
    private PieChart pieChart;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    private ProgressDialog progressDialog;
    private  TextView counrtyName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("updating");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        loadData();
        setSupportActionBar(toolbar);
        todayCase = findViewById(R.id.textView5);
        todayDeath = findViewById(R.id.textView7);
        todayRecovered = findViewById(R.id.textView6);
        totalTest = findViewById(R.id.textView8);
        totalCase = findViewById(R.id.textView57);
        totalDeath = findViewById(R.id.textView723);
        totalRecovered = findViewById(R.id.textView61);
        activeCase = findViewById(R.id.textView81);
        toolbar_title = findViewById(R.id.toolbar_title);
        simpleArcLoader = findViewById(R.id.loader);
        counrtyName = findViewById(R.id.countryname);
        flag = findViewById(R.id.flag);
        pieChart = findViewById(R.id.piechart);
        fetchData();

        spinner = findViewById(R.id.spinner);
        imageView = findViewById(R.id.submit);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryName.countryNames));
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
                            toolbar_title.setText(jsonObject.getString("country"));
                            counrtyName.setText(toolbar_title.getText().toString());
                            JSONObject object = jsonObject.getJSONObject("countryInfo");
                            String flagUrl = object.getString("flag");
                            Glide.with(getApplicationContext()).load(flagUrl).into(flag);
                            /*String len = jsonObject.getString("todayCases");
                            System.out.println(" Today case =  " + jsonObject.getString("todayCases"));
                            System.out.println(" Today case  len =  " + len.length());
                            if (Integer.parseInt(len) == 0) {
                                System.out.println("True");
                            }
                            else{
                                System.out.println("false");
                            }*/
                            if(Integer.parseInt( jsonObject.getString("todayCases")) == 0 && Integer.parseInt(jsonObject.getString("todayDeaths"))== 0 && Integer.parseInt(jsonObject.getString("todayRecovered"))== 0){
                                showDialogPolygon();

                            }



                            pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(totalCase.getText().toString()), Color.parseColor("#FFA726")));
                            pieChart.addPieSlice(new PieModel("Recoverd",Integer.parseInt(totalRecovered.getText().toString()), Color.parseColor("#66BB6A")));
                            pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(totalDeath.getText().toString()), Color.parseColor("#EF5350")));
                            pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(activeCase.getText().toString()), Color.parseColor("#29B6F6")));
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
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }

    public void details(View view) {
        Intent homeIntent=new Intent(getApplicationContext(), detailesActivity.class);
        startActivity(homeIntent);
    }

    public void saveData(String data) {
        progressDialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT, data);
        editor.apply();
        View layout = getLayoutInflater().inflate(R.layout.toast_custom, (ViewGroup) findViewById(R.id.custom_toast_layout_id));
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setTextColor(Color.WHITE);
        text.setText("Updating!");
        CardView lyt_card = (CardView) layout.findViewById(R.id.lyt_card);
        lyt_card.setCardBackgroundColor(getResources().getColor(R.color.yellow_800));

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
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

    public void wordData(View view) {
        Intent homeIntent=new Intent(getApplicationContext(), AllcountryActivity.class);
        startActivity(homeIntent);
    }

    private void showDialogPolygon() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_header_polygon);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);

        ((Button) dialog.findViewById(R.id.bt_join)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

            }
        });

        ((Button) dialog.findViewById(R.id.bt_decline)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent=new Intent(getApplicationContext(), WorldDataActivity.class);
                startActivity(homeIntent);
            }
        });


        dialog.show();
    }

}
