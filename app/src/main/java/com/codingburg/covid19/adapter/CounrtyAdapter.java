package com.codingburg.covid19.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codingburg.covid19.R;
import com.codingburg.covid19.activity.detailesActivity;
import com.codingburg.covid19.model.Data;

import java.util.List;

public class CounrtyAdapter  extends RecyclerView.Adapter<CounrtyAdapter.CounrtyViewHolder> {


    private Context mCtx;
    private List<Data> counrtyList;

    public CounrtyAdapter(Context mCtx, List<Data> counrtyList) {
        this.mCtx = mCtx;
        this.counrtyList = counrtyList;
    }

    @Override
    public CounrtyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.allcounrty_list, null);
        return new CounrtyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CounrtyViewHolder holder, int position) {
        Data getData = counrtyList.get(position);
        holder.name.setText(getData.getCountry());
        holder.cases.setText(getData.getCases());
        holder.deaths.setText(getData.getDeaths());
        holder.todayCases.setText(getData.getTodayCases());
        holder.todayDeaths.setText(getData.getTodayDeaths());
        holder.country.setText(getData.getCountry());
        Glide.with(mCtx).load(getData.getFlag()).into(holder.flag);

    }

    @Override
    public int getItemCount() {
        return counrtyList.size();
    }

    class CounrtyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name, cases, deaths, todayCases, todayDeaths, country;
               ImageView flag;


        public CounrtyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            cases = itemView.findViewById(R.id.cases);
            deaths = itemView.findViewById(R.id.deaths);
            todayCases = itemView.findViewById(R.id.todayCases);
            todayDeaths = itemView.findViewById(R.id.todayDeaths);
            flag = itemView.findViewById(R.id.flag);
            country = (TextView) itemView.findViewById(R.id.country);
        }

        @Override
        public void onClick(View v) {
            System.out.println("adfzsdcbkjvhbzhjvbzjhb");
            Intent intent = new Intent(mCtx.getApplicationContext(), detailesActivity.class);
            intent.putExtra("country", country.getText().toString());
            mCtx.startActivity(intent);
        }
    }
}