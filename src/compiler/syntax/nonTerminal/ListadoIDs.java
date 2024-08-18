package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.Consola;
import compiler.utils.IdDatos;

public class ListadoIDs extends NonTerminal {

	private List<IdDatos> ids = new ArrayList<>();
	
	public ListadoIDs(String lexema, IdDatos id, List<IdDatos> ids) {
		super(lexema);
		
		this.ids.add(id);

		this.ids.addAll(ids);
	}
	
	public List<IdDatos> getIds() {
		return new ArrayList<>(this.ids);
	}
	
	// listadoIDs ::= id:id listadoIDs1:listadoIDs1
	public static ListadoIDs produccion(Id id, ListadoIDs1 listadoIDs1) {

		String lexema = id.getLexema() + " " + listadoIDs1.getLexema();
		
		Consola.log("listadoIDs[1]: \n" + lexema); 
		
		return new ListadoIDs(lexema, new IdDatos(id.getNombre(), id.getValor()), listadoIDs1.getIds());
		
	}
	
}
