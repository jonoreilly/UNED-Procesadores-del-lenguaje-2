package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.Consola;
import compiler.utils.ParametroDatos;

public class SeccionParametros extends NonTerminal {

	public List<ParametroDatos> parametros = new ArrayList<>();
	
	public SeccionParametros(String lexema) {
		super(lexema);
	}
	
	public SeccionParametros(String lexema, List<ParametroDatos> parametros) {
		super(lexema);
		
		this.parametros.addAll(parametros);
	}
	
	public List<ParametroDatos> getParametros() {
		return new ArrayList<>(this.parametros);
	}
	
	// seccionParametros ::= declaracionParametros:declaracionParametros
	public static SeccionParametros produccion_declaracionParametros(DeclaracionParametros declaracionParametros) {

		String lexema = declaracionParametros.getLexema();

    	Consola.log("seccionParametros[1]: \n" + lexema);

		return new SeccionParametros(lexema, declaracionParametros.getParametros());
		
	}
	
	// seccionParametros ::= epsilon:epsilon
	public static SeccionParametros produccion_epsilon(Epsilon epsilon) {

		String lexema = epsilon.getLexema();
	
    	Consola.log("seccionParametros[2]: \n" + lexema);
	
		return new SeccionParametros(lexema);
		
	}
}
