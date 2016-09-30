package com.lovemyweather.lovemyweather;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.zip.Inflater;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lovemyweather.lovemyweather.Entity.WeatherList;
import com.lovemyweather.lovemyweather.Services.ServicesAPI;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailActivity extends Fragment {
    TextView cityField;
    ImageView weatherIcon;
    TextView conditionField;
    TextView timeField;
    TextView windModerateBreezeField;
    TextView windWestNorhWestField;
    TextView cloudinessField;
    TextView pressureField;
    TextView humidityField;
    TextView sunriseField;
    TextView sunsetField;
    TextView geoCoordsField;
    TextView temperatureField;
    TextView countryField;

    ServicesAPI servicesAPI;
    LayoutInflater inflater;

    LocationManager locationManager;
    LocationListener locationListener;

    WeatherList weatherList;

    ProgressDialog progressDialog;
    AlertDialog.Builder builder;

    String method;
    String queryToolBar;
    double currentLatitude;
    double currentLongitude;

    Gson gson;
    Type type;

    public static String tes = "";
    public static final String TAG = "DetailActivity";//set the TAG
    private static final String PATH_FOTO ="http://openweathermap.org/img/w/";//url path for icon weather

    public DetailActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        init(rootView);//init all the text field

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(inflater.getContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        if (!ImageLoader.getInstance().isInited()) {
            ImageLoader.getInstance().init(config);
        }
        //init imageLoader

        Bundle b = getArguments();
        method = b.getString("method");//method is for know what button is being pressed

        locationManager = (LocationManager) (inflater.getContext().getSystemService(Context.LOCATION_SERVICE));//get location

        currentLatitude = 0.00;
        currentLongitude = 0.00;

        gson = new Gson();
        type = new TypeToken<WeatherList>() {}.getType();//type initial untuk class weatherlist
        servicesAPI = ServicesAPI.Factory.getInstance(inflater.getContext());

        SetProgressDialog(inflater.getContext());
        return rootView;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
//        builder.create().dismiss();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if(method.equals("place")) {
            MenuItem mSearchMenuItem = menu.findItem(R.id.menu_search);
            mSearchMenuItem.setVisible(true);
           mSearchMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
               @Override
               public boolean onMenuItemClick(MenuItem menuItem) {
                   showInputDialog();
                   return true;
               }
           });
        }
    }

   /* private void ShowCustomDialog(){
        SampleDialogFragment fragment
                = SampleDialogFragment.newInstance(
                7,
                5,
                true,
                true,true,true
        );
        fragment.show(getFragmentManager(), "blur_sample");

        Log.i(TAG, "ShowCustomDialog: "+tes);
    }*/
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

       if(method.equals("geo")) {
            GetCurrentLocation();
        }
        else if(method.equals("place")){
           showInputDialog();
        }

    }
    private void GetCurrentLocation()
    {

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();
                locationManager.removeUpdates(locationListener);
                getDataByGeo();
                Log.i(TAG, "onLocationChanged: "+currentLatitude+currentLongitude);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Toast.makeText(inflater.getContext(),"Unabled to get the GPS",Toast.LENGTH_LONG).show();
                if(progressDialog.isShowing())
                progressDialog.dismiss();
                getFragmentManager().popBackStack();
            }
        };
        if (ActivityCompat.checkSelfPermission(inflater.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(inflater.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET
            }, 10);

        } else {
                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
        }


    }
    private void getDataByGeo(){
        Log.e(TAG, "onActivityCreated: "+currentLongitude );
        if(currentLongitude != 0.00) {
            Log.i(TAG, "getDataByGeo: "+currentLatitude + "lon : "+currentLongitude);
            //call the interface service API for retrofit
            servicesAPI.getWeatherListByGeo("metric",
                   currentLatitude, currentLongitude).enqueue(new Callback<WeatherList>() {
                @Override
                public void onResponse(Call<WeatherList> call, Response<WeatherList> response) {
                    //jika pemanggilan berhasil
                    progressDialog.dismiss();
                    String json = gson.toJson(response.body());
                    weatherList = gson.fromJson(json, type);
                    //data feedback dari retrofit diconvert ke json kemudian dimasukkan ke dalam class weaterList
                    setText(weatherList);
                    //set semua data

                }

                @Override
                public void onFailure(Call<WeatherList> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.toString());
                    Toast.makeText(inflater.getContext(), "Kota tidak ada", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    //jika data kosong atau ada kesalahan,maka akan ada feedback untuk error tersebut
                }
            });
        }
    }
    private void setText(WeatherList list){
        try {

            cityField.setText(list.getName());
            countryField.setText(list.getSys().getCountry());
            geoCoordsField.setText("[" + list.getCoord().getLat() + "," + list.getCoord().getLon() + "]");
            humidityField.setText(list.getMain().getHumidity() + " %");
            pressureField.setText(list.getMain().getPressure() + " hpa");
            temperatureField.setText(String.format("%.2f", list.getMain().getTemp()) + " \u00B0 C");
            windModerateBreezeField.setText("Moderate breeze" + list.getWind().getSpeed() + "m/s ");
            windWestNorhWestField.setText("West-norhwest (" + list.getWind().getDeg() + ")");
            conditionField.setText(list.getWeather().get(0).getMain());
            cloudinessField.setText(list.getWeather().get(0).getDescription());

            Date sunsetDate = new Date(list.getSys().getSunset());
            Date sunriseDate = new Date(list.getSys().getSunrise());
            SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
            String timeSunset = localDateFormat.format(sunsetDate);
            String timeSunrise = localDateFormat.format(sunriseDate);
            sunriseField.setText(timeSunrise);
            sunsetField.setText(timeSunset);
            String updateOn = new SimpleDateFormat().format(Calendar.getInstance().getTime());
            timeField.setText(updateOn);

            ImageLoader.getInstance().displayImage(PATH_FOTO+list.getWeather().get(0).getIcon()+".png", weatherIcon);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private void SetProgressDialog(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Fench Data");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }
    private void init(View rootView){
        cityField = (TextView) rootView.findViewById(R.id.txtCity);
        countryField = (TextView) rootView.findViewById(R.id.txtCountry);
        weatherIcon = (ImageView) rootView.findViewById(R.id.imageWeather);
        conditionField = (TextView) rootView.findViewById(R.id.txtCondition);
        timeField = (TextView) rootView.findViewById(R.id.txtCurrentTime);
        windModerateBreezeField = (TextView) rootView.findViewById(R.id.txtWindModerateBreeze);
        windWestNorhWestField = (TextView) rootView.findViewById(R.id.txtWindWestNorhWest);
        cloudinessField = (TextView) rootView.findViewById(R.id.txtCloudiness);
        pressureField = (TextView) rootView.findViewById(R.id.txtPlessure);
        humidityField = (TextView) rootView.findViewById(R.id.txtHumidity);
        sunriseField = (TextView) rootView.findViewById(R.id.txtSunrise);
        sunsetField = (TextView) rootView.findViewById(R.id.txtSunset);
        geoCoordsField = (TextView) rootView.findViewById(R.id.txtGeoCoords);
        temperatureField = (TextView) rootView.findViewById(R.id.txtCelcius);
    }

    private void showInputDialog(){
     builder = new AlertDialog.Builder(inflater.getContext());
        builder.setTitle("Change City");

        final EditText input = new EditText(inflater.getContext());
        input.setHint("Masukkan Nama Kota");
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ChangeCity(input.getText().toString());
            }
        });
        builder.show();
       /* ShowCustomDialog();
        ChangeCity("Yogyakarta");*/
    }
    private void ChangeCity(String city)
    {
        //call the interface service API for retrofit
        servicesAPI.getWeatherListByInput(city, "metric").enqueue(new Callback<WeatherList>() {
            @Override
            public void onResponse(Call<WeatherList> call, Response<WeatherList> response) {
                //jika pemanggilan berhasil
                progressDialog.dismiss();

                String json = gson.toJson(response.body());
                weatherList = gson.fromJson(json, type);
                //data feedback dari retrofit diconvert ke json kemudian dimasukkan ke dalam class weaterList
                Log.e(TAG, "onResponse: "+weatherList.getCod() );
                if(weatherList.getCod() == 404)
                {
                    Toast.makeText(inflater.getContext(), "Kota tidak ada", Toast.LENGTH_SHORT).show();
                    showInputDialog();
                }
                else
                {
                     setText(weatherList);//set semua data
                }


            }

            @Override
            public void onFailure(Call<WeatherList> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(inflater.getContext(), "Kota tidak ada", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                //jika data kosong atau ada kesalahan,maka akan ada feedback untuk error tersebut
            }
        });
    }

}
