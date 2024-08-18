package compiler.syntax.nonTerminal;

import es.uned.lsi.compiler.semantic.type.TypeIF;

public class AccesoVector extends NonTerminal {

	private TypeIF tipo;
	
	public AccesoVector(String lexema, TypeIF tipo) {
		
		super(lexema);
		
		this.tipo = tipo;
	
	}
	
	public TypeIF getTipo() {
	
		return this.tipo;
	
	}
	
}
