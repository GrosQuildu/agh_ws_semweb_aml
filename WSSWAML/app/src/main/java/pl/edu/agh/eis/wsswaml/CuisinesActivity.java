package pl.edu.agh.eis.wsswaml;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Resource;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.eis.wsswaml.models.Cuisine;
import pl.edu.agh.eis.wsswaml.sparql.Wikidata;
import pl.edu.agh.eis.wsswaml.sparql.EndpointInterface;

public class CuisinesActivity extends AppCompatActivity {
    private RecyclerView cuisinesRecyclerView;
    private CuisinesAdapter mAdapter;
    private List<Cuisine> mCuisinesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisines);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView cuisinesRecyclerView = findViewById(R.id.cuisines_recycler_view);
        //cuisinesRecyclerView.setHasFixedSize(true);
        cuisinesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCuisinesList = new ArrayList<>();
        //mCuisinesList.add(new Cuisine("Turkish", "https://upload.wikimedia.org/wikipedia/commons/c/c5/Eggplant_kebab_and_isot.jpg"));
        //mCuisinesList.add(new Cuisine("Korean", "https://upload.wikimedia.org/wikipedia/commons/3/3e/Korean.food-Hanjungsik-01.jpg"));
        //mCuisinesList.add(new Cuisine("French", "https://upload.wikimedia.org/wikipedia/commons/4/44/Cuisine_Trois_%C3%A9toiles.jpg"));

        String queryString = "SELECT ?item ?itemLabel ?image (MD5(CONCAT(str(?item),str(RAND()))) as ?random) WHERE {\n" +
                "?item wdt:P31 wd:Q1968435.\n" +
                "?item wdt:P18 ?image.\n" +
                "SERVICE wikibase:label { bd:serviceParam wikibase:language \"en\"}\n" +
                "} ORDER BY ?random\n" +
                "LIMIT 10";

        EndpointInterface wikidata = new Wikidata();
        ResultSet results = wikidata.query(queryString);
//        ResultSetFormatter.out(System.out, results);

        int resultsCounter = 0;
        for (; results.hasNext(); ) {
            QuerySolution soln = results.nextSolution();

            Resource image = soln.getResource("image");
            Literal itemLabel = soln.getLiteral("itemLabel");
            // String image_string = image.toString();
            mCuisinesList.add(new Cuisine(itemLabel.toString().split(" |@")[0], image.toString()));

            //System.out.println(itemLabel + " - " + image);
            resultsCounter++;
        }

        mAdapter = new CuisinesAdapter(mCuisinesList,this);
        cuisinesRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
