package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import compiler.utils.ParametroDatos;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.ScopeIF;

public class DeclaracionParametros extends NonTerminal {

	public List<ParametroDatos> parametros = new ArrayList<>();
	
	public DeclaracionParametros(String lexema, List<ParametroDatos> parametros, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
		this.parametros.addAll(parametros);
	
	}
	
	public List<ParametroDatos> getParametros() {
	
		return new ArrayList<>(this.parametros);
	
	}
	
	// declaracionParametros ::= parametro:parametro
	public static DeclaracionParametros produccion_parametro(Parametro parametro) {

		String lexema = parametro.getLexema();

    	Consola.log("declaracionParametros[1]: \n" + lexema);
    	
    	List<ParametroDatos> parametrosDatos = new ArrayList<>();
    	    	
    	parametrosDatos.add(new ParametroDatos(parametro.getTipo(), parametro.getNombre()));
	
		return new DeclaracionParametros(lexema, parametrosDatos, parametro.getIntermediateCode());
	
	}

	// declaracionParametros ::= parametro:parametro COLON:colon declaracionParametros:declaracionParametros
	public static DeclaracionParametros produccion_parametro_COLON_declaracionParametros(Parametro parametro, TokenIF colon, DeclaracionParametros declaracionParametros) {

		String lexema = parametro.getLexema() + colon.getLexema() + " " + declaracionParametros.getLexema();
		
    	Consola.log("declaracionParametros[2]: \n" + lexema);
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);
    	
    	List<ParametroDatos> parametrosDatos = new ArrayList<>();
	
    	parametrosDatos.add(new ParametroDatos(parametro.getTipo(), parametro.getNombre()));
    	
    	parametrosDatos.addAll(declaracionParametros.getParametros());

 		// Encapsular codigo intermedio de las subexpresiones
 		 		
 		intermediateCodeBuilder.addQuadruples(parametro.getIntermediateCode());

 		intermediateCodeBuilder.addQuadruples(declaracionParametros.getIntermediateCode());
    	
		return new DeclaracionParametros(lexema, parametrosDatos, intermediateCodeBuilder.create());
		
	}
	
}
