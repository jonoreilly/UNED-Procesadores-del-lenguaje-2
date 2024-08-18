package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.UtilsTiposDevuelve;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class PorDefecto extends NonTerminal {

	private List<TypeIF> tiposDevuelve = new ArrayList<>();
		
	public PorDefecto(String lexema) {
		this(lexema, UtilsTiposDevuelve.ramaSinDevuelve());
	}
	
	public PorDefecto(String lexema, List<TypeIF> tiposDevuelve) {
		super(lexema);
		
		this.tiposDevuelve.addAll(tiposDevuelve);
	}

	public List<TypeIF> getTiposDevuelve() {
		return new ArrayList<>(this.tiposDevuelve);
	}
	
}
