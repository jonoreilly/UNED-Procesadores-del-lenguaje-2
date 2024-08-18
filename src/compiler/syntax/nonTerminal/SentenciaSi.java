package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.UtilsTiposDevuelve;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class SentenciaSi extends NonTerminal {

	private List<TypeIF> tiposDevuelve = new ArrayList<>();
	
	public SentenciaSi(String lexema, List<TypeIF> tiposDevuelveSi) {

		this(lexema, tiposDevuelveSi, UtilsTiposDevuelve.ramaSinDevuelve());
			
	}

	public SentenciaSi(String lexema, List<TypeIF> tiposDevuelveSi, List<TypeIF> tiposDevuelveSino) {
	
		super(lexema);

		this.tiposDevuelve.addAll(
				UtilsTiposDevuelve.unirRamas(tiposDevuelveSi, tiposDevuelveSino)
			);
	
	}
	
	public List<TypeIF> getTiposDevuelve() {
	
		return new ArrayList<>(this.tiposDevuelve);
	
	}
	
}
