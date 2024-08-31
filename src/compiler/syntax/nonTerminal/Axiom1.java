package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.semantic.ScopeIF;

public class Axiom1 extends NonTerminal {

	public Axiom1(String lexema, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
	}

	// axiom1 ::= seccionVariables:seccionVariables seccionFunciones:seccionFunciones
	public static Axiom1 produccion_seccionVariables_seccionFunciones(SeccionVariables seccionVariables, SeccionFunciones seccionFunciones) {

		String lexema = seccionVariables.getLexema() + "\n" + seccionFunciones.getLexema();

    	Consola.log("axiom[1]: \n" + lexema);
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);

 		// Generar codigo intermedio de esta expresion
 		
 		intermediateCodeBuilder.addQuadruples(seccionVariables.getIntermediateCode());

 		intermediateCodeBuilder.addQuadruples(seccionFunciones.getIntermediateCode());
	 	
		return new Axiom1(lexema, intermediateCodeBuilder.create());
		
	}

	// axiom1 ::= seccionFunciones:seccionFunciones
	public static Axiom1 produccion_seccionFunciones(SeccionFunciones seccionFunciones) {

		String lexema = seccionFunciones.getLexema();

    	Consola.log("axiom[2]: \n" + lexema);
	 	
		return new Axiom1(lexema, seccionFunciones.getIntermediateCode());
		
	}
	
}
