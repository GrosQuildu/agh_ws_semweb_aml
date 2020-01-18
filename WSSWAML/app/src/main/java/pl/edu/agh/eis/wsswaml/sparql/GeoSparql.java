package pl.edu.agh.eis.wsswaml.sparql;

public class GeoSparql extends SimpleEndpointInterface {

    public String getURL() {
        return "http://www.lotico.com:3030/lotico/sparql";
    }

    public String getPrefixes() {
        return "PREFIX spatial:<http://jena.apache.org/spatial#>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\n" +
                    "PREFIX wd: <http://www.wikidata.org/entity/>\n" +
                    "PREFIX geo:<http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                    "PREFIX gn:<http://www.geonames.org/ontology#>\n" +
                    "PREFIX foaf:<http://xmlns.com/foaf/0.1/>\n" +
                    "PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>\n" +
                    "PREFIX loticoowl:<http://www.lotico.com/ontology/>";
    }

}
