package compiler.syntax.nonTerminal;

import compiler.utils.Consola;

public class SeccionVariables extends NonTerminal {

	public SeccionVariables(String lexema) {
		
		super(lexema);
		
	}
	
	// seccionVariables ::= seccionVariables:seccionVariables declaracionVariable:declaracionVariable
	public static SeccionVariables produccion_seccionVariables_declaracionVariable(SeccionVariables seccionVariables, DeclaracionVariable declaracionVariable) {

		String lexema = seccionVariables.getLexema() + "\n" + declaracionVariable.getLexema();

		Consola.log("seccionVariables[1]: \n" + lexema); 
		
		return new SeccionVariables(lexema);
		
	}

	// seccionVariables ::= declaracionVariable:declaracionVariable
	public static SeccionVariables produccion_declaracionVariable(DeclaracionVariable declaracionVariable) {

		String lexema = declaracionVariable.getLexema();

		Consola.log("seccionVariables[2]: \n" + lexema); 
		
		return new SeccionVariables(lexema);
		
	}
	
}
