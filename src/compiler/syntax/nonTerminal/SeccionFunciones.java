package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.semantic.ScopeIF;

public class SeccionFunciones extends NonTerminal {
	
	public SeccionFunciones(String lexema, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
	
	}

	// seccionFunciones ::= funcion:funcion seccionFunciones:seccionFunciones
	public static SeccionFunciones produccion_funcion_seccionFunciones(Funcion funcion, SeccionFunciones seccionFunciones) {

		String lexema = funcion.getLexema() + seccionFunciones.getLexema();

		Consola.log("seccionFunciones[1]: \n" + lexema);
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);

 		// Encapsular codigo intermedio de las subexpresiones
 		
 		intermediateCodeBuilder.addQuadruples(funcion.getIntermediateCode());

 		intermediateCodeBuilder.addQuadruples(seccionFunciones.getIntermediateCode());
		
		return new SeccionFunciones(lexema, intermediateCodeBuilder.create()); 

	}

	// seccionFunciones ::= funcionPrincipal:funcionPrincipal
	public static SeccionFunciones produccion_funcionPrincipal(FuncionPrincipal funcionPrincipal) {

		String lexema = funcionPrincipal.getLexema();

    	Consola.log("seccionFunciones[2]: \n" + lexema); 
    	
		return new SeccionFunciones(lexema, funcionPrincipal.getIntermediateCode());

	}
	
}
