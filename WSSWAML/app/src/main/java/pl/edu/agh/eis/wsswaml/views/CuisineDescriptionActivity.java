package pl.edu.agh.eis.wsswaml.views;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import java.util.Objects;

import pl.edu.agh.eis.wsswaml.R;
import pl.edu.agh.eis.wsswaml.data.Queries;
import pl.edu.agh.eis.wsswaml.sparql.DBpedia;
import pl.edu.agh.eis.wsswaml.sparql.EndpointInterface;

public class CuisineDescriptionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine_description);

        String entityID = getIntent().getStringExtra("CuisineEntityID");
        EndpointInterface dbpedia = new DBpedia();
        ResultSet results = dbpedia.query(Queries.oneCuisine(entityID));

        String cuisineText = getResources().getString(R.string.cuisine) + getResources().getString(R.string.not_found);
        for (; results.hasNext(); ) {
            QuerySolution soln = results.nextSolution();
            cuisineText = soln.get("abstract").toString();
            cuisineText = cuisineText.substring(0, cuisineText.length() - 3);
            break;
        }

        TextView cuisineDecription = findViewById(R.id.cuisine_description_text);
        cuisineDecription.setText(cuisineText);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}