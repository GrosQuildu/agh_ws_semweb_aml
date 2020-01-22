package pl.edu.agh.eis.wsswaml;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import pl.edu.agh.eis.wsswaml.data.Cuisines;
import pl.edu.agh.eis.wsswaml.models.Restaurant;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.io.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UserSettingsActivity extends AppCompatActivity {
    private TextView mTextViewResult;
    private RequestQueue mQueue;
    private Button findRestaurantButton;
    private Spinner dropdown;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextViewResult = findViewById(R.id.text_view_result);
        dropdown = findViewById(R.id.spinner_cuisines_options);
        findRestaurantButton = findViewById(R.id.btn_ok);
        mQueue = Volley.newRequestQueue(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getCuisines());
        dropdown.setAdapter(adapter);


        findRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callApi();
            }
        });
    }

    private List<String>  getCuisines() {
        List<String> items = new ArrayList<String>();
        for(Cuisines cus : Cuisines.values()){
            items.add(cus.toString());
        }
        return items;
    }

    private int getDistance()
    {
        EditText distanceNumber = findViewById(R.id.input_distance);
        return  Integer.parseInt( distanceNumber.getText().toString() );
    }

    private void callApi()
    {
        //To do:
        // wstawić parametry pobrane z ustawień tego activity (cuisines, radius)
        // wstawić prawidłową lokalizację
        // wysłanie jsona do nowej aktywności
        String uri = "https://developers.zomato.com/api/v2.1/search?entity_id=255&entity_type=city&count=10&lat=50.013439&lon=19.881645&radius=500&cuisines=161&sort=real_distance";


        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                uri,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest Response", response.toString());
                        try{

                            JSONObject object = new JSONObject(response.toString());
                            JSONArray jsonArray = object.getJSONArray("restaurants");
                            //String restaurants = "{}";
                            Intent restaurantsIntent = new Intent(UserSettingsActivity.this, RestaurantsActivity.class);
                            restaurantsIntent.putExtra("data", jsonArray.toString());
                            UserSettingsActivity.this.startActivity(restaurantsIntent);

                        }
                        catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.e("Rest Resoinse", error.toString());
                        TextView distance = findViewById(R.id.label_choose_distance);
                        distance.setText(error.toString().substring(0,1000));
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user-key", "4e6fa5534bfc1fbe48829a826a096a0a");
                return params;
            }
        };
        mQueue.add(objectRequest);
    }
}
