package pl.edu.agh.eis.wsswaml;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

import pl.edu.agh.eis.wsswaml.data.Cuisines;

public class UserSettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    static String TAG = "UserSettingsActivity";

    private Button findRestaurantButton;
    private Spinner dropdown;
    private Toolbar toolbar;
    private EditText distanceInMetersText;
    private int choosenCuisine = 161;
    private int distance = 1000;
    private double latitude = 50.013439;
    private double longitude = 19.881645;
    private JSONArray jsonArray;
    private TextView chooseDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        distanceInMetersText = findViewById(R.id.input_distance);
        chooseDistance = findViewById(R.id.label_choose_distance);

        // dropdown - cuisine type
        dropdown = findViewById(R.id.spinner_cuisines_options);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Cuisines.getAll());
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

        // find restaurant button
        findRestaurantButton = findViewById(R.id.btn_ok);
        findRestaurantButton.setOnClickListener(view -> {
            String value = distanceInMetersText.getText().toString();
            if (!value.isEmpty()) {
                distance = Integer.parseInt((distanceInMetersText.getText().toString()));
            }
            callApi(distance, choosenCuisine, longitude, latitude);
        });

    }

    private int getDistance() {
        EditText distanceNumber = findViewById(R.id.input_distance);
        return Integer.parseInt(distanceNumber.getText().toString());
    }

    private void callApi(int radius, int cuisines, double lon, double lat) {
        //To do:
        // wstawić prawidłową lokalizację
        String url = "https://developers.zomato.com/api/v2.1/search?entity_id=255&entity_type=city&count=10&lat=" + lat + "&lon=" + lon + "&radius=" + radius + "&cuisines=" + cuisines + "&sort=real_distance";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null,
                        response -> {
                            Log.i(TAG, response.toString());
                            try {
                                JSONArray restaurants = response.getJSONArray("restaurants");
                                Intent restaurantsIntent = new Intent(UserSettingsActivity.this, RestaurantsActivity.class);
                                restaurantsIntent.putExtra("data", restaurants.toString());
                                UserSettingsActivity.this.startActivity(restaurantsIntent);
                            } catch (JSONException e) {
                                Toast.makeText(UserSettingsActivity.this, "Error parsing Zomato response", Toast.LENGTH_SHORT).show();
                            }
                        },
                        error -> {
                            Log.e(TAG, error.toString());
                            Toast.makeText(UserSettingsActivity.this, "Error calling Zomato API", Toast.LENGTH_SHORT).show();
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
        this.choosenCuisine = cuisine.getValue();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
