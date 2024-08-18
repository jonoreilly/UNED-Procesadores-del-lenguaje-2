package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.UtilsTiposDevuelve;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class SentenciaAlternativas extends NonTerminal {

	private List<TypeIF> tiposDevuelve = new ArrayList<>();
		
	public SentenciaAlternativas(String lexema, List<TypeIF> tiposDevuelveCasos) {

		this(lexema, tiposDevuelveCasos, UtilsTiposDevuelve.ramaSinDevuelve());
		
	}

	public SentenciaAlternativas(String lexema, List<TypeIF> tiposDevuelveCasos, List<TypeIF> tiposDevuelvePorDefecto) {
		super(lexema);
		
		this.tiposDevuelve.addAll(
				UtilsTiposDevuelve.unirRamas(tiposDevuelveCasos, tiposDevuelvePorDefecto)
			);

	}

	public List<TypeIF> getTiposDevuelve() {
		return new ArrayList<>(this.tiposDevuelve);
	}
	
}
