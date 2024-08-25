package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.Consola;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
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

		return new ContenidoBloque(lexema, listadoSentencias.getTiposDevuelve());

	}

	// contenidoBloque ::= listadoSentencias:listadoSentencias
	public static ContenidoBloque produccion_listadoSentencias(ListadoSentencias listadoSentencias) {

		String lexema = listadoSentencias.getLexema();
	
		Consola.log("contenidoBloque[2]: \n" + lexema);

		return new ContenidoBloque(lexema, listadoSentencias.getTiposDevuelve());

	}
	
}
