package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import compiler.utils.ParametroTemporal;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.ScopeIF;

public class Parametros extends NonTerminal {

	private List<ParametroTemporal> parametros = new ArrayList<>();
	
	public Parametros(String lexema, List<ParametroTemporal> parametros, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);

		this.parametros.addAll(parametros);
	
	}
	
	public List<ParametroTemporal> getParametros() {
		
		return new ArrayList<>(this.parametros);
	
	}
	
	// parametros ::= expresion:expresion
	public static Parametros produccion_expresion(Expresion expresion) {

		String lexema = expresion.getLexema();
		
		Consola.log("parametros[1]: \n" + lexema);
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);

		List<ParametroTemporal> listaParametros = new ArrayList<>();

		listaParametros.add(new ParametroTemporal(expresion.getTipo(), expresion.getTemporal()));
		
 		// Encapsular codigo intermedio de las subexpresiones
 		 		
 		intermediateCodeBuilder.addQuadruples(expresion.getIntermediateCode());

		return new Parametros(lexema, listaParametros, intermediateCodeBuilder.create());
		
	}

	// expresion:expresion COLON:colon parametros:parametros
	public static Parametros produccion_expresion_COLON_parametros(Expresion expresion, TokenIF colon, Parametros parametros) {

		String lexema = expresion.getLexema() + colon.getLexema() + " " + parametros.getLexema();
	
		Consola.log("parametros[1]: \n" + lexema);
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);

		List<ParametroTemporal> listaParametros = new ArrayList<>();

		listaParametros.add(new ParametroTemporal(expresion.getTipo(), expresion.getTemporal()));
		
 		// Encapsular codigo intermedio de las subexpresiones
				
		listaParametros.addAll(parametros.getParametros());

		return new Parametros(lexema, listaParametros, intermediateCodeBuilder.create());
		
	}
	
}
