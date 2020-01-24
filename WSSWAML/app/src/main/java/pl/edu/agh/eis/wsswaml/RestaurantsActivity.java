package pl.edu.agh.eis.wsswaml;

import android.app.VoiceInteractor;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import pl.edu.agh.eis.wsswaml.models.Restaurant;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsActivity extends AppCompatActivity {

    private JSONObject json;
    private List<Restaurant> restaurants;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String restaurants = getIntent().getStringExtra("data");
        GetRestaurants(restaurants);


    }

    private void GetRestaurants(String data) {

        if (data == null) return;
        Restaurant restaurant;
        this.restaurants = new ArrayList<>();
        try {
            JSONArray jsonarray = new JSONArray(data);
            for(int i=0; i < jsonarray.length(); i++) {
                restaurant = new Restaurant();
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                restaurant.name = jsonobject.getJSONObject("restaurant").getString("name");
                restaurant.url = jsonobject.getJSONObject("restaurant").getString("url");
                restaurant.location = jsonobject.getJSONObject("restaurant").getJSONObject("location").getString("address");
                restaurant.cuisines = jsonobject.getJSONObject("restaurant").getString("cuisines");
                restaurant.timings = jsonobject.getJSONObject("restaurant").getString("timings");
                restaurant.id = jsonobject.getJSONObject("restaurant").getString("id");

                //JSONArray photos = jsonobject.getJSONObject("restaurant").getJSONObject("photos").getJSONArray("photos");
                //JSONObject photoObject = photos.getJSONObject(0);
                restaurant.photoUrl = "atrapa";//photoObject.getJSONObject("photo").getString("url");

                this.restaurants.add(restaurant);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
