package compiler.syntax.nonTerminal;


import java.util.List;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.semantic.ScopeIF;

public class SeccionTipos extends NonTerminal {

	public SeccionTipos(String lexema, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
	}
	
	// seccionTipos ::= declaracionTipo:declaracionTipo seccionTipos:seccionTipos
	public static SeccionTipos produccion_declaracionTipo_seccionTipos(DeclaracionTipo declaracionTipo, SeccionTipos seccionTipos) {

		String lexema = declaracionTipo.getLexema() + "\n" + seccionTipos.getLexema();
	
		Consola.log("seccionTipos[1]: \n" + lexema);
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);

 		// Encapsular codigo intermedio de las subexpresiones
 		 		
 		intermediateCodeBuilder.addQuadruples(declaracionTipo.getIntermediateCode());

 		intermediateCodeBuilder.addQuadruples(seccionTipos.getIntermediateCode());
 		
		return new SeccionTipos(lexema, intermediateCodeBuilder.create());
		
	}

	// seccionTipos ::= epsilon:epsilon
	public static SeccionTipos produccion_epsilon(Epsilon epsilon) {

		String lexema = epsilon.getLexema();
	
		Consola.log("seccionTipos[2]: \n" + lexema);
		
		return new SeccionTipos(lexema, epsilon.getIntermediateCode());
		
	}
	
}
