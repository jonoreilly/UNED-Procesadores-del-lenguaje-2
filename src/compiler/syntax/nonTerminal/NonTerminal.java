package compiler.syntax.nonTerminal;

import java.util.List;

import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.syntax.nonTerminal.NonTerminalIF;

public abstract class NonTerminal implements NonTerminalIF {

    private String lexema;
    
    private List<QuadrupleIF> intermediateCode;
    
    public NonTerminal(String lexema, List<QuadrupleIF> intermediateCode) {
        
    	super ();
        
    	this.intermediateCode = intermediateCode;
        
    	this.lexema = lexema;
    
    }

    public String getLexema() {

    	return this.lexema;
    
    }

    public List<QuadrupleIF> getIntermediateCode() {
    
    	return intermediateCode;
    
    }

    public void setIntermediateCode(List<QuadrupleIF> intermediateCode) {
    
    	this.intermediateCode = intermediateCode;
    
    }
    
}