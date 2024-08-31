package compiler.syntax.nonTerminal;

import java.util.ArrayList;

import compiler.utils.Consola;
import es.uned.lsi.compiler.lexical.TokenIF;

public class Id extends NonTerminal {

	private String nombre;
	
	private Integer valor;
	
	public Id(String lexema, String nombre, Integer valor) {
		
		super(lexema, new ArrayList<>());
		
		this.nombre = nombre;

		this.valor = valor;
		
	}
	
	public String getNombre() {
		
		return this.nombre;
		
	}
	
	public Integer getValor() {
		
		return this.valor;
		
	}
	
	// id ::= IDENTIFICADOR:identificador id1:id1
	public static Id produccion(TokenIF identificador, Id1 id1) {

		String lexema = identificador.getLexema() + " " + id1.getLexema();
		
		Consola.log("id[1]: \n" + lexema);
		
		String nombreVariable = identificador.getLexema();
   				
		return new Id(lexema, nombreVariable, id1.getValor());
	
	}
	
}
