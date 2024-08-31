package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.semantic.ScopeIF;

public class SeccionConstantes extends NonTerminal {

	public SeccionConstantes(String lexema, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
	}
	
	// seccionConstantes ::= declaracionConstante:declaracionConstante seccionConstantes:seccionConstantes
	public static SeccionConstantes produccion_declaracionConstante_seccionConstantes(DeclaracionConstante declaracionConstante, SeccionConstantes seccionConstantes) {

		String lexema = declaracionConstante.getLexema() + "\n" + seccionConstantes.getLexema();
	
		Consola.log("seccionConstantes[1]: \n" + lexema);
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);

 		// Encapsular codigo intermedio de las subexpresiones
 		 		
 		intermediateCodeBuilder.addQuadruples(declaracionConstante.getIntermediateCode());

 		intermediateCodeBuilder.addQuadruples(seccionConstantes.getIntermediateCode());
 		
		return new SeccionConstantes(lexema, intermediateCodeBuilder.create());
		
	}

	// seccionConstantes ::= epsilon:epsilon
	public static SeccionConstantes produccion_epsilon(Epsilon epsilon) {

      	String lexema = epsilon.getLexema();
	
		Consola.log("seccionConstantes[2]: \n" + lexema);
		
		return new SeccionConstantes(lexema, epsilon.getIntermediateCode());
		
	}
	
}
