package compiler.syntax.nonTerminal;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class SentenciaAsignacion extends NonTerminal {

	public SentenciaAsignacion(String lexema) {
		super(lexema);
	}

	// sentenciaAsignacion ::= ref:ref ASSIGN:assign expresion:expresion SEMI_COLON:semiColon
	public static SentenciaAsignacion produccion(Ref ref, TokenIF assign, Expresion expresion, TokenIF semiColon) {

		String lexema = ref.getLexema() + " " + assign.getLexema() + " " + expresion.getLexema() + semiColon.getLexema();
		
		Consola.log("sentenciaAsignacion[1]: \n" + lexema);

		TypeIF tipoRef = ref.getTipo();
		
		TypeIF tipoExpresion = expresion.getTipo();

		// Comprobar que ref y expresion son del mismo tipo
		if (!tipoRef.equals(tipoExpresion)) {
			
			Contexto.semanticErrorManager.semanticFatalError("Error, ambos lados de una asignacion deben tener el mismo tipo: " + tipoRef.getName() + " , " + tipoExpresion.getName());
			
		} 
			
		return new SentenciaAsignacion(lexema);
		
	}
	
}
