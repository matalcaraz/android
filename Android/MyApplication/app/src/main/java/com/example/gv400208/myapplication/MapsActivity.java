package com.example.gv400208.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gv400208.myapplication.data.Meteo;
import com.example.gv400208.myapplication.data.Weather;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener
{
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    float zoom = 16;
    boolean z = true;
    GoogleMap mMap;
    Double Lat;
    Double Lng;
    LatLng pos;
    Marker m = null;
    boolean isRestore = false;
    private Bundle savedInstanceState = null;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("lat", Lat);
        outState.putDouble("lng", Lng);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
                LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                // Register the listener with the Location Manager to receive location updates
            if( savedInstanceState == null ) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 100, this);
            }
        }
        if( savedInstanceState != null ) {
            this.savedInstanceState = savedInstanceState;
        }

    }


    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setMyLocationEnabled(true);
        mMap.setInfoWindowAdapter(new MyInfoWindowAdapter());

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent appel = new Intent(MapsActivity.this, ListActivity.class);
                appel.putExtra("Lat",Lat);
                appel.putExtra("Lng",Lng);
                startActivity(appel);
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            public void onMapClick(LatLng point) {
                mMap.clear();
                Lat=point.latitude;
                Lng=point.longitude;
                pos=point;
                new DownloadWebPageTask().execute("http://api.openweathermap.org/data/2.5/weather?lat=" + Lat + "&lon=" + Lng + "&appid=d6234773e1b51a0555d3166a03097c61&units=metric&lang=fr");

            }
        });


        if( savedInstanceState != null ) {
            Lat = savedInstanceState.getDouble("lat");
            Lng = savedInstanceState.getDouble("lng");
            LatLng location = new LatLng(Lat, Lng);
            onLocationChanged(location);

        }


    }

    public void onLocationChanged(LatLng latLng){
        mMap.clear();
        pos = latLng;
        Lat = latLng.latitude;
        Lng = latLng.longitude;
        new DownloadWebPageTask().execute("http://api.openweathermap.org/data/2.5/weather?lat=" + Lat + "&lon=" + Lng + "&appid=d6234773e1b51a0555d3166a03097c61&units=metric&lang=fr");
        if (z == true) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
            z = false;
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    @Override
    public void onLocationChanged(Location location) {

            Log.d("onLocationChanged", "Lat : " + location.getLatitude() + ",Lng : " + location.getLongitude());
            LatLng myposition = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.clear();
            pos = myposition;
            Lat = location.getLatitude();
            Lng = location.getLongitude();
            new DownloadWebPageTask().execute("http://api.openweathermap.org/data/2.5/weather?lat=" + Lat + "&lon=" + Lng + "&appid=d6234773e1b51a0555d3166a03097c61&units=metric&lang=fr");
            if (z == true) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myposition, zoom));
                z = false;
            } else {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(myposition));
            }

        }






    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                    // Register the listener with the Location Manager to receive location updates
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 100, this);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    MapsActivity.this.finish();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
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
                Meteo meteo = gson.fromJson(result, Meteo.class);
                Weather[] weather = meteo.getWeather();

                // Add a marker in myposition, and move the camera.
                Marker mypos = mMap.addMarker(new MarkerOptions()
                        .position(pos)
                );
                mypos.setTag(meteo);
                //mypos.showInfoWindow();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView, myContentsView2;

        MyInfoWindowAdapter() {
            myContentsView = getLayoutInflater().inflate(R.layout.infos, null);
            myContentsView2 = getLayoutInflater().inflate(R.layout.sansinfos, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }



        @Override
        public View getInfoContents(Marker marker) {

            if(marker.getTag()!=null){
                Meteo meteo= (Meteo) marker.getTag();
                TextView tvname = ((TextView) myContentsView.findViewById(R.id.name));
                ImageView Image = ((ImageView) myContentsView.findViewById(R.id.image));
                TextView tvWind = ((TextView) myContentsView.findViewById(R.id.wind));
                TextView tvTemp = ((TextView) myContentsView.findViewById(R.id.temperature));
                TextView tvHum = ((TextView) myContentsView.findViewById(R.id.humidite));
                tvname.setText(meteo.getName());
                tvTemp.setText("Il fait : " + meteo.getMain().getTemp() + "°C" );
                tvHum.setText("Humidité : " + meteo.getMain().getHumidity() + "%" );
                tvWind.setText("La vitesse du vent est de : " + meteo.getWind().getSpeed() + " m/s");
                switch (meteo.getWeather()[0].getIcon()) {
                    case "01d":
                        Image.setImageDrawable(getDrawable(R.drawable.a01d));
                        break;
                    case "02d":
                        Image.setImageDrawable(getDrawable(R.drawable.a02d));
                    case "03d":
                        Image.setImageDrawable(getDrawable(R.drawable.a03d));
                        break;
                    case "04d":
                        Image.setImageDrawable(getDrawable(R.drawable.a04d));
                        break;
                    case "09d":
                        Image.setImageDrawable(getDrawable(R.drawable.a09d));
                        break;
                    case "10d":
                        Image.setImageDrawable(getDrawable(R.drawable.a10d));
                        break;
                    case "11d":
                        Image.setImageDrawable(getDrawable(R.drawable.a11d));
                        break;
                    case "13d":
                        Image.setImageDrawable(getDrawable(R.drawable.a13d));
                        break;
                    case "50d":
                        Image.setImageDrawable(getDrawable(R.drawable.a50d));
                        break;

                }

                return myContentsView;
            }else {
                TextView tvTitle = ((TextView) myContentsView.findViewById(R.id.probleme));
                tvTitle.setText("Impossible de charger la météo");
                return myContentsView2;
            }

        }
    }
}




