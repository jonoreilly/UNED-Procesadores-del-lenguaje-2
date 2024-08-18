package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import es.uned.lsi.compiler.semantic.type.TypeIF;

public class Parametros extends NonTerminal {

	private List<TypeIF> parametros = new ArrayList<>();
	
	public Parametros(String lexema, TypeIF tipo) {
		super(lexema);
		
		this.parametros.add(tipo);
	}
	

	public Parametros(String lexema, TypeIF tipo, List<TypeIF> parametros) {
		super(lexema);

		this.parametros.add(tipo);

		this.parametros.addAll(parametros);
	}
	
	public List<TypeIF> getParametros() {
		return new ArrayList<>(this.parametros);
	}
	
}
