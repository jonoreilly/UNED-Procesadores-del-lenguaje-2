package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.semantic.ScopeIF;

public class SeccionVariables extends NonTerminal {

	public SeccionVariables(String lexema, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
	}
	
	// seccionVariables ::= seccionVariables:seccionVariables declaracionVariable:declaracionVariable
	public static SeccionVariables produccion_seccionVariables_declaracionVariable(SeccionVariables seccionVariables, DeclaracionVariable declaracionVariable) {

		String lexema = seccionVariables.getLexema() + "\n" + declaracionVariable.getLexema();

		Consola.log("seccionVariables[1]: \n" + lexema); 
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);

 		// Encapsular codigo intermedio de las subexpresiones
 		
 		intermediateCodeBuilder.addQuadruples(seccionVariables.getIntermediateCode());

 		intermediateCodeBuilder.addQuadruples(declaracionVariable.getIntermediateCode());
		
		return new SeccionVariables(lexema, intermediateCodeBuilder.create());
		
	}

	// seccionVariables ::= declaracionVariable:declaracionVariable
	public static SeccionVariables produccion_declaracionVariable(DeclaracionVariable declaracionVariable) {

		String lexema = declaracionVariable.getLexema();

		Consola.log("seccionVariables[2]: \n" + lexema); 
		
		return new SeccionVariables(lexema, declaracionVariable.getIntermediateCode());
		
	}
	
}
