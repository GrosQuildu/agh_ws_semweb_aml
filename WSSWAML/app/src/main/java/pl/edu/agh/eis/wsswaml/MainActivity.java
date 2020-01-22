package pl.edu.agh.eis.wsswaml;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import pl.edu.agh.eis.wsswaml.localization.LocalizerServiceConnection;


public class MainActivity extends AppCompatActivity {
    private LocalizerServiceConnection localizer = LocalizerServiceConnection.getInstance();

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION};
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        Button findCuisineButton = findViewById(R.id.find_cuisine_button);
        findCuisineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (localizer.isEnabled()) {
                    localizer.disable(getApplicationContext());
                }

                Intent intent = new Intent(MainActivity.this, CuisinesActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        Button findRestaurantButton = findViewById(R.id.find_restaurant_button);
        findRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!localizer.isEnabled()) {
                    localizer.enable(getApplicationContext());
                }
                Intent userSettingIntent = new Intent(MainActivity.this, UserSettingsActivity.class);
                MainActivity.this.startActivity(userSettingIntent);
            }
        });

        Button cuisineDescriptionButton = findViewById(R.id.cuisine_description_button);
        cuisineDescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if (!localizer.isEnabled()) {
                //    localizer.enable(getApplicationContext());
                //}
                Intent cuisineDescriptionIntent = new Intent(MainActivity.this, CuisineDescriptionActivity.class);
                MainActivity.this.startActivity(cuisineDescriptionIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
