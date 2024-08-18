package compiler.syntax.nonTerminal;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class SentenciaAsignacionSuma extends NonTerminal {

	public SentenciaAsignacionSuma(String lexema) {
		super(lexema);
	}
	
	// sentenciaAsignacionSuma ::= ref:ref ASSIGN_SUMA:assignSuma expresion:expresion SEMI_COLON:semiColon
	public static SentenciaAsignacionSuma produccion(Ref ref, TokenIF assignSuma, Expresion expresion, TokenIF semiColon) {

		String lexema = ref.getLexema() + " " + assignSuma.getLexema() + " " + expresion.getLexema() + semiColon.getLexema();
		
		Consola.log("sentenciaAsignacionSuma[1]: \n" + lexema); 

		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
				
		TypeIF tipoRef = ref.getTipo();
		
		TypeIF tipoExpresion = expresion.getTipo();

		// Comprobar que ref y expresion son del mismo tipo
		if (!tipoEntero.equals(tipoRef) || !tipoEntero.equals(tipoExpresion)) {
			
			Contexto.semanticErrorManager.semanticFatalError("Error, ambos lados de una asignacion suma deben ser de tipo numerico: " + tipoRef.getName() + " , " + tipoExpresion.getName());
			
		} 

		return new SentenciaAsignacionSuma(lexema);
		
	}
	
}
