package labreasoner;

import org.apache.jena.graph.Node;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.reasoner.rulesys.Builtin;
import org.apache.jena.reasoner.rulesys.RuleContext;

public class GrdfD1 implements Builtin{
	
	OntModel ontModel;
	
	public GrdfD1(OntModel ontModel) {
		this.ontModel = ontModel;
	}
	
	@Override
	public boolean bodyCall(Node[] arg0, int arg1, RuleContext arg2) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("\"");
		sb.append(arg0[0].getLiteralValue());
		sb.append("\"^^");
		sb.append(arg0[0].getLiteralDatatypeURI());
		
		Resource lit = ontModel.createResource(sb.toString());
		Property property = ontModel.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
		Resource ddd = ontModel.createResource(arg0[0].getLiteralDatatypeURI());
		
		ontModel.add(lit, property, ddd);
		
		return true;
	}

	@Override
	public int getArgLength() {
		return 1;
	}

	@Override
	public String getName() {
		return "testGrdfD1";
	}

	@Override
	public String getURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void headAction(Node[] arg0, int arg1, RuleContext arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isMonotonic() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSafe() {
		// TODO Auto-generated method stub
		return false;
	}

}
