package pl.edu.agh.eis.wsswaml.sparql;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

public class GeoSparql extends SimpleEndpoint {
    private String endpointUrl = "http://www.lotico.com:3030/lotico/sparql";

    private String prefixes = "PREFIX spatial:<http://jena.apache.org/spatial#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\n" +
            "PREFIX wd: <http://www.wikidata.org/entity/>\n" +
            "PREFIX geo:<http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
            "PREFIX gn:<http://www.geonames.org/ontology#>\n" +
            "PREFIX foaf:<http://xmlns.com/foaf/0.1/>\n" +
            "PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>\n" +
            "PREFIX loticoowl:<http://www.lotico.com/ontology/>";

    public String getURL() {
        return this.endpointUrl;
    }

    public String getPrefixes() {
        return this.prefixes;
    }

}
