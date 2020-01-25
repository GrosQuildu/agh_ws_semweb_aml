package pl.edu.agh.eis.wsswaml;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.eis.wsswaml.models.Restaurant;

public class RestaurantsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private JSONObject json;
    private List<Restaurant> restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
