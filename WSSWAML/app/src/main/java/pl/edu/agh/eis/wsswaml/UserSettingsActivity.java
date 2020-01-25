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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;
import pl.edu.agh.eis.wsswaml.data.Cuisines;

public class UserSettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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

        dropdown = findViewById(R.id.spinner_cuisines_options);
        findRestaurantButton = findViewById(R.id.btn_ok);
        distanceInMetersText = findViewById(R.id.input_distance);
        chooseDistance = findViewById(R.id.label_choose_distance);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getCuisines());
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

        findRestaurantButton.setOnClickListener(view -> {
            String value = distanceInMetersText.getText().toString();
            if(!value.isEmpty()) {
                distance = Integer.parseInt((distanceInMetersText.getText().toString()));
            }
            callApi(distance, choosenCuisine, longitude, latitude);
        });

    }

    private List<String> getCuisines() {
        List<String> items = new ArrayList<>();
        for (Cuisines cus : Cuisines.values()) {
            items.add(cus.toString());
        }
        return items;
    }


    private int getDistance() {
        EditText distanceNumber = findViewById(R.id.input_distance);
        return Integer.parseInt(distanceNumber.getText().toString());
    }

    private void callApi(int radius, int cuisines, double lon, double lat) {
        //To do:
        // wstawić prawidłową lokalizację
        String uri = "https://developers.zomato.com/api/v2.1/search?entity_id=255&entity_type=city&count=10&lat=" + lat + "&lon=" + lon + "&radius=" + radius + "&cuisines=" + cuisines + "&sort=real_distance";

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                uri,
                null,
                response -> {
                    Log.e("Rest Response", response.toString());
                    try {

                        JSONObject object = new JSONObject(response.toString());
                        JSONArray jsonArray = object.getJSONArray("restaurants");

                        Intent restaurantsIntent = new Intent(UserSettingsActivity.this, RestaurantsActivity.class);
                        restaurantsIntent.putExtra("data", jsonArray.toString());
                        UserSettingsActivity.this.startActivity(restaurantsIntent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.e("Rest Resoinse", error.toString());
                    TextView distance = findViewById(R.id.label_choose_distance);
                    distance.setText(error.toString().substring(0, 1000));
                }
        );

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("user-key", "4e6fa5534bfc1fbe48829a826a096a0a");
        client.get(uri, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Toast.makeText(UserSettingsActivity.this, "OnSuccess call", Toast.LENGTH_SHORT).show();
                JSONObject object = null;
                try {
                    String data = response.toString();
                    chooseDistance.append(data);
                    object = new JSONObject(response.toString());
                    jsonArray = object.getJSONArray("restaurants");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent restaurantsIntent = new Intent(UserSettingsActivity.this, RestaurantsActivity.class);
                restaurantsIntent.putExtra("data", jsonArray.toString());
                UserSettingsActivity.this.startActivity(restaurantsIntent);
            }
        });
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
