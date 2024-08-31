package compiler.syntax.nonTerminal;

import java.util.ArrayList;

import compiler.utils.Consola;
import es.uned.lsi.compiler.lexical.TokenIF;

public class Id1 extends NonTerminal {

	private Integer valor;
	
	public Id1(String lexema, Integer valor) {
		
		super(lexema, new ArrayList<>());
		
		this.valor = valor;
		
	}
	
	public Integer getValor() {
		
		return this.valor;
		
	}
	
	// id1 ::= epsilon:epsilon
	public static Id1 produccion_epsilon(Epsilon epsilon) {

		String lexema = epsilon.getLexema();
	
		Consola.log("id1[1]: \n" + lexema);
		
		return new Id1(lexema, null);
		
	}
	
	// id1 ::= ASSIGN:assign NUMERO:numero
	public static Id1 produccion_ASSIGN_NUMERO(TokenIF assign, TokenIF numero) {

		String lexema = assign.getLexema() + " " + numero.getLexema();
	
		Consola.log("id1[2]: \n" + lexema);
    		
    	int valor = Integer.parseInt(numero.getLexema());
    	
    	return new Id1(lexema, valor);
    	
	}
	
}
