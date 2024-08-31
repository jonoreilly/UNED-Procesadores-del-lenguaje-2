package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import compiler.utils.IdDatos;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.semantic.ScopeIF;

public class ListadoIDs extends NonTerminal {

	private List<IdDatos> ids = new ArrayList<>();
	
	public ListadoIDs(String lexema, List<IdDatos> ids, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);

		this.ids.addAll(ids);
		
	}
	
	public List<IdDatos> getIds() {
		
		return new ArrayList<>(this.ids);
	
	}
	
	// listadoIDs ::= id:id listadoIDs1:listadoIDs1
	public static ListadoIDs produccion(Id id, ListadoIDs1 listadoIDs1) {

		String lexema = id.getLexema() + " " + listadoIDs1.getLexema();
		
		Consola.log("listadoIDs[1]: \n" + lexema); 
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);
		
		List<IdDatos> ids = new ArrayList<>();
		
		ids.add(new IdDatos(id.getNombre(), id.getValor()));
		
		ids.addAll(listadoIDs1.getIds());
 		
 		// Encapsular codigo intermedio de las subexpresiones
 		 		
 		intermediateCodeBuilder.addQuadruples(id.getIntermediateCode());

 		intermediateCodeBuilder.addQuadruples(listadoIDs1.getIntermediateCode());
		
		return new ListadoIDs(lexema, ids, intermediateCodeBuilder.create());
		
	}
	
}
