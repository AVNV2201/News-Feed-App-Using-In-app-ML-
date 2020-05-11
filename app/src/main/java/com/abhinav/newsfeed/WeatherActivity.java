package com.abhinav.newsfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherActivity extends AppCompatActivity {

    EditText cityEditText;

    public void getData(View v ){

        final String city = cityEditText.getText().toString() ;

        if( city.length() <= 0 ){
            Toast.makeText(WeatherActivity.this, "Enter city", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = ResourceHelper.WEATHER_URL + city + "&appid=" +  ResourceHelper.WEATHER_API_KEY;

        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject info) {

                        Log.i( "Sdisco::::::::::::::::::::::::", info.toString() );

                        try {

                            MainActivity.sharedPreferences.edit().putString("weather", city).apply();

                            TextView tempTextView = findViewById(R.id.TempTextView) ;
                            TextView mintempTextView = findViewById(R.id.minTempTextView) ;
                            TextView maxtempTextView = findViewById(R.id.maxTempTextView) ;
                            TextView cityNameTextView = findViewById(R.id.cityTextView) ;
                            TextView precipitationTextView = findViewById(R.id.precipitationTextView) ;
                            TextView descriptionTextView = findViewById(R.id.descriptionTextView) ;
                            TextView longitudeTextView = findViewById(R.id.longirudeTextView) ;
                            TextView latitudeTextView = findViewById(R.id.latitudeTextView) ;

                            String city = info.getString("name") ;
                            cityNameTextView.setText(city);

                            JSONObject coord = new JSONObject(info.getString("coord")) ;
                            double val = coord.getDouble("lon") ;
                            String lon = String.valueOf(Math.abs(val)) ;
                            if( val > 0 ) lon += " E" ;
                            else if( val < 0 ) lon += " W" ;
                            longitudeTextView.setText(lon);
                            val = coord.getDouble("lat") ;
                            String lat = String.valueOf(Math.abs(val)) ;
                            if( val > 0 ) lat += " N" ;
                            else if( val < 0 ) lat += " S" ;
                            latitudeTextView.setText(lat);

                            JSONObject temp = new JSONObject(info.getString("main")) ;
                            int i = (int)Math.round(temp.getDouble("temp") - 273 ) ;
                            String tmp = String.valueOf(i) + "° C" ;
                            tempTextView.setText(tmp);
                            i = (int)Math.round(temp.getDouble("temp_min") - 273 ) ;
                            tmp = "Min: " + String.valueOf(i) + "°C" ;
                            mintempTextView.setText(tmp);
                            i = (int)Math.round(temp.getDouble("temp_max") - 273 ) ;
                            tmp = "Max: " + String.valueOf(i) + "°C" ;
                            maxtempTextView.setText(tmp);

                            JSONArray arr = new JSONArray( info.getString("weather")) ;
                            JSONObject weather = arr.getJSONObject(0) ;
                            precipitationTextView.setText( weather.getString("main") );
                            descriptionTextView.setText( weather.getString("description") );

                            ImageView image = findViewById(R.id.imageView ) ;
                            if( weather.getString("main").equals("Rain") || weather.getString("main").equals("Driaale") )
                                image.setImageResource(R.drawable.rain);
                            else
                                image.setImageResource(R.drawable.clear);

                        } catch (Exception e) {
                            Toast.makeText(WeatherActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("failed:::::::::::::::::::::::::::",error.getMessage());
                        Toast.makeText(WeatherActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        cityEditText = findViewById(R.id.cityName) ;

        String city = MainActivity.sharedPreferences.getString("weather", "" );

        if( city.length() > 0 ){
            cityEditText.setText(city);
            getData(cityEditText);
        }
    }
}
