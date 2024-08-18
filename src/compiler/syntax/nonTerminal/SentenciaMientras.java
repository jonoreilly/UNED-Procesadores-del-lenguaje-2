package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.UtilsTiposDevuelve;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class SentenciaMientras extends NonTerminal {

	private List<TypeIF> tiposDevuelve = new ArrayList<>();
			
	public SentenciaMientras(String lexema, List<TypeIF> tiposDevuelve) {
	
		super(lexema);
		
		this.tiposDevuelve.addAll(
				UtilsTiposDevuelve.unirRamas(
						tiposDevuelve,
						UtilsTiposDevuelve.ramaSinDevuelve()
					)
			);
	
	}

	public List<TypeIF> getTiposDevuelve() {
	
		return new ArrayList<>(this.tiposDevuelve);
	
	}
	
}
