package pl.edu.agh.eis.wsswaml.sparql;

import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

public interface Endpoint {
    String getURL();
    String getPrefixes();
    ResultSet query(String queryString);
    Model describe(String queryString);
//    Model construct(String queryString);
//    boolean Ask(String queryString);
}

/*
*  http://ldf.fi/finlex/sparql
* http://etna.istc.cnr.it/food-sparql/
* http://etna.istc.cnr.it/food/index-en.html
* http://dbpedia-live.openlinksw.com/sparql/
* */