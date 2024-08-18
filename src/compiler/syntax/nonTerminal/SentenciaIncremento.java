package compiler.syntax.nonTerminal;

import compiler.semantic.symbol.SymbolVariable;
import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class SentenciaIncremento extends NonTerminal {

	public SentenciaIncremento(String lexema) {
		
		super(lexema);
		
	}
	
	// sentenciaIncremento ::= IDENTIFICADOR:identificador AUTO_INCREMENTO:autoIncremento SEMI_COLON:semiColon
	public static SentenciaIncremento produccion(TokenIF identificador, TokenIF autoIncremento, TokenIF semiColon) {

		String lexema = identificador.getLexema() + autoIncremento.getLexema() + semiColon.getLexema();

		Consola.log("sentenciaIncremento[1]: \n" + lexema);

		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
		
		String nombreVariable = identificador.getLexema();
		
		SymbolIF simbolo = Contexto.scopeManager.searchSymbol(nombreVariable);
		
		TypeIF tipoVariable = simbolo.getType();
		
		// Comprobar que la variable es de tipo entero
		if (!tipoEntero.equals(tipoVariable)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, solo se pueden autoincrementar variables de tipo entero: " + nombreVariable);
	
		} 
		
		// Comprobar que el simbolo es una variable, y no una constante, funcion...
		if (!(simbolo instanceof SymbolVariable)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, solo se pueden autoincrementar variables: " + nombreVariable);
		
		}
		
		return new SentenciaIncremento(lexema);
		
	}
	
}
