package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.Consola;
import compiler.utils.IdDatos;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.lexical.TokenIF;

public class ListadoIDs1 extends NonTerminal {

	private List<IdDatos> ids = new ArrayList<>();
		
	public ListadoIDs1(String lexema, List<IdDatos> ids, List<QuadrupleIF> intermediateCode) {
		super(lexema, intermediateCode);

		this.ids.addAll(ids);
	}
	
	public List<IdDatos> getIds() {
		return new ArrayList<>(this.ids);
	}
	
	// listadoIDs1 ::= COLON:colon listadoIDs:listadoIDs
	public static ListadoIDs1 produccion_COLON_listadoIDs(TokenIF colon, ListadoIDs listadoIDs) {

		String lexema = colon.getLexema() + " " + listadoIDs.getLexema();
	
		Consola.log("listadoIDs1[1]: \n" + lexema); 
		
		return new ListadoIDs1(lexema, listadoIDs.getIds());
		
	}
	
	// listadoIDs1 ::= epsilon:epsilon
	public static ListadoIDs1 produccion_epsilon(Epsilon epsilon) {

		String lexema = epsilon.getLexema();
	                  
		Consola.log("listadoIDs1[2]: \n" + epsilon); 
    
    	return new ListadoIDs1(lexema, new ArrayList<>());
		
	}
	
}
