package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.intermediate.Value;
import compiler.intermediate.Variable;
import compiler.semantic.symbol.SymbolVariable;
import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class SentenciaIncremento extends NonTerminal {

	public SentenciaIncremento(String lexema, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
	}
	
	// sentenciaIncremento ::= IDENTIFICADOR:identificador AUTO_INCREMENTO:autoIncremento SEMI_COLON:semiColon
	public static SentenciaIncremento produccion(TokenIF identificador, TokenIF autoIncremento, TokenIF semiColon) {

		String lexema = identificador.getLexema() + autoIncremento.getLexema() + semiColon.getLexema();

		Consola.log("sentenciaIncremento[1]: \n" + lexema);
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);

		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
		
		String nombreVariable = identificador.getLexema();
		
		SymbolIF simbolo = Contexto.scopeManager.searchSymbol(nombreVariable);
		
		TypeIF tipoVariable = simbolo.getType();
		
		// Comprobar que la variable es de tipo entero
		if (!tipoEntero.equals(tipoVariable)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, solo se pueden autoincrementar variables de tipo entero: " + nombreVariable);
	
		} 
		
		// Comprobar que el simbolo es una variable, y no una constante, funcion...
		if (!(simbolo instanceof SymbolVariable)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, solo se pueden autoincrementar variables: " + nombreVariable);
		
		}
		
		// Generar codigo intermedio

		Variable var = new Variable(nombreVariable, simbolo.getScope());
 		
 		intermediateCodeBuilder.addQuadruple("ADD", var, var, new Value(1));
		
		return new SentenciaIncremento(lexema, intermediateCodeBuilder.create());
		
	}
	
}
