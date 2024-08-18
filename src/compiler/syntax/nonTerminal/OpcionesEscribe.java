package compiler.syntax.nonTerminal;

import compiler.utils.Consola;
import es.uned.lsi.compiler.lexical.TokenIF;

public class OpcionesEscribe extends NonTerminal {

	public OpcionesEscribe(String lexema) {
		
		super(lexema);
		
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
