package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import compiler.utils.Consola;
import compiler.utils.ParametroDatos;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.lexical.TokenIF;

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
    	
    	ParametroDatos parametroDatos = new ParametroDatos(parametro.getTipo(), parametro.getNombre());
    	
    	List<ParametroDatos> parametrosDatos = Arrays.asList(parametroDatos);
	
		return new DeclaracionParametros(lexema, parametrosDatos);
	
	}

	// declaracionParametros ::= parametro:parametro COLON:colon declaracionParametros:declaracionParametros
	public static DeclaracionParametros produccion_parametro_COLON_declaracionParametros(Parametro parametro, TokenIF colon, DeclaracionParametros declaracionParametros) {

		String lexema = parametro.getLexema() + colon.getLexema() + " " + declaracionParametros.getLexema();
		
    	Consola.log("declaracionParametros[2]: \n" + lexema);
	
    	ParametroDatos parametroDatos = new ParametroDatos(parametro.getTipo(), parametro.getNombre());

    	List<ParametroDatos> parametrosDatos = Arrays.asList(parametroDatos);
    	
    	parametrosDatos.addAll(declaracionParametros.getParametros());
    	
		return new DeclaracionParametros(lexema, parametroDatos);
		
	}
	
}
