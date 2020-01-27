package pl.edu.agh.eis.wsswaml.views.restaurants;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import pl.edu.agh.eis.wsswaml.ErrorActivity;
import pl.edu.agh.eis.wsswaml.HttpSingleton;
import pl.edu.agh.eis.wsswaml.R;
import pl.edu.agh.eis.wsswaml.data.Cuisines;
import pl.edu.agh.eis.wsswaml.localization.LocalizerServiceConnection;

public class RestaurantsFindSettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    static String TAG = "RestaurantsFindSettingsActivity";

    private EditText distanceInMetersText;
    private int chosenCuisine = -1;
    private int distance = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_find_settings);

        // setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // input - distance
        distanceInMetersText = findViewById(R.id.input_distance);
        distanceInMetersText.setText(Integer.toString(distance));

        // dropdown - cuisine type
        Spinner dropdown = findViewById(R.id.spinner_cuisines_options);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Cuisines.getAll());
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

        // find restaurant button
        Button findRestaurantButton = findViewById(R.id.btn_ok);
        findRestaurantButton.setOnClickListener(view -> {
            String value = distanceInMetersText.getText().toString();
            if (!value.isEmpty()) {
                distance = Integer.parseInt((distanceInMetersText.getText().toString()));
            }

            Location location = LocalizerServiceConnection.getInstance().getLocation();
            if (location == null) {
                Toast.makeText(this, "No localization", Toast.LENGTH_LONG).show();
                Intent errorIntent = new Intent(this, ErrorActivity.class);
                this.startActivity(errorIntent);
            }
            callApi(distance, chosenCuisine, location.getLongitude(), location.getLatitude());
        });

    }

    private void callApi(int radius, int cuisines, double lon, double lat) {
        HttpSingleton.getInstance(getApplicationContext()).waitForNetworkConnection();

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("developers.zomato.com")
                .appendPath("api")
                .appendPath("v2.1")
                .appendPath("search")
                .appendQueryParameter("entity_id", "255")
                .appendQueryParameter("entity_type", "city")
                .appendQueryParameter("count", "10")
                .appendQueryParameter("lat", Double.toString(lat))
                .appendQueryParameter("lon", Double.toString(lon))
                .appendQueryParameter("radius", Integer.toString(radius))
                .appendQueryParameter("sort", "real_distance");
        if (cuisines != -1)
            builder.appendQueryParameter("cuisines", Integer.toString(cuisines));
        String url = builder.build().toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null,
                        response -> {
                            try {
                                JSONArray restaurants = response.getJSONArray("restaurants");
                                Intent restaurantsIntent = new Intent(RestaurantsFindSettingsActivity.this, RestaurantsListActivity.class);
                                restaurantsIntent.putExtra("data", restaurants.toString());
                                RestaurantsFindSettingsActivity.this.startActivity(restaurantsIntent);
                            } catch (JSONException e) {
                                Toast.makeText(RestaurantsFindSettingsActivity.this, "Error parsing Zomato response", Toast.LENGTH_SHORT).show();
                            }
                        },
                        error -> {
                            Log.e(TAG, error.toString());
                            Toast.makeText(RestaurantsFindSettingsActivity.this, "Error calling Zomato API", Toast.LENGTH_SHORT).show();
                        }
                ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("user-key", "4e6fa5534bfc1fbe48829a826a096a0a");
                return params;
            }
        };
        HttpSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String text = adapterView.getItemAtPosition(position).toString();
        Cuisines cuisine = Cuisines.valueOf(text);
        this.chosenCuisine = cuisine.getValue();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        this.chosenCuisine = -1;
    }
}
