package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.intermediate.Value;
import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class SentenciaDevuelve extends NonTerminal {

	private TypeIF tipoDevuelve;
	
	public SentenciaDevuelve(String lexema, TypeIF tipoDevuelve, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
		this.tipoDevuelve = tipoDevuelve;
	
	}
	
	public TypeIF getTipoDevuelve() {
	
		return this.tipoDevuelve;
	
	}
	
	// sentenciaDevuelve ::= DEVUELVE:devuelve SEMI_COLON:semiColon
	public static SentenciaDevuelve produccion_DEVUELVE_SEMI_COLON(TokenIF devuelve, TokenIF semiColon) {

		String lexema = devuelve.getLexema() + semiColon.getLexema();

		Consola.log("sentenciaDevuelve[1]: \n" + lexema);
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);
		
		TypeIF tipoVacio = Contexto.scopeManager.searchType("vacio");
		
		// Generar codigo intermedio
		
 		intermediateCodeBuilder.addQuadruple("RETURN",  new Value(0));
		
		return new SentenciaDevuelve(lexema, tipoVacio, intermediateCodeBuilder.create());
		
	}
	
	// sentenciaDevuelve ::= DEVUELVE:devuelve expresion:expresion SEMI_COLON:semiColon
	public static SentenciaDevuelve produccion_DEVUELVE_expresion_SEMI_COLON(TokenIF devuelve, Expresion expresion, TokenIF semiColon) {

		String lexema = devuelve.getLexema() + " " + expresion.getLexema() + semiColon.getLexema();

		Consola.log("sentenciaDevuelve[2]: \n" + lexema);
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);
		
		// Generar codigo intermedio
		
 		intermediateCodeBuilder.addQuadruple("RETURN", expresion.getTemporal());
	
		return new SentenciaDevuelve(lexema, expresion.getTipo(), intermediateCodeBuilder.create());
		
	}
	
}
