package Reasoner;

import org.apache.jena.graph.Node;
import org.apache.jena.reasoner.rulesys.Builtin;
import org.apache.jena.reasoner.rulesys.RuleContext;

public class Property_Check implements Builtin{

    
    public boolean bodyCall(Node[] arg0, int arg1, RuleContext arg2) {

        String property = arg0[0].toString();
        if(property.length() > 44) {
            String rdf = property.substring(0, 43);
            if(rdf.contentEquals("http://www.w3.org/1999/02/22-rdf-syntax-ns#")) {
                String rest = property.substring(43);
                //System.out.println(rest);
                if(rest.substring(0,1).contentEquals("_")) {
                    String potentialNumb = rest.substring(1);
                    try {
                        Integer.parseInt(potentialNumb);
                        return true;
                    }//end try
                    catch(Exception e) {
                        //nothing should happen if error occurs
                    }//end catch
                }//third if
            }//second if
        }//first if

        return false;
    }

    
    public int getArgLength() {
        // TODO Auto-generated method stub
        return 1;
    }

    
    public String getName() {
        // TODO Auto-generated method stub
        return "propertyCheck";
    }

    
    public String getURI() {
        // TODO Auto-generated method stub
        return null;
    }

    
    public void headAction(Node[] arg0, int arg1, RuleContext arg2) {
        // TODO Auto-generated method stub
    }

    
    public boolean isMonotonic() {
        // TODO Auto-generated method stub
        return false;
    }

    
    public boolean isSafe() {
        // TODO Auto-generated method stub
        return false;
    }

}