package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.UtilsTiposDevuelve;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class ListadoSentencias extends NonTerminal {

	private List<TypeIF> tiposDevuelve = new ArrayList<>();
	
	public ListadoSentencias(String lexema, List<TypeIF> tiposDevuelve) {
	
		super(lexema);
		
		this.tiposDevuelve.addAll(tiposDevuelve);

	}

	public ListadoSentencias(String lexema, List<TypeIF> tiposDevuelve1, List<TypeIF> tiposDevuelve2) {
		
		super(lexema);
				
		this.tiposDevuelve.addAll(
				UtilsTiposDevuelve.consolidarRamas(tiposDevuelve1, tiposDevuelve2)
			);
	
	}
	
	public List<TypeIF> getTiposDevuelve() {
		
		return new ArrayList<>(this.tiposDevuelve);
	
	}
}
