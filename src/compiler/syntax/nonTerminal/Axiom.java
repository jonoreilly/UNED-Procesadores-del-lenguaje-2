package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.semantic.ScopeIF;

public class Axiom extends NonTerminal {
    public Axiom(String lexema, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
	}

	// axiom ::= seccionConstantes:seccionConstantes seccionTipos:seccionTipos axiom1:axiom1
	public static Axiom produccion(SeccionConstantes seccionConstantes, SeccionTipos seccionTipos, Axiom1 axiom1) {

		String lexema = seccionConstantes.getLexema() + "\n" + seccionTipos.getLexema() + "\n" + axiom1.getLexema();

    	Consola.log("axiom[1]: \n" + lexema);
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);

 		// Generar codigo intermedio de esta expresion
 		
 		intermediateCodeBuilder.addQuadruples(seccionConstantes.getIntermediateCode());

 		intermediateCodeBuilder.addQuadruples(seccionTipos.getIntermediateCode());
 		
 		intermediateCodeBuilder.addQuadruples(axiom1.getIntermediateCode());
	 	
		return new Axiom(lexema, intermediateCodeBuilder.create());
		
	}
    
}
