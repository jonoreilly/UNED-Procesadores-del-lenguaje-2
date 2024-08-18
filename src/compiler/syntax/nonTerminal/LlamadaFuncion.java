package compiler.syntax.nonTerminal;

import es.uned.lsi.compiler.semantic.type.TypeIF;

public class LlamadaFuncion extends NonTerminal {

	private TypeIF tipoRetorno;
	
	public LlamadaFuncion(String lexema, TypeIF tipoRetorno) {
		super(lexema);
		
		this.tipoRetorno = tipoRetorno;
	}
	
	public TypeIF getTipoRetorno() {
		return this.tipoRetorno;
	}
	
}
