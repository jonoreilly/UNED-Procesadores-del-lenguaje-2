package compiler.syntax.nonTerminal;

import compiler.utils.Consola;

public class SeccionFunciones extends NonTerminal {
	
	public SeccionFunciones(String lexema) {
		
		super(lexema);
	
	}

	// seccionFunciones ::= funcion:funcion seccionFunciones:seccionFunciones
	public static SeccionFunciones produccion_funcion_seccionFunciones(Funcion funcion, SeccionFunciones seccionFunciones) {

		String lexema = funcion.getLexema() + seccionFunciones.getLexema();

		Consola.log("seccionFunciones[1]: \n" + lexema);
		
		return new SeccionFunciones(lexema); 

	}

	// seccionFunciones ::= funcionPrincipal:funcionPrincipal
	public static SeccionFunciones produccion_funcionPrincipal(FuncionPrincipal funcionPrincipal) {

		String lexema = funcionPrincipal.getLexema();

    	Consola.log("seccionFunciones[2]: \n" + lexema); 
    	
		return new SeccionFunciones(lexema);

	}
	
}
