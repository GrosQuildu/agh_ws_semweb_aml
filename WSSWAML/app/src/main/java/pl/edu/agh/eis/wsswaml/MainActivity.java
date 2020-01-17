package pl.edu.agh.eis.wsswaml;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;

import wiki.lemedia.sparqlandroid.*;
import com.hp.hpl.jena.query.*;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SparqlClient client = new SparqlClient();

                String wikidataPrefixes = "PREFIX wd: <http://www.wikidata.org/entity/>\n" +
                        "PREFIX wds: <http://www.wikidata.org/entity/statement/>\n" +
                        "PREFIX wdv: <http://www.wikidata.org/value/>\n" +
                        "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\n" +
                        "PREFIX wikibase: <http://wikiba.se/ontology#>\n" +
                        "PREFIX p: <http://www.wikidata.org/prop/>\n" +
                        "PREFIX ps: <http://www.wikidata.org/prop/statement/>\n" +
                        "PREFIX pq: <http://www.wikidata.org/prop/qualifier/>\n" +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                        "PREFIX bd: <http://www.bigdata.com/rdf#>\n\n";
                String endpointUrl = "https://query.wikidata.org/sparql";
                String queryString = "SELECT ?item ?itemLabel ?image (MD5(CONCAT(str(?item),str(RAND()))) as ?random)  WHERE {\n" +
                        "  ?item wdt:P279 wd:Q1778821.\n" +
                        "  ?item wdt:P18 ?image.\n" +
                        "  SERVICE wikibase:label { bd:serviceParam wikibase:language \"en\"}\n" +
                        "} ORDER BY ?random\n" +
                        "LIMIT 10";

                Query query = QueryFactory.create(wikidataPrefixes+queryString) ;
                QueryExecution qexec = QueryExecutionFactory.sparqlService(endpointUrl, query);
                ResultSet results = qexec.execSelect();
                ResultSetFormatter.out(System.out, results, query) ;
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
