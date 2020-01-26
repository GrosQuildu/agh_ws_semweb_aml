package pl.edu.agh.eis.wsswaml.views.restaurants;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import pl.edu.agh.eis.wsswaml.R;
import pl.edu.agh.eis.wsswaml.models.Restaurant;

public class RestaurantsListActivity extends AppCompatActivity implements OnItemClickListener {
    private RecyclerView restaurantsRecyclerView;
    private RestaurantsAdapter mAdapter;
    private ArrayList<Restaurant> restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_restaurant_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        restaurantsRecyclerView = findViewById(R.id.restaurants_recycler_view);
        //cuisinesRecyclerView.setHasFixedSize(true);
        restaurantsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        String restaurantsJson = getIntent().getStringExtra("data");
        this.restaurants = getRestaurants(restaurantsJson);

        mAdapter = new RestaurantsAdapter(restaurants, this);
        restaurantsRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClicked(View view, Restaurant restaurant) {
        Intent restaurantDescriptionIntent = new Intent(this, RestaurantDescriptionActivity.class);
        restaurantDescriptionIntent.putExtra("RestaurantEntityID", restaurant.id);
        this.startActivity(restaurantDescriptionIntent);
    }

    public void showOnMap(View view) {
        Intent intent = new Intent(this, RestaurantsMapActivity.class);
        intent.putExtra("restaurants", restaurants);
        startActivity(intent);
    }

    private ArrayList<Restaurant> getRestaurants(String data) {
        if (data == null)
            return null;

        ArrayList<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant;
        try {
            JSONArray jsonarray = new JSONArray(data);
            for (int i = 0; i < jsonarray.length(); i++) {
                restaurant = new Restaurant();
                JSONObject restaurantJson = jsonarray.getJSONObject(i).getJSONObject("restaurant");
                restaurant.name = restaurantJson.getString("name");
                restaurant.url = restaurantJson.getString("deeplink");
                restaurant.address = restaurantJson.getJSONObject("location").getString("address");
                restaurant.cuisines = restaurantJson.getString("cuisines");
                restaurant.timings = restaurantJson.getString("timings");
                restaurant.id = restaurantJson.getString("id");
                restaurant.photoUrl = restaurantJson.getString("featured_image");
                restaurant.rating = restaurantJson.getJSONObject("user_rating").getDouble("aggregate_rating");
                restaurant.ratingText = restaurantJson.getJSONObject("user_rating").getString("rating_text");
                restaurant.location = new Location(restaurant.address);
                restaurant.location.setLongitude(Double.valueOf(restaurantJson.getJSONObject("location").getString("longitude")));
                restaurant.location.setLatitude(Double.valueOf(restaurantJson.getJSONObject("location").getString("latitude")));
                restaurants.add(restaurant);
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error parsing Zomato restaurants", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return restaurants;
    }
}
