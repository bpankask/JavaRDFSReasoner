package Reasoner;

import JSONHandler.CreateOntologyFromJson;
import JSONHandler.JsonParser;
import JSONHandler.JsonWriter;
import Parsing.ParseMethods;

import java.io.File;
import java.io.FileWriter;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.ModelFactory;


public class Main {

    public static void main(String[] args){

		for(File file : getFiles("json")) {

            //create empty ont model
            OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

            try {
                //gets file and creates parsed json date
                JsonParser jp = new JsonParser(file);

                //creates models
                CreateOntologyFromJson.createOntology(jp, ontModel);

                FileWriter fw = new FileWriter("OntModelForCorrectingReadIssue.ttl");
                //ontModel.write(fw, "TTL");

                InfModel infModel = Logic.reasonAndTrace(ontModel, "Reasoner/Rules.txt");
                infModel.getDeductionsModel().write(fw, "TTL");

                //creates output file
                String outputFileName = "OutputFiles/" + file.getName().toString();

                /*//creates noisy triples
                List<String> correctTriples = Arrays.asList(ParseMethods.getOriginalTriplesFromOntology(ontModel));
                List<String> invalidTriples = new ArrayList<>();
                Logic.makeNWrongTriples(jp.getSubjects(), jp.getObjects(), jp.getPredicates(),
                        correctTriples,invalidTriples, 5);
                */

                JsonWriter.writeToJson(jp.getOntologyName(), jp.getPrefixMap(), ParseMethods.getOriginalTriplesFromOntology(ontModel), ParseMethods.getReasonedTriplesFromOntology(infModel), outputFileName);

                System.out.println("Correctly ran " + file);

            } catch (Exception e) {
                System.out.println("Error with file: " + file + "-----" + e);
            }

        }//end for loop

    }//end main

    /**
     * Gets files in specified folder
     * @param folderName
     * @return
     */
    public static File[] getFiles(String folderName) {
        File folder = new File(folderName);
        File[] files = folder.listFiles();
        return files;
    }
}
