package compiler.syntax.nonTerminal;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.lexical.TokenIF;
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
	
	// sentenciaDevuelve ::= DEVUELVE:devuelve SEMI_COLON:semiColon
	public static SentenciaDevuelve produccion_DEVUELVE_SEMI_COLON(TokenIF devuelve, TokenIF semiColon) {

		String lexema = devuelve.getLexema() + semiColon.getLexema();

		Consola.log("sentenciaDevuelve[1]: \n" + lexema);
	
		TypeIF tipoVacio = Contexto.scopeManager.searchType("vacio");
	
		return new SentenciaDevuelve(lexema, tipoVacio);
		
	}
	
	// sentenciaDevuelve ::= DEVUELVE:devuelve expresion:expresion SEMI_COLON:semiColon
	public static SentenciaDevuelve produccion_DEVUELVE_expresion_SEMI_COLON(TokenIF devuelve, Expresion expresion, TokenIF semiColon) {

		String lexema = devuelve.getLexema() + expresion.getLexema() + semiColon.getLexema();

		Consola.log("sentenciaDevuelve[2]: \n" + lexema);
	
		return new SentenciaDevuelve(lexema, expresion.getTipo());
		
	}
	
}
