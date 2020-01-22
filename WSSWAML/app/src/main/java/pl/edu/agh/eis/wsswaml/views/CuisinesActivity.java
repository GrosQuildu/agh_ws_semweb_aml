package pl.edu.agh.eis.wsswaml.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
import java.util.Objects;

import pl.edu.agh.eis.wsswaml.R;
import pl.edu.agh.eis.wsswaml.data.Queries;
import pl.edu.agh.eis.wsswaml.models.Cuisine;
import pl.edu.agh.eis.wsswaml.sparql.EndpointInterface;
import pl.edu.agh.eis.wsswaml.sparql.Wikidata;

public class CuisinesActivity extends AppCompatActivity implements OnItemClickListener {
    private RecyclerView cuisinesRecyclerView;
    private CuisinesAdapter mAdapter;
    private List<Cuisine> mCuisinesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisines);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cuisinesRecyclerView = findViewById(R.id.cuisines_recycler_view);
        //cuisinesRecyclerView.setHasFixedSize(true);
        cuisinesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCuisinesList = new ArrayList<>();

        EndpointInterface wikidata = new Wikidata();
        ResultSet results = wikidata.query(Queries.allCuisines());

        for (; results.hasNext(); ) {
            QuerySolution soln = results.nextSolution();

            Resource image = soln.getResource("image");
            Literal cuisineLabel = soln.getLiteral("cuisineLabel");
            Resource entityID = soln.getResource("cuisine");
            mCuisinesList.add(new Cuisine(entityID.toString(), cuisineLabel.toString().split("[ @]")[0], image.toString()));
        }

        mAdapter = new CuisinesAdapter(mCuisinesList, this);
        cuisinesRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onItemClicked(View view, Cuisine cuisine) {
        Intent cuisineDescriptionIntent = new Intent(this, CuisineDescriptionActivity.class);
        cuisineDescriptionIntent.putExtra("CuisineEntityID", cuisine.getEntityID());
        this.startActivity(cuisineDescriptionIntent);
    }
}
