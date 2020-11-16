package Reasoner;

import JSONHandler.CreateOntologyFromJson;
import JSONHandler.JsonParser;
import JSONHandler.JsonWriter;
import Parsing.ParseMethods;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args){

        //gets files in specified folder
        File[] files = getFiles("C:\\Users\\Brayden Pankaskie\\Desktop\\LabStuff\\json_data_v4\\test");

		for(int i=0; i<files.length; i++) {

            //create empty ont model
            OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

            //input file to be parsed
            File file = files[i];

            try {
                //gets file and creates parsed json date
                JsonParser jp = new JsonParser(file);

                //creates models
                CreateOntologyFromJson.createOntology(jp, ontModel);

                FileWriter fw = new FileWriter("OntModelForCorrectingReadIssue.ttl");
                //ontModel.write(fw, "TTL");

                InfModel infModel = Logic.reasonAndTrace(ontModel, "Reasoner\\Rules.txt");
                infModel.getDeductionsModel().write(fw, "TTL");

                //creates output file
                String outputFileName = "C:\\Users\\Brayden Pankaskie\\Desktop\\JavaRDFSReasoner\\OutputFiles\\" + file.toString().substring(62);

                /*//creates noisy triples
                List<String> correctTriples = Arrays.asList(ParseMethods.getOriginalTriplesFromOntology(ontModel));
                List<String> invalidTriples = new ArrayList<>();
                Logic.makeNWrongTriples(jp.getSubjects(), jp.getObjects(), jp.getPredicates(),
                        correctTriples,invalidTriples, 5);
                */

                JsonWriter.writeToJson(jp.getOntologyName(), jp.getPrefixMap(), ParseMethods.getOriginalTriplesFromOntology(ontModel),
                        ParseMethods.getReasonedTriplesFromOntology(infModel), outputFileName);

                System.out.println("Correctly ran " + files[i]);

            } catch (Exception e) {
                System.out.println("Error with file: " + files[i] + "-----" + e);
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
