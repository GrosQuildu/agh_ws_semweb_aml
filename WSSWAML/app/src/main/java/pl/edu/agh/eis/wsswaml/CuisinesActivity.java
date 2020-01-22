package pl.edu.agh.eis.wsswaml;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Resource;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.eis.wsswaml.models.Cuisine;
import pl.edu.agh.eis.wsswaml.sparql.EndpointInterface;
import pl.edu.agh.eis.wsswaml.sparql.Wikidata;

public class CuisinesActivity extends AppCompatActivity {
    private RecyclerView cuisinesRecyclerView;
    private CuisinesAdapter mAdapter;
    private List<Cuisine> mCuisinesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisines);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView cuisinesRecyclerView = findViewById(R.id.cuisines_recycler_view);
        //cuisinesRecyclerView.setHasFixedSize(true);
        cuisinesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCuisinesList = new ArrayList<>();

        String queryString = "SELECT ?item ?itemLabel ?image (MD5(CONCAT(str(?item),str(RAND()))) as ?random) WHERE {\n" +
                "?item wdt:P31 wd:Q1968435.\n" +
                "?item wdt:P18 ?image.\n" +
                "SERVICE wikibase:label { bd:serviceParam wikibase:language \"en\"}\n" +
                "} ORDER BY ?random\n";

        EndpointInterface wikidata = new Wikidata();
        ResultSet results = wikidata.query(queryString);

        for (; results.hasNext(); ) {
            QuerySolution soln = results.nextSolution();

            Resource image = soln.getResource("image");
            Literal itemLabel = soln.getLiteral("itemLabel");
            mCuisinesList.add(new Cuisine(itemLabel.toString().split("[ @]")[0], image.toString()));
        }

        mAdapter = new CuisinesAdapter(mCuisinesList,this);
        cuisinesRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
