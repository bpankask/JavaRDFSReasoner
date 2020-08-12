package labreasoner;

import org.apache.jena.graph.Node;
import org.apache.jena.reasoner.rulesys.Builtin;
import org.apache.jena.reasoner.rulesys.RuleContext;

public class TestForGL implements Builtin{
	
	@Override
	public boolean bodyCall(Node[] arg0, int arg1, RuleContext arg2) {
		
		if(arg0[0].isBlank()) {
			if(arg0[1].toString().contentEquals("http://www.w3.org/2000/01/rdf-schema#subPropertyOf")) {
				
			}
		}
		
		return true;
	}

	@Override
	public int getArgLength() {
		return 3;
	}

	@Override
	public String getName() {
		return "testForGL";
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
