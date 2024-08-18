package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.Consola;
import compiler.utils.UtilsTiposDevuelve;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class CasosAlternativa extends NonTerminal {

	private List<TypeIF> tiposDevuelve = new ArrayList<>();
		
	public CasosAlternativa(String lexema, List<TypeIF> tiposDevuelve) {
		
		super(lexema);
		
		this.tiposDevuelve.addAll(tiposDevuelve);
		
	}

	public CasosAlternativa(String lexema, List<TypeIF> tiposDevuelve1, List<TypeIF> tiposDevuelve2) {
		
		super(lexema);
		
		this.tiposDevuelve.addAll(
				UtilsTiposDevuelve.unirRamas(tiposDevuelve1, tiposDevuelve2)
			);
		
	}

	public List<TypeIF> getTiposDevuelve() {
		
		return new ArrayList<>(this.tiposDevuelve);
		
	}
	
	// casosAlternativa ::= casosAlternativa:casosAlternativa casoAlt:casoAlt
	public static CasosAlternativa produccion_casosAlternativa_casoAlt(CasosAlternativa casosAlternativa, CasoAlt casoAlt) {

		String lexema = casosAlternativa.getLexema() + "\n" + casoAlt.getLexema();

		Consola.log("casosAlternativa[1]: \n" + lexema);
		
		List<TypeIF> tiposDevuelveCasosAlternativa = casosAlternativa.getTiposDevuelve();
		
		List<TypeIF> tiposDevuelveCasoAlt = casoAlt.getTiposDevuelve();
		
		return new CasosAlternativa(lexema, tiposDevuelveCasosAlternativa, tiposDevuelveCasoAlt);
		
	}
	
	// casosAlternativa ::= casoAlt:casoAlt
	public static CasosAlternativa produccion_casoAlt(CasoAlt casoAlt) {
		
		String lexema = casoAlt.getLexema();
	
		Consola.log("casosAlternativa[2]: \n" + lexema);
		
		return new CasosAlternativa(lexema, casoAlt.getTiposDevuelve());
		
	}
		
}
