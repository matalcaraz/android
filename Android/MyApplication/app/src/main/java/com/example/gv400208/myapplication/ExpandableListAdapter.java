package com.example.gv400208.myapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gv400208.myapplication.data.List;
import com.example.gv400208.myapplication.data.Meteo2;
import com.example.gv400208.myapplication.data.Weather;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


import static android.R.attr.format;

/**
 * Created by gv400208 on 31/03/2017.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    //Date Format ...
    static SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat sdDisplay = new SimpleDateFormat("dd/MM/yyyy");


    private Context _context;
    private java.util.List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, java.util.List<List>> _listDataChild;

    public ExpandableListAdapter(Context context, ArrayList<String> listDataHeader, HashMap<String, java.util.List<List>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        String header_key = this._listDataHeader.get(groupPosition);
        java.util.List<List> weathers = this._listDataChild.get(header_key);
        return weathers.get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final List childText = (List) getChild(groupPosition, childPosition);

        if (convertView == null) {

            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.listitem, null);
        }

        //TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
        TextView tvHour = ((TextView) convertView.findViewById(R.id.hour));
        ImageView ImageJ1 = ((ImageView) convertView.findViewById(R.id.image));
        TextView tvTemp = ((TextView) convertView.findViewById(R.id.temp));
        TextView tvHum = ((TextView) convertView.findViewById(R.id.hum));
        TextView tvWind = ((TextView) convertView.findViewById(R.id.wind));

        //txtListChild.setText(childText.getDescription());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int hour = 0;
        try {
            hour = format.parse(childText.getDt_txt()).getHours();
            tvHour.setText(hour + ":00");
            tvTemp.setText("Température: " + childText.getMain().getTemp() + " °C");
            tvHum.setText("Humidité: " + childText.getMain().getHumidity() + " %");
            tvWind.setText("Vent: " + childText.getWind().getSpeed() + " m/s");
            switch (childText.getWeather()[0].getIcon()) {
                case "01d":
                    ImageJ1.setImageDrawable(convertView.getResources().getDrawable(R.drawable.a01d));
                    break;
                case "02d":
                    ImageJ1.setImageDrawable(convertView.getResources().getDrawable(R.drawable.a02d));
                case "03d":
                    ImageJ1.setImageDrawable(convertView.getResources().getDrawable(R.drawable.a03d));
                    break;
                case "04d":
                    ImageJ1.setImageDrawable(convertView.getResources().getDrawable(R.drawable.a04d));
                    break;
                case "09d":
                    ImageJ1.setImageDrawable(convertView.getResources().getDrawable(R.drawable.a09d));
                    break;
                case "10d":
                    ImageJ1.setImageDrawable(convertView.getResources().getDrawable(R.drawable.a10d));
                    break;
                case "11d":
                    ImageJ1.setImageDrawable(convertView.getResources().getDrawable(R.drawable.a11d));
                    break;
                case "13d":
                    ImageJ1.setImageDrawable(convertView.getResources().getDrawable(R.drawable.a13d));
                    break;
                case "01n":
                    ImageJ1.setImageDrawable(convertView.getResources().getDrawable(R.drawable.a01n));
                    break;
                case "02n":
                    ImageJ1.setImageDrawable(convertView.getResources().getDrawable(R.drawable.a02n));
                case "03n":
                    ImageJ1.setImageDrawable(convertView.getResources().getDrawable(R.drawable.a03n));
                    break;
                case "04n":
                    ImageJ1.setImageDrawable(convertView.getResources().getDrawable(R.drawable.a04n));
                    break;
                case "09n":
                    ImageJ1.setImageDrawable(convertView.getResources().getDrawable(R.drawable.a09n));
                    break;
                case "10n":
                    ImageJ1.setImageDrawable(convertView.getResources().getDrawable(R.drawable.a10n));
                    break;
                case "11n":
                    ImageJ1.setImageDrawable(convertView.getResources().getDrawable(R.drawable.a11n));
                    break;
                case "13n":
                    ImageJ1.setImageDrawable(convertView.getResources().getDrawable(R.drawable.a13n));
                    break;
                case "50n":
                case "50d":
                    ImageJ1.setImageDrawable(convertView.getResources().getDrawable(R.drawable.a50d));
                    break;

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertView;
    }



    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.listgroup, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.Jour);
        lblListHeader.setTypeface(null, Typeface.BOLD);

        String[] sdate  = headerTitle.split(" ");
        String[] cutDate  = sdate[0].split("-");
        String maDate = cutDate[2] + "/" + cutDate[1] + "/" + cutDate[0];
        lblListHeader.setText(maDate);

/*
        try {
            Date date = sd.parse(headerTitle);
            lblListHeader.setText(sdDisplay.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            lblListHeader.setText(headerTitle);
        }
*/

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}