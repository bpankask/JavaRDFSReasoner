package Parsing;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.*;

import java.util.ArrayList;
import java.util.List;

public abstract class ParseMethods {

    public static String[] getOriginalTriplesFromOntology(OntModel model) {

        List<String> list = new ArrayList<String>();

        StmtIterator iter = model.listStatements();

        while (iter.hasNext()) {

            StringBuilder sb = new StringBuilder();

            Statement stmt = iter.nextStatement();// get next statement
            Resource subject = stmt.getSubject();   // get the subject
            Property predicate = stmt.getPredicate(); // get the predicate
            RDFNode object = stmt.getObject();    // get the object

            sb.append(subject + " ");
            sb.append(predicate + " ");
            sb.append(object);

            list.add(sb.toString());
        }

        return list.toArray(new String[list.size()]);
    }

    public static String[] getReasonedTriplesFromOntology(InfModel model) {

        List<String> list = new ArrayList<String>();

        Model m = model.getDeductionsModel();

        StmtIterator iter = m.listStatements();

        while (iter.hasNext()) {

            StringBuilder sb = new StringBuilder();

            Statement stmt = iter.nextStatement();// get next statement
            Resource subject = stmt.getSubject();   // get the subject
            Property predicate = stmt.getPredicate(); // get the predicate
            RDFNode object = stmt.getObject();    // get the object

            sb.append(subject + " ");
            sb.append(predicate + " ");
            sb.append(object);

            list.add(sb.toString());
        }

        return list.toArray(new String[list.size()]);
    }
}
