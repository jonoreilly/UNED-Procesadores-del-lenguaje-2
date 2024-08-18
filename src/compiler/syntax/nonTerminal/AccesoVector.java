package compiler.syntax.nonTerminal;

import compiler.semantic.type.TypeArray;
import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;
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
	
	// accesoVector ::= IDENTIFICADOR:identificador OPEN_BRACKET:openBracket expresion:expresion CLOSE_BRACKET:closeBracket
	public static AccesoVector produccion(TokenIF identificador, TokenIF openBracket, Expresion expresion, TokenIF closeBracket) {

		String lexema = identificador.getLexema() + openBracket.getLexema() + expresion.getLexema() + closeBracket.getLexema();

    	Consola.log("accesoVector[1]: \n" + lexema);
 	
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
 		
 		String nombreVector = identificador.getLexema();
 		
 		// Comprobabr que la expresion de acceso es de tipo numerica
 		if (!tipoEntero.equals(expresion.getTipo())) {
 		
 			Contexto.semanticErrorManager.semanticFatalError("Error, expresion de acceso no es de tipo entero: " + expresion.getTipo().getName());
		}
			
		// Comprobabr que el simbolo del vector existe
		if (!Contexto.scopeManager.containsSymbol(nombreVector)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, simbolo no definido: " + nombreVector);
		}
		
		SymbolIF simboloVector = Contexto.scopeManager.searchSymbol(nombreVector);
		
		TypeIF tipoVector = simboloVector.getType();
	
		// Comprobar que el symbolo del vector es de tipo vector
		if (!(tipoVector instanceof TypeArray)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, expresion de acceso solo permitida sobre vectores");
		
		}
		
		TypeIF tipoElemento = ((TypeArray)tipoVector).getTipoElemento();
	
		return new AccesoVector(lexema, tipoElemento);
		
	}
	
}
