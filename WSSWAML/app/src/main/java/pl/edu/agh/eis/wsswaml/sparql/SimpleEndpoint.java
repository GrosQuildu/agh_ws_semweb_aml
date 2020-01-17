package pl.edu.agh.eis.wsswaml.sparql;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

public abstract class SimpleEndpoint implements Endpoint {
    public ResultSet query(String queryString) {
        Query query = QueryFactory.create(getPrefixes() + queryString) ;
        QueryExecution qexec = QueryExecutionFactory.sparqlService(getURL(), query);
        return qexec.execSelect();
    }

    public Model describe(String queryString) {
        Query query = QueryFactory.create(getPrefixes() + queryString) ;
        QueryExecution qexec = QueryExecutionFactory.sparqlService(getURL(), query);
        return qexec.execDescribe();
    }
}
