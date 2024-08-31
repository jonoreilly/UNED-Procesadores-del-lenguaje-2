package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class OpcionesEscribeEnt extends NonTerminal {

	/** Null si no tiene parametros */
	private TemporalIF temporal;

	public OpcionesEscribeEnt(String lexema, TemporalIF temporal, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
		this.temporal = temporal;
		
	}

	/** @return Null si no tiene parametros */
	public TemporalIF getTemporal() {
		
		return this.temporal;
		
	}
	
	// opcionesEscribeEnt ::= epsilon:epsilon
	public static OpcionesEscribeEnt produccion_epsilon(Epsilon epsilon) {

		String lexema = epsilon.getLexema();
	
		Consola.log("opcionesEscribeEnt[1]: \n" + lexema);
				
		return new OpcionesEscribeEnt(lexema, null, new ArrayList<>());
		
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
				
		return new OpcionesEscribeEnt(lexema, expresion.getTemporal(), expresion.getIntermediateCode());
		
	}
	
}
