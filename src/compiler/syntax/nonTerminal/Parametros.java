package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import compiler.utils.Consola;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class Parametros extends NonTerminal {

	private List<TypeIF> parametros = new ArrayList<>();
	
	public Parametros(String lexema, List<TypeIF> parametros) {
		
		super(lexema);

		this.parametros.addAll(parametros);
	
	}
	
	public List<TypeIF> getParametros() {
		
		return new ArrayList<>(this.parametros);
	
	}
	
	// parametros ::= expresion:expresion
	public static Parametros produccion_expresion(Expresion expresion) {

		String lexema = expresion.getLexema();
		
		Consola.log("parametros[1]: \n" + lexema);
		
		List<TypeIF> parametros = Arrays.asList(expresion.getTipo());

		return new Parametros(lexema, parametros);
		
	}

	// expresion:expresion COLON:colon parametros:parametros
	public static Parametros produccion_expresion_COLON_parametros(Expresion expresion, TokenIF colon, Parametros parametros) {

		String lexema = expresion.getLexema() + colon.getLexema() + " " + parametros.getLexema();
	
		Consola.log("parametros[1]: \n" + lexema);

		List<TypeIF> listaParametros = Arrays.asList(expresion.getTipo());
				
		listaParametros.addAll(parametros.getParametros());
		
		return new Parametros(lexema, listaParametros);
		
	}
	
}
