package compiler.syntax.nonTerminal;

import es.uned.lsi.compiler.semantic.type.TypeIF;

public class SentenciaDevuelve extends NonTerminal {

	private TypeIF tipoDevuelve;
	
	public SentenciaDevuelve(String lexema, TypeIF tipoDevuelve) {
		
		super(lexema);
		
		this.tipoDevuelve = tipoDevuelve;
	
	}
	
	public TypeIF getTipoDevuelve() {
	
		return this.tipoDevuelve;
	
	}
	
}
