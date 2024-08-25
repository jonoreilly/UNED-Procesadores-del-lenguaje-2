package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.utils.Consola;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.lexical.TokenIF;

public class OpcionesEscribe extends NonTerminal {

	public OpcionesEscribe(String lexema, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
	}
	
	// opcionesEscribe ::= epsilon:epsilon
	public static OpcionesEscribe produccion_epsilon(Epsilon epsilon) {
		
		String lexema = epsilon.getLexema();
	
		Consola.log("opcionesEscribe[1]: \n" + lexema);
		
		return new OpcionesEscribe(lexema);
		
	}

	// opcionesEscribe ::= STRING:string
	public static OpcionesEscribe produccion_STRING(TokenIF string) {
		
		String lexema = string.getLexema();
	
		Consola.log("opcionesEscribe[2]: \n" + lexema);
		
		return new OpcionesEscribe(lexema);
		
	}
	
}
