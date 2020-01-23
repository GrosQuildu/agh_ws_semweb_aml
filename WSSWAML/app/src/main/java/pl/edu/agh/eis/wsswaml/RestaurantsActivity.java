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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

public class RestaurantsActivity extends AppCompatActivity {

    private JSONObject json;
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
        try {
            this.json = new JSONObject(restaurants);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        GetRestaurants();
    }

    private void GetRestaurants() {

        if(this.json == null) return;

        try {
            JSONObject restaurants = this.json.getJSONObject("restaurants");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
