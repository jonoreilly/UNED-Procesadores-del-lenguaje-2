package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.intermediate.TemporalFactory;
import es.uned.lsi.compiler.intermediate.TemporalFactoryIF;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class SentenciaAsignacionSuma extends NonTerminal {

	public SentenciaAsignacionSuma(String lexema, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
	}
	
	// sentenciaAsignacionSuma ::= ref:ref ASSIGN_SUMA:assignSuma expresion:expresion SEMI_COLON:semiColon
	public static SentenciaAsignacionSuma produccion(Ref ref, TokenIF assignSuma, Expresion expresion, TokenIF semiColon) {

		String lexema = ref.getLexema() + " " + assignSuma.getLexema() + " " + expresion.getLexema() + semiColon.getLexema();
		
		Consola.log("sentenciaAsignacionSuma[1]: \n" + lexema); 
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);
 		
 		TemporalFactoryIF temporalFactory = new TemporalFactory(scope);

		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
				
		TypeIF tipoRef = ref.getTipo();
		
		TypeIF tipoExpresion = expresion.getTipo();

		// Comprobar que ref y expresion son del mismo tipo
		if (!tipoEntero.equals(tipoRef) || !tipoEntero.equals(tipoExpresion)) {
			
			Contexto.semanticErrorManager.semanticFatalError("Error, ambos lados de una asignacion suma deben ser de tipo numerico: " + tipoRef.getName() + " , " + tipoExpresion.getName());
			
		} 
		
		// Generar codigo intermedio
 		
		TemporalIF temporalPunteroIzquierda = ref.getPunteroTemporal();
		
		TemporalIF temporalValorDerecha = expresion.getTemporal();
		
 		TemporalIF temporalValor = temporalFactory.create();
 		
 		intermediateCodeBuilder.addQuadruple("FIND", temporalValor, temporalPunteroIzquierda);

 		intermediateCodeBuilder.addQuadruple("ADD", temporalValor, temporalValor, temporalValorDerecha);

		intermediateCodeBuilder.addQuadruple("STORE", temporalPunteroIzquierda, temporalValor);

		return new SentenciaAsignacionSuma(lexema, intermediateCodeBuilder.create());
		
	}
	
}
