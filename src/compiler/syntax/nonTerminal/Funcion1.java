package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import compiler.utils.UtilsTiposDevuelve;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class Funcion1 extends NonTerminal {

	private TypeIF tipoDevuelve;
			
	public Funcion1(String lexema, TypeIF tipoDevuelve, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
		this.tipoDevuelve = tipoDevuelve;
		
	}

	public TypeIF getTipoDevuelve() {
		
		return this.tipoDevuelve;
		
	}
	
	// funcion1 ::= listadoSentencias:listadoSentencias CLOSE_PARENTHESIS:closeParenthesis
	public static Funcion1 produccion_listadoSentencias_CLOSE_PARENTHESIS(ListadoSentencias listadoSentencias, TokenIF closeParenthesis) {

		String lexema = listadoSentencias.getLexema() + "\n" + closeParenthesis.getLexema();

    	Consola.log("funcion1[1]: \n" + lexema);
        	
		List<TypeIF> tiposDevuelve = listadoSentencias.getTiposDevuelve();
		
		// Comprobar si todas las ramas de la funcion devuelven
		if (UtilsTiposDevuelve.tieneRamasSinResolver(tiposDevuelve)) {
			
			Contexto.semanticErrorManager.semanticFatalError("Error, la funcion tiene ramas sin resolver");
				
		} 
		
		// Comprobar que hay solo un tipo devuelto
		if(tiposDevuelve.size() > 1) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, la funcion tiene devuelve multiples tipos");
				
		} 
		
		// Comprobar que la funcion devuelve
		if (tiposDevuelve.size() == 0) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, la funcion no devuelve");
			
		} 
		
		return new Funcion1(lexema, tiposDevuelve.get(0), listadoSentencias.getIntermediateCode());

	}

	// funcion1 ::= seccionVariables:seccionVariables listadoSentencias:listadoSentencias  CLOSE_PARENTHESIS:closeParenthesis
	public static Funcion1 produccion_seccionVariables_listadoSentencias_CLOSE_PARENTHESIS(SeccionVariables seccionVariables, ListadoSentencias listadoSentencias, TokenIF closeParenthesis) {

		String lexema = seccionVariables.getLexema() + "\n" + listadoSentencias.getLexema() + "\n" + closeParenthesis.getLexema();

    	Consola.log("funcion1[2]: \n" + lexema);
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);
    	
		List<TypeIF> tiposDevuelve = listadoSentencias.getTiposDevuelve();
		
		// Comprobar si todas las ramas de la funcion devuelven
		if (UtilsTiposDevuelve.tieneRamasSinResolver(tiposDevuelve)) {
			
			Contexto.semanticErrorManager.semanticFatalError("Error, la funcion tiene ramas sin resolver");
				
		} 
		
		// Comprobar que hay solo un tipo devuelto
		if(tiposDevuelve.size() > 1) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, la funcion tiene devuelve multiples tipos");
				
		} 
		
		// Comprobar que la funcion devuelve
		if (tiposDevuelve.size() == 0) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, la funcion no devuelve");
			
		}

 		// Encapsular codigo intermedio de las subexpresiones
 		
 		intermediateCodeBuilder.addQuadruples(seccionVariables.getIntermediateCode());

 		intermediateCodeBuilder.addQuadruples(listadoSentencias.getIntermediateCode());
		
		return new Funcion1(lexema, tiposDevuelve.get(0), intermediateCodeBuilder.create());
	}
	
}
