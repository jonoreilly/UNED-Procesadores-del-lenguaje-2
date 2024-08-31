package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.CasoAltDatos;
import compiler.utils.Consola;
import compiler.utils.UtilsTiposDevuelve;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class CasosAlternativa extends NonTerminal {

	private List<TypeIF> tiposDevuelve = new ArrayList<>();
	
	private List<CasoAltDatos> casosAltDatos = new ArrayList<>();
		
	public CasosAlternativa(String lexema, List<TypeIF> tiposDevuelve, List<CasoAltDatos> casosAltDatos, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
		this.tiposDevuelve.addAll(tiposDevuelve);
		
		this.casosAltDatos.addAll(casosAltDatos);
		
	}

	public List<TypeIF> getTiposDevuelve() {
		
		return new ArrayList<>(this.tiposDevuelve);
		
	}

	public List<CasoAltDatos> getCasosAltDatos() {
		
		return new ArrayList<>(this.casosAltDatos);
		
	}
	
	// casosAlternativa ::= casosAlternativa:casosAlternativa casoAlt:casoAlt
	public static CasosAlternativa produccion_casosAlternativa_casoAlt(CasosAlternativa casosAlternativa, CasoAlt casoAlt) {

		String lexema = casosAlternativa.getLexema() + "\n" + casoAlt.getLexema();

		Consola.log("casosAlternativa[1]: \n" + lexema);
		
		List<TypeIF> tiposDevuelveCasosAlternativa = casosAlternativa.getTiposDevuelve();
		
		List<TypeIF> tiposDevuelveCasoAlt = casoAlt.getTiposDevuelve();

		List<TypeIF> tiposDevuelve = UtilsTiposDevuelve.unirRamas(tiposDevuelveCasosAlternativa, tiposDevuelveCasoAlt);

		List<CasoAltDatos> casosAltDatos = new ArrayList<>();
		
		casosAltDatos.addAll(casosAlternativa.getCasosAltDatos());
		
		casosAltDatos.add(new CasoAltDatos(casoAlt.getValorCondicion(), casoAlt.getIntermediateCode()));
						
		return new CasosAlternativa(lexema, tiposDevuelve, casosAltDatos, new ArrayList<>());
		
	}
	
	// casosAlternativa ::= casoAlt:casoAlt
	public static CasosAlternativa produccion_casoAlt(CasoAlt casoAlt) {
		
		String lexema = casoAlt.getLexema();
	
		Consola.log("casosAlternativa[2]: \n" + lexema);

		List<TypeIF> tiposDevuelve = casoAlt.getTiposDevuelve();

		List<CasoAltDatos> casosAltDatos = new ArrayList<>();
		
		casosAltDatos.add(new CasoAltDatos(casoAlt.getValorCondicion(), casoAlt.getIntermediateCode()));
		
		return new CasosAlternativa(lexema, tiposDevuelve, casosAltDatos, new ArrayList<>());
		
	}
		
}
