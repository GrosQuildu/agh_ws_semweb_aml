package pl.edu.agh.eis.wsswaml.views.restaurants;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.eis.wsswaml.R;
import pl.edu.agh.eis.wsswaml.models.Restaurant;

public class FindRestaurantListActivity extends AppCompatActivity {

    private JSONObject json;
    private List<Restaurant> restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_restaurant_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String restaurants = getIntent().getStringExtra("data");
        GetRestaurants(restaurants);
    }

    private void GetRestaurants(String data) {
        if (data == null) return;
        Restaurant restaurant;
        this.restaurants = new ArrayList<>();
        try {
            JSONArray jsonarray = new JSONArray(data);
            for (int i = 0; i < jsonarray.length(); i++) {
                restaurant = new Restaurant();
                JSONObject restaurantJson = jsonarray.getJSONObject(i).getJSONObject("restaurant");
                restaurant.name = restaurantJson.getString("name");
                restaurant.url = restaurantJson.getString("url");
                restaurant.location = restaurantJson.getJSONObject("location").getString("address");
                restaurant.cuisines = restaurantJson.getString("cuisines");
                restaurant.timings = restaurantJson.getString("timings");
                restaurant.id = restaurantJson.getString("id");

                //JSONArray photos = jsonobject.getJSONObject("restaurant").getJSONObject("photos").getJSONArray("photos");
                //JSONObject photoObject = photos.getJSONObject(0);
                restaurant.photoUrl = "atrapa";//photoObject.getJSONObject("photo").getString("url");

                this.restaurants.add(restaurant);
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error parsing Zomato restaurants", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}