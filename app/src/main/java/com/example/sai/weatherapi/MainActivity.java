package com.example.sai.weatherapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView temptxt;
    private TextView descript;
    private RequestQueue requestQueue;
    private TextView unitxt;
    private TextView date;
    private TextView city;
    private ImageView image;
    private Animation anime;
    private EditText getcity;
    private Button getbtn;
    private String apiKey;
    private String cityname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temptxt = findViewById(R.id.temp);
        unitxt = findViewById(R.id.unit);
        date = findViewById(R.id.date);
        city = findViewById(R.id.city);
        image = findViewById(R.id.img);
        getcity = findViewById(R.id.enter);
        getbtn = findViewById(R.id.getbtn);
        descript = findViewById(R.id.desc);

        anime = AnimationUtils.loadAnimation(this,R.anim.fade);

        apiKey ="d6d247b103e3c90e264ca3256aca15a7";

        requestQueue = Volley.newRequestQueue(this);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE-MM-dd");
        String formattedDate = simpleDateFormat.format(calendar.getTime());
        date.setText(formattedDate);
        getbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                giveWeather();
            }
        });

    }
//https://api.openweathermap.org/data/2.5/weather?q=akola&appid=d6d247b103e3c90e264ca3256aca15a7
    private void giveWeather() {
        cityname = getcity.getText().toString();
        if (cityname.isEmpty()){
            Toast.makeText(this, "Empty city name!", Toast.LENGTH_SHORT).show();
        }else{
            String url="https://api.openweathermap.org/data/2.5/weather?q=" + cityname + "&appid=" + apiKey;

            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject mainObj = response.getJSONObject("main");
                        JSONArray jsonArray = response.getJSONArray("weather");
                        JSONObject obj = jsonArray.getJSONObject(0);

                        double tempKel =mainObj.getDouble("temp");
                        double tempInt = tempKel - 273.15;
                        tempInt = Math.round(tempInt);
                        int myTempCel = (int) tempInt;
                        String description = obj.getString("description");
                        temptxt.startAnimation(anime);
                        temptxt.setText(String.valueOf(myTempCel));
                        unitxt.startAnimation(anime);
                        unitxt.setText("C");
                        city.startAnimation(anime);
                        city.setText(cityname);
                        descript.startAnimation(anime);
                        descript.setText(description);

                        if(description.equals("clear sky")){
                            image.startAnimation(anime);
                            image.setBackgroundResource(R.drawable.sunny);
                        }else if(description.equals("smoke")){
                            image.startAnimation(anime);
                            image.setBackgroundResource(R.drawable.cloudy);
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Error loading data!", Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(request);
        }

    }
}
