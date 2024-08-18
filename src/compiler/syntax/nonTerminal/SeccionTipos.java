package compiler.syntax.nonTerminal;


import compiler.utils.Consola;

public class SeccionTipos extends NonTerminal {

	public SeccionTipos(String lexema) {
		
		super(lexema);
		
	}
	
	// seccionTipos ::= declaracionTipo:declaracionTipo seccionTipos:seccionTipos
	public static SeccionTipos produccion_declaracionTipo_seccionTipos(DeclaracionTipo declaracionTipo, SeccionTipos seccionTipos) {

		String lexema = declaracionTipo.getLexema() + "\n" + seccionTipos.getLexema();
	
		Consola.log("seccionTipos[1]: \n" + lexema);
		
		return new SeccionTipos(lexema);
		
	}

	// seccionTipos ::= epsilon:epsilon
	public static SeccionTipos produccion_epsilon(Epsilon epsilon) {

		String lexema = epsilon.getLexema();
	
		Consola.log("seccionTipos[2]: \n" + lexema);
		
		return new SeccionTipos(lexema);
		
	}
	
}
