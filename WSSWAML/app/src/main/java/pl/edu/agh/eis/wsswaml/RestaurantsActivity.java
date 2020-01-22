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

import org.json.JSONObject;

public class RestaurantsActivity extends AppCompatActivity {

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

        //String uri = "https://developers.zomato.com/api/v2.1/search?entity_id=255&entity_type=city&count=7&lat=50.013439&lon=19.881645&radius=500&cuisines=161&sort=real_distance";

        String uri = new Uri.Builder()
                .scheme("https")
                .authority("developers.zomato.com")
                .path("api/v2.1/search")
                .appendQueryParameter("user-key", "4e6fa5534bfc1fbe48829a826a096a0a")
                .appendQueryParameter("entity_id", "255")
                .appendQueryParameter("entity_type", "city")
                .appendQueryParameter("count", "10")
                .appendQueryParameter("lat", "50.013439")
                .appendQueryParameter("lon", "19.881645")
                .appendQueryParameter("radius", "2000")
                .appendQueryParameter("cuisines", "161")
                .appendQueryParameter("sort", "real_distance")
                .build()
                .toString();


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                uri,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest Response", response.toString());
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.e("Rest Resoinse", error.toString());
                    }
                }
        );
    }

}
