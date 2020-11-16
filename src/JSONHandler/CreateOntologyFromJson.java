package JSONHandler;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;

public abstract class CreateOntologyFromJson {

    public static void createOntology(JsonParser json, OntModel model) throws Exception {

        String[] subjects = json.getSubjects();
        String[] predicates = json.getPredicates();
        String[] objects = json.getObjects();

        if(subjects.length != predicates.length || predicates.length != objects.length) throw new Exception("Something went wrong in json parsing");
        else{

            //sets prefix map of ontology
            model.setNsPrefixes(json.getPrefixMap());

            //adds triples to ontology
            for(int i=subjects.length-1; i>=0; i--){

                String subString = subjects[i].split(":")[0];
                String predString = predicates[i].split(":")[0];
                String objString = objects[i].split(":")[0];

                Resource subject = null;
                Property predicate = null;
                Resource object = null;

                //creates triple
                if(subjects[i].split(":").length > 1) {
                    subject = model.createResource(model.getNsPrefixURI(subString) + subjects[i].substring(subString.length()+1));
                }
                else{
                    subject = model.createResource(subjects[i]);
                }

                if(predicates[i].split(":").length > 1) {
                    predicate = model.createProperty(model.getNsPrefixURI(predString) + predicates[i].substring(predString.length()+1));
                }
                else{
                    predicate = model.createProperty(predicates[i]);
                }

                if(objects[i].split(":").length > 1) {
                    object = model.createResource(model.getNsPrefixURI(objString) + objects[i].substring(objString.length()+1));
                }
                else {
                    object = model.createResource(objects[i]);
                }

                Statement s = model.createStatement(subject, predicate, object);

                //adds triple to ontology
                model.add(s);
            }
        }

    }
}
