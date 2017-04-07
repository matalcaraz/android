package com.example.gv400208.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gv400208.myapplication.R;
import com.example.gv400208.myapplication.data.List;
import com.example.gv400208.myapplication.data.Meteo;
import com.example.gv400208.myapplication.data.Meteo2;
import com.example.gv400208.myapplication.data.Weather;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    java.util.List<String> listDataHeader;
    HashMap<String, java.util.List<List>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        double Lat = (double) getIntent().getExtras().get("Lat");
        double Lng = (double) getIntent().getExtras().get("Lng");
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        new DownloadWebPageTask2().execute("http://api.openweathermap.org/data/2.5/forecast?lat=" + Lat + "&lon=" + Lng + "&appid=d6234773e1b51a0555d3166a03097c61&units=metric&lang=fr");

    }

    private void prepareListData() {


    }

    private class DownloadWebPageTask2 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(urls[0]).build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Download failed";
        }


        @Override
        protected void onPostExecute(String result) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Gson gson = new Gson();
                Meteo2 meteo = gson.fromJson(result, Meteo2.class);
                Date defaultDate = null;
                listDataHeader = new ArrayList<String>();

                TextView tvJour = ((TextView)findViewById(R.id.Ville));
                tvJour.setText( meteo.getCity().getName() + "");

                listDataChild = new HashMap<String, java.util.List<List>>();
                String header = null;
                ArrayList<List> listeMeteoJours = new ArrayList<List>();

                for (List list : meteo.getList()) {
                    if(defaultDate == null){
                        defaultDate = new Date(format.parse(list.getDt_txt()).getTime());
                        header = list.getDt_txt();
                        listDataHeader.add(header);

                    }else if(defaultDate.getDay() != new Date(format.parse(list.getDt_txt()).getTime()).getDay()){
                        listDataHeader.add(header);
                        listDataChild.put(header, listeMeteoJours);
                        header = list.getDt_txt();
                        defaultDate = new Date(format.parse(list.getDt_txt()).getTime());
                        listeMeteoJours = new ArrayList<List>();
                        listeMeteoJours.add(list);
                    }else{
                        listeMeteoJours.add(list);
                    }
                }
                listDataHeader.add(header);
                listDataChild.put(header, listeMeteoJours);

                ArrayList list = new ArrayList(listDataChild.keySet());
                Collections.sort(list);
                listAdapter = new ExpandableListAdapter(ListActivity.this, list, listDataChild);

                // setting list adapter
                expListView.setAdapter(listAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
