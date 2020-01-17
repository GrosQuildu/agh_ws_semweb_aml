package pl.edu.agh.eis.wsswaml.sparql;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

public class Wikidata extends SimpleEndpoint {
    private String endpointUrl = "https://query.wikidata.org/sparql";

    private String prefixes = "PREFIX wd: <http://www.wikidata.org/entity/>\n" +
            "PREFIX wds: <http://www.wikidata.org/entity/statement/>\n" +
            "PREFIX wdv: <http://www.wikidata.org/value/>\n" +
            "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\n" +
            "PREFIX wikibase: <http://wikiba.se/ontology#>\n" +
            "PREFIX p: <http://www.wikidata.org/prop/>\n" +
            "PREFIX ps: <http://www.wikidata.org/prop/statement/>\n" +
            "PREFIX pq: <http://www.wikidata.org/prop/qualifier/>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "PREFIX bd: <http://www.bigdata.com/rdf#>";

    public String getURL() {
        return this.endpointUrl;
    }

    public String getPrefixes() {
        return this.prefixes;
    }
}
