package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import es.uned.lsi.compiler.semantic.type.TypeIF;

public class Bloque extends NonTerminal {

	private List<TypeIF> tiposDevuelve = new ArrayList<>();
			
	public Bloque(String lexema, List<TypeIF> tiposDevuelve) {
		
		super(lexema);
		
		this.tiposDevuelve.addAll(tiposDevuelve);
		
	}

	public List<TypeIF> getTiposDevuelve() {
		
		return new ArrayList<>(this.tiposDevuelve);
		
	}
	
}
