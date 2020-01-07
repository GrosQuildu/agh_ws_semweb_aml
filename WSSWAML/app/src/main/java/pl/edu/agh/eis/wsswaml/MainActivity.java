package pl.edu.agh.eis.wsswaml;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import wiki.lemedia.sparqlandroid;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                String endpointUrl = "https://query.wikidata.org/sparql";

                String querySelect = "# This returns 10 random painting images\n" +
                        "# RAND() returns one random number (cached like every query).\n" +
                        "# The string representation of the item and the random number are hashed.\n" +
                        "# This will give a complete different ordering every time you have a different random number.\n" +
                        "# You can change the LIMIT if you want to trigger a new random number\n" +
                        "#defaultView:ImageGrid\n" +
                        "SELECT ?item ?itemLabel ?image (MD5(CONCAT(str(?item),str(RAND()))) as ?random)  WHERE {\n" +
                        "  ?item wdt:P279 wd:Q1778821.\n" +
                        "  ?item wdt:P18 ?image.\n" +
                        "  SERVICE wikibase:label { bd:serviceParam wikibase:language \"en\"}\n" +
                        "} ORDER BY ?random\n" +
                        "LIMIT 10";

                try {
                    HashMap data = retrieveData(endpointUrl, querySelect);
                    printResult(data, 30);
                } catch (EndpointException eex) {
                    eex.printStackTrace();
                }
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

    public static HashMap<String, HashMap> retrieveData(String endpointUrl, String query) throws EndpointException {
        Endpoint sp = new Endpoint(endpointUrl, false);
        HashMap<String, HashMap> rs = sp.query(query);
        return rs;
    }

    public static void printResult(HashMap<String, HashMap> rs , int size) {
        for (String variable : (ArrayList<String>) rs.get("result").get("variables")) {
            System.out.print(String.format("%-"+size+"."+size+"s", variable ) + " | ");
        }
        System.out.print("\n");
        for (HashMap value : (ArrayList<HashMap>) rs.get("result").get("rows")) {
            for (String variable : (ArrayList<String>) rs.get("result").get("variables")) {
                System.out.print(String.format("%-"+size+"."+size+"s", value.get(variable)) + " | ");
            }
            System.out.print("\n");
        }
    }
}
