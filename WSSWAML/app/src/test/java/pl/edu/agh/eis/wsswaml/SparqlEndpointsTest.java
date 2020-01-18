package pl.edu.agh.eis.wsswaml;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

import org.junit.Test;

import pl.edu.agh.eis.wsswaml.sparql.EndpointInterface;
import pl.edu.agh.eis.wsswaml.sparql.GeoSparql;
import pl.edu.agh.eis.wsswaml.sparql.Wikidata;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SparqlEndpointsTest {

    @Test
    public void wikidataSimpleQuery() {
        // get some food images
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

            System.out.println(itemLabel + " - " + image);
            resultsCounter++;
        }
        assertEquals(10, resultsCounter);
    }

    @Test
    public void GeoSparqlSimpleQuery() {
        // places nearby Nowa Huta
        String queryString = "SELECT ?link ?name ?type ?lat ?lon\n" +
                "WHERE  {  \n" +
                "   ?link spatial:nearby(50.05 20.05 5 'km') .  \n" +
                "   ?link gn:name ?name .  \n" +
                "   ?link geo:lat ?lat .\n" +
                "   ?link gn:featureCode ?type .\n" +
                "   ?link geo:long ?lon\n" +
                "} LIMIT 15";

        EndpointInterface wikidata = new GeoSparql();
        ResultSet results = wikidata.query(queryString);
//        ResultSetFormatter.out(System.out, results);

        int resultsCounter = 0;
        for (; results.hasNext(); ) {
            QuerySolution soln = results.nextSolution();
            RDFNode name = soln.get("name");
            Resource type = soln.getResource("type");

            System.out.println(name + " - " + type);
            resultsCounter++;
        }
        assertEquals(15, resultsCounter);
    }
}