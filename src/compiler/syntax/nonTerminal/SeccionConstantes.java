package compiler.syntax.nonTerminal;

import compiler.utils.Consola;

public class SeccionConstantes extends NonTerminal {

	public SeccionConstantes(String lexema) {
		
		super(lexema);
		
	}
	
	// seccionConstantes ::= declaracionConstante:declaracionConstante seccionConstantes:seccionConstantes
	public static SeccionConstantes produccion_declaracionConstante_seccionConstantes(DeclaracionConstante declaracionConstante, SeccionConstantes seccionConstantes) {

		String lexema = declaracionConstante.getLexema() + "\n" + seccionConstantes.getLexema();
	
		Consola.log("seccionConstantes[1]: \n" + lexema);
		
		return new SeccionConstantes(lexema);
		
	}

	// seccionConstantes ::= epsilon:epsilon
	public static SeccionConstantes produccion_epsilon(Epsilon epsilon) {

      	String lexema = epsilon.getLexema();
	
		Consola.log("seccionConstantes[2]: \n" + lexema);
		
		return new SeccionConstantes(lexema);
		
	}
	
}
