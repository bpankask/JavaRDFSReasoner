package Reasoner;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.BuiltinRegistry;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.vocabulary.ReasonerVocabulary;


public class Logic {

    /**
     * Method to create custom reasoner, reason over ontology, and record the trace of every rule producing a new triple
     * @param ontModel
     * @param ruleFile
     * @throws FileNotFoundException
     */
    public static InfModel reasonAndTrace(OntModel ontModel, String ruleFile) throws FileNotFoundException {

        //register for custom method used in rule file
        BuiltinRegistry.theRegistry.register(new Property_Check());

        //load rules
        List<Rule> rules = Rule.rulesFromURL(ruleFile);

        //creates reasoner with custom rules and enables tracing
        Reasoner reasoner = new GenericRuleReasoner(rules);
        reasoner.setDerivationLogging(true);
        reasoner.setParameter(ReasonerVocabulary.PROPtraceOn, Boolean.TRUE);

        //creates an inference model using custom reasoner and the read in ontology model
        //contains the original KG and inferences
        InfModel inf = ModelFactory.createInfModel(reasoner, ontModel);

        return inf;
    }

    /**
     * Creates noisy triples using parts of existing triples
     * @param allSubAndObj
     * @param allPredicates
     * @param correctTriples
     * @param invalidTriples
     */
    private static void makeWrongTriple(String[] allSubAndObj, String[] allPredicates,
                                       List<String> correctTriples, List<String> invalidTriples){

        Random rand = new Random();

        //builds potential invalid triple
        StringBuilder sb = new StringBuilder();
        String subject = allSubAndObj[rand.nextInt(allSubAndObj.length)];
        String predicate = allPredicates[rand.nextInt(allPredicates.length)];
        String object = allSubAndObj[rand.nextInt(allSubAndObj.length)];

        sb.append(subject + " ");
        sb.append(predicate + " ");
        sb.append(object);

        if(!correctTriples.contains(sb.toString())){
            invalidTriples.add(sb.toString());
        }
    }

    /**
     * Method to make n number of invalid triples
     * @param allSubjects
     * @param allObjects
     * @param allPredicates
     * @param correctTriples
     * @param invalidTriples
     * @param n
     */
    public static void makeNWrongTriples(String[] allSubjects, String[] allObjects, String[] allPredicates,
                                         List<String> correctTriples, List<String> invalidTriples, int n){

        //create list of subjects and objects available to pick at random
        List<String> list = new ArrayList(Arrays.asList(allSubjects));
        list.addAll(Arrays.asList(allObjects));
        String[] allSubAndObj = list.toArray(new String[list.size()]);

        while(invalidTriples.size() < n){
            makeWrongTriple(allSubAndObj, allPredicates, correctTriples, invalidTriples);
        }
    }

}
