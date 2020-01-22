package pl.edu.agh.eis.wsswaml;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

import pl.edu.agh.eis.wsswaml.models.Cuisine;
import pl.edu.agh.eis.wsswaml.sparql.DBpedia;
import pl.edu.agh.eis.wsswaml.sparql.EndpointInterface;


public class CuisineDescriptionActivity extends AppCompatActivity {

    String queryString = "SELECT DISTINCT ?property ?hasValue\n" +
            "WHERE {\n" +
            "    ?property rdfs:comment ?abstract.\n" +
            "    { <http://dbpedia.org/resource/Polish_cuisine> ?property ?hasValue }\n" +
            "    FILTER (langMatches(lang(?hasValue),\"en\"))\n" +
            "    }";

    EndpointInterface dbpedia = new DBpedia();
    ResultSet results = dbpedia.query(queryString);


    QuerySolution soln = results.nextSolution();
    RDFNode hasValue = soln.get("hasValue");
    Resource property = soln.getResource("property");


    //private List<Cuisine> mCuisinesList;
    Cuisine cuisine = new Cuisine("Polish", "https://upload.wikimedia.org/wikipedia/commons/c/c5/Eggplant_kebab_and_isot.jpg", hasValue.toString());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine_description);
        TextView desId = (TextView) findViewById(R.id.textViewDescription);
        desId.setText(cuisine.getDescription());
    }

    private void fillTextView(int id, String text) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(text);
    }


}
