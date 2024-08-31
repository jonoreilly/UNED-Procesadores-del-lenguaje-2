package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class BloqueFuncionPrincipal extends NonTerminal {

	private List<TypeIF> tiposDevuelve = new ArrayList<>();
	
	public BloqueFuncionPrincipal(String lexema, List<TypeIF> tiposDevuelve, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
		this.tiposDevuelve.addAll(tiposDevuelve);
		
	}
	
	public List<TypeIF> getTiposDevuelve() {
		
		return new ArrayList<>(this.tiposDevuelve);
	
	}
	
	// bloqueFuncionPrincipal ::= seccionTipos:seccionTipos listadoSentencias:listadoSentencias CLOSE_PARENTHESIS:closeParenthesis
	public static BloqueFuncionPrincipal produccion_seccionTipos_listadoSentencias_CLOSE_PARENTHESIS(SeccionTipos seccionTipos, ListadoSentencias listadoSentencias, TokenIF closeParenthesis) {

		String lexema = seccionTipos.getLexema() + "\n" + listadoSentencias.getLexema() + "\n" + closeParenthesis.getLexema();

    	Consola.log("funcionPrincipal[2]: \n" + lexema);
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);

		List<TypeIF> tiposDevuelve = listadoSentencias.getTiposDevuelve();
		
 		// Encapsular codigo intermedio de las subexpresiones
 		
 		intermediateCodeBuilder.addQuadruples(seccionTipos.getIntermediateCode());

 		intermediateCodeBuilder.addQuadruples(listadoSentencias.getIntermediateCode());

		return new BloqueFuncionPrincipal(lexema, tiposDevuelve, intermediateCodeBuilder.create());
		
	}
	
	// bloqueFuncionPrincipal ::= seccionTipos:seccionTipos seccionVariables:seccionVariables listadoSentencias:listadoSentencias CLOSE_PARENTHESIS:closeParenthesis
	public static BloqueFuncionPrincipal produccion_seccionTipos_seccionVariables_listadoSentencias_CLOSE_PARENTHESIS(SeccionTipos seccionTipos, SeccionVariables seccionVariables, ListadoSentencias listadoSentencias, TokenIF closeParenthesis) {

		String lexema = seccionTipos.getLexema() + "\n" + seccionVariables.getLexema() + "\n" + listadoSentencias.getLexema() + "\n" + closeParenthesis.getLexema();

    	Consola.log("funcionPrincipal[2]: \n" + lexema);
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);

		List<TypeIF> tiposDevuelve = listadoSentencias.getTiposDevuelve();

 		// Encapsular codigo intermedio de las subexpresiones
 		
 		intermediateCodeBuilder.addQuadruples(seccionTipos.getIntermediateCode());
 		
 		intermediateCodeBuilder.addQuadruples(seccionVariables.getIntermediateCode());

 		intermediateCodeBuilder.addQuadruples(listadoSentencias.getIntermediateCode());

		return new BloqueFuncionPrincipal(lexema, tiposDevuelve, intermediateCodeBuilder.create());
		
	}
	
}
