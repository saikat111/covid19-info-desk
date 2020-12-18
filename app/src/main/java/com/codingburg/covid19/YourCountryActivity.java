package com.codingburg.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class YourCountryActivity extends AppCompatActivity {
    private Spinner spinner;
    private ImageView imageView;
    private String country;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_country);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("updating");
        spinner = findViewById(R.id.spinner);
        imageView = findViewById(R.id.submit);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryName.countryNames));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String country = CountryName.countryNames[spinner.getSelectedItemPosition()];
                saveData(country);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("country", country);
                progressDialog.dismiss();
                startActivity(intent);
            }
        });
    }
    public void saveData(String data) {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT, data);
        editor.apply();
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();

    }

}