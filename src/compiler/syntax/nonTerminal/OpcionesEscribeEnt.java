package compiler.syntax.nonTerminal;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class OpcionesEscribeEnt extends NonTerminal {

	public OpcionesEscribeEnt(String lexema) {
		
		super(lexema);
		
	}
	
	// opcionesEscribeEnt ::= epsilon:epsilon
	public static OpcionesEscribeEnt produccion_epsilon(Epsilon epsilon) {

		String lexema = epsilon.getLexema();
	
		Consola.log("opcionesEscribeEnt[1]: \n" + lexema);
		
		return new OpcionesEscribeEnt(lexema);
		
	}
	
	// opcionesEscribeEnt ::= expresion:expresion
	public static OpcionesEscribeEnt produccion_expresion(Expresion expresion) {

		String lexema = expresion.getLexema();

		Consola.log("opcionesEscribeEnt[2]: \n" + lexema);

		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
		
		TypeIF tipoExpresion = expresion.getTipo();

		// Comprobar que la expresion es de tipo numerico
		if (!tipoEntero.equals(tipoExpresion)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, escribeEnt solo acepta parametros de tipo numerico: " + tipoExpresion.getName());

		}
		
		return new OpcionesEscribeEnt(lexema);
		
	}
	
}
