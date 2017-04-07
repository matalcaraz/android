package com.example.gv400208.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gv400208.myapplication.data.City;
import com.example.gv400208.myapplication.data.List;
import com.example.gv400208.myapplication.data.Meteo;
import com.example.gv400208.myapplication.data.Meteo2;
import com.example.gv400208.myapplication.data.Weather;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cinqjours);
        double Lat = (double) getIntent().getExtras().get("Lat");
        double Lng = (double) getIntent().getExtras().get("Lng");
        Log.d("onCreate","Je suis la");
        new DownloadWebPageTask2().execute("http://api.openweathermap.org/data/2.5/forecast?lat=" + Lat + "&lon=" + Lng + "&appid=d6234773e1b51a0555d3166a03097c61&units=metric&lang=fr");
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

            try {
                Gson gson = new Gson();
                Meteo2 meteo = gson.fromJson(result, Meteo2.class);
                List[] list = meteo.getList();
                TextView tvVille = ((TextView) findViewById(R.id.Ville));

                ImageView ImageJ1 = ((ImageView) findViewById(R.id.imageJ1));
                TextView tvJ1Temp = ((TextView) findViewById(R.id.J1Temp));
                TextView tvJ1Hum = ((TextView) findViewById(R.id.J1Hum));
                TextView tvJ1Wind = ((TextView) findViewById(R.id.J1Wind));
                TextView tvJ1 = ((TextView) findViewById(R.id.J1));


                ImageView ImageJ1pm = ((ImageView) findViewById(R.id.imageJ1pm));
                TextView tvJ1Temppm = ((TextView) findViewById(R.id.J1Temppm));
                TextView tvJ1Humpm = ((TextView) findViewById(R.id.J1Humpm));
                TextView tvJ1Windpm = ((TextView) findViewById(R.id.J1Windpm));
                TextView tvJ1pm = ((TextView) findViewById(R.id.J1pm));

                ImageView ImageJ2 = ((ImageView) findViewById(R.id.imageJ2));
                TextView tvJ2Temp = ((TextView) findViewById(R.id.J2Temp));
                TextView tvJ2Hum = ((TextView) findViewById(R.id.J2Hum));
                TextView tvJ2Wind = ((TextView) findViewById(R.id.J2Wind));
                TextView tvJ2 = ((TextView) findViewById(R.id.J2));

                ImageView ImageJ3 = ((ImageView) findViewById(R.id.imageJ3));
                TextView tvJ3Temp = ((TextView) findViewById(R.id.J3Temp));
                TextView tvJ3Hum = ((TextView) findViewById(R.id.J3Hum));
                TextView tvJ3Wind = ((TextView) findViewById(R.id.J3Wind));
                TextView tvJ3 = ((TextView) findViewById(R.id.J3));

                ImageView ImageJ4 = ((ImageView) findViewById(R.id.imageJ4));
                TextView tvJ4Temp = ((TextView) findViewById(R.id.J4Temp));
                TextView tvJ4Hum = ((TextView) findViewById(R.id.J4Hum));
                TextView tvJ4Wind = ((TextView) findViewById(R.id.J4Wind));
                TextView tvJ4 = ((TextView) findViewById(R.id.J4));

                ImageView ImageJ5 = ((ImageView) findViewById(R.id.imageJ5));
                TextView tvJ5Temp = ((TextView) findViewById(R.id.J5Temp));
                TextView tvJ5Hum = ((TextView) findViewById(R.id.J5Hum));
                TextView tvJ5Wind = ((TextView) findViewById(R.id.J5Wind));
                TextView tvJ5 = ((TextView) findViewById(R.id.J5));

                tvVille.setText(meteo.getCity().getName());


                tvJ1Temp.setText( meteo.getList()[0].getMain().getTemp() + "°C" );
                tvJ1Hum.setText( meteo.getList()[0].getMain().getHumidity() + "%" );
                tvJ1Wind.setText( meteo.getList()[0].getWind().getSpeed() + " m/s");
                tvJ1.setText(meteo.getList()[0].getDt_txt() );
                switch (meteo.getList()[0].getWeather()[0].getIcon()) {
                    case "01d":
                        ImageJ1.setImageDrawable(getDrawable(R.drawable.a01d));
                        break;
                    case "02d":
                        ImageJ1.setImageDrawable(getDrawable(R.drawable.a02d));
                    case "03d":
                        ImageJ1.setImageDrawable(getDrawable(R.drawable.a03d));
                        break;
                    case "04d":
                        ImageJ1.setImageDrawable(getDrawable(R.drawable.a04d));
                        break;
                    case "09d":
                        ImageJ1.setImageDrawable(getDrawable(R.drawable.a09d));
                        break;
                    case "10d":
                        ImageJ1.setImageDrawable(getDrawable(R.drawable.a10d));
                        break;
                    case "11d":
                        ImageJ1.setImageDrawable(getDrawable(R.drawable.a11d));
                        break;
                    case "13d":
                        ImageJ1.setImageDrawable(getDrawable(R.drawable.a13d));
                        break;
                    case "50d":
                        ImageJ1.setImageDrawable(getDrawable(R.drawable.a50d));
                        break;

                }

                tvJ1Temppm.setText( meteo.getList()[4].getMain().getTemp() + "°C" );
                tvJ1Humpm.setText( meteo.getList()[4].getMain().getHumidity() + "%" );
                tvJ1Windpm.setText( meteo.getList()[4].getWind().getSpeed() + " m/s");
                tvJ1pm.setText(meteo.getList()[4].getDt_txt() );

                tvJ2Temp.setText( meteo.getList()[8].getMain().getTemp() + "°C" );
                tvJ2Hum.setText(meteo.getList()[8].getMain().getHumidity() + "%");
                tvJ2Wind.setText(meteo.getList()[8].getWind().getSpeed() + " m/s");
                tvJ2.setText(meteo.getList()[8].getDt_txt() );

                tvJ3Temp.setText(meteo.getList()[16].getMain().getTemp() + "°C" );
                tvJ3Hum.setText(meteo.getList()[16].getMain().getHumidity() + "%");
                tvJ3Wind.setText(meteo.getList()[16].getWind().getSpeed() + " m/s");
                tvJ3.setText(meteo.getList()[16].getDt_txt());

                tvJ4Temp.setText(meteo.getList()[24].getMain().getTemp() + "°C" );
                tvJ4Hum.setText(meteo.getList()[24].getMain().getHumidity() + "%");
                tvJ4Wind.setText(meteo.getList()[24].getWind().getSpeed() + " m/s");
                tvJ4.setText(meteo.getList()[24].getDt_txt() );

                tvJ5Temp.setText(meteo.getList()[32].getMain().getTemp() + "°C" );
                tvJ5Hum.setText(meteo.getList()[32].getMain().getHumidity() + "%");
                tvJ5Wind.setText(meteo.getList()[32].getWind().getSpeed() + " m/s");
                tvJ5.setText(meteo.getList()[32].getDt_txt() );

                //TODO J'affiche sur l'ui....
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
