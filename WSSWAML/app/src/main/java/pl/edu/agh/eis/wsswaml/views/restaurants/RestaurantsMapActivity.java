package pl.edu.agh.eis.wsswaml.views.restaurants;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import pl.edu.agh.eis.wsswaml.R;
import pl.edu.agh.eis.wsswaml.models.Restaurant;

public class RestaurantsMapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ArrayList<Restaurant> restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(getApplicationContext(), "Map is null :(", Toast.LENGTH_SHORT).show();
            finish();
        }

        restaurants = getIntent().getParcelableArrayListExtra("restaurants");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (Restaurant restaurant : restaurants) {
            LatLng restaurantLocation = new LatLng(restaurant.location.getLatitude(), restaurant.location.getLongitude());
            MarkerOptions marker = new MarkerOptions()
                    .position(restaurantLocation)
                    .title(restaurant.name)
                    .snippet(restaurant.cuisines);
            mMap.addMarker(marker).setTag(restaurant);
        }

        Restaurant restaurant = restaurants.get(0);
        LatLng restaurantLocation = new LatLng(restaurant.location.getLatitude(), restaurant.location.getLongitude());
        float zoomLevel = 16.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantLocation, zoomLevel));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Restaurant restaurant = (Restaurant) marker.getTag();
        Intent intent = new Intent(this, RestaurantsMapActivity.class);
        intent.putExtra("restaurants", restaurants);
        startActivity(intent);
        return false;
    }
}
