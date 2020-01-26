package pl.edu.agh.eis.wsswaml.data;

import android.util.Log;

import java.util.Locale;

public class Queries {
    private static final String TAG = "Queries";

    // query wikidata for all NatonionalCuisines and some images of them
    public static String allCuisines() {
        String query = "SELECT ?cuisine ?cuisineLabel ?image (MD5(CONCAT(str(?cuisine),str(RAND()))) as ?random) WHERE {\n" +
                "   ?cuisine wdt:P31 wd:Q1968435.\n" +
                "   ?cuisine wdt:P18 ?image.\n" +
                "SERVICE wikibase:label { bd:serviceParam wikibase:language \"" +
                Locale.getDefault().getLanguage() +
                "\"}\n" +
                "} ORDER BY ?random\n";
        Log.i(TAG, query);
        return query;
    }

    public static String oneCuisine(String entityID) {
        String query = "SELECT DISTINCT  ?cuisine ?abstract \n" +
                "WHERE {\n" +
                "    ?cuisine dbo:abstract ?abstract.\n" +
                "    ?cuisine owl:sameAs <" + entityID + ">.\n" +
                "    FILTER (langMatches(lang(?abstract), \"" +
                Locale.getDefault().getLanguage() + "\"))\n" +
                "    }\n";
        Log.i(TAG, query);
        return query;
    }
}

