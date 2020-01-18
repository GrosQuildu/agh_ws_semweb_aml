package pl.edu.agh.eis.wsswaml.sparql;

public class Wikidata extends SimpleEndpointInterface {

    public String getURL() {
        return "https://query.wikidata.org/sparql";
    }

    public String getPrefixes() {
        return "PREFIX wd: <http://www.wikidata.org/entity/>\n" +
                    "PREFIX wds: <http://www.wikidata.org/entity/statement/>\n" +
                    "PREFIX wdv: <http://www.wikidata.org/value/>\n" +
                    "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\n" +
                    "PREFIX wikibase: <http://wikiba.se/ontology#>\n" +
                    "PREFIX p: <http://www.wikidata.org/prop/>\n" +
                    "PREFIX ps: <http://www.wikidata.org/prop/statement/>\n" +
                    "PREFIX pq: <http://www.wikidata.org/prop/qualifier/>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX bd: <http://www.bigdata.com/rdf#>";
    }
}
