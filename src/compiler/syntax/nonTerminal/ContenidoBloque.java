package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class ContenidoBloque extends NonTerminal {

	private List<TypeIF> tiposDevuelve = new ArrayList<>();
			
	public ContenidoBloque(String lexema, List<TypeIF> tiposDevuelve, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
		this.tiposDevuelve.addAll(tiposDevuelve);
		
	}

	public List<TypeIF> getTiposDevuelve() {
		
		return new ArrayList<>(this.tiposDevuelve);
		
	}
	
	// contenidoBloque ::= seccionVariables:seccionVariables listadoSentencias:listadoSentencias
	public static ContenidoBloque produccion_seccionVariables_listadoSentencias(SeccionVariables seccionVariables, ListadoSentencias listadoSentencias) {

		String lexema = seccionVariables.getLexema() + "\n" + listadoSentencias.getLexema();

		Consola.log("contenidoBloque[1]: \n" + lexema);
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);
 		
 		// Encapsular codigo intermedio de las subexpresiones
 		 		
 		intermediateCodeBuilder.addQuadruples(seccionVariables.getIntermediateCode());
	 		
 		intermediateCodeBuilder.addQuadruples(listadoSentencias.getIntermediateCode());

		return new ContenidoBloque(lexema, listadoSentencias.getTiposDevuelve(), intermediateCodeBuilder.create());

	}

	// contenidoBloque ::= listadoSentencias:listadoSentencias
	public static ContenidoBloque produccion_listadoSentencias(ListadoSentencias listadoSentencias) {

		String lexema = listadoSentencias.getLexema();
	
		Consola.log("contenidoBloque[2]: \n" + lexema);
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);
 		
 		// Encapsular codigo intermedio de las subexpresiones
	 		
 		intermediateCodeBuilder.addQuadruples(listadoSentencias.getIntermediateCode());

		return new ContenidoBloque(lexema, listadoSentencias.getTiposDevuelve(), intermediateCodeBuilder.create());

	}
	
}
