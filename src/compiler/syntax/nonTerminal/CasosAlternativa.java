package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import compiler.utils.CasoAltDatos;
import compiler.utils.Consola;
import compiler.utils.UtilsTiposDevuelve;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class CasosAlternativa extends NonTerminal {

	private List<TypeIF> tiposDevuelve = new ArrayList<>();
	
	private List<CasoAltDatos> casosAltDatos = new ArrayList<>();
		
	public CasosAlternativa(String lexema, List<TypeIF> tiposDevuelve, List<CasoAltDatos> casosAltDatos) {
		
		super(lexema, new ArrayList<>());
		
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

		List<CasoAltDatos> casosAltDatos = new ArrayList<>(casosAlternativa.getCasosAltDatos());
		
		CasoAltDatos casoAltDatos = new CasoAltDatos(casoAlt.getValorCondicion(), casoAlt.getIntermediateCode());
		
		casosAltDatos.add(casoAltDatos);
				
		return new CasosAlternativa(lexema, tiposDevuelve, casosAltDatos);
		
	}
	
	// casosAlternativa ::= casoAlt:casoAlt
	public static CasosAlternativa produccion_casoAlt(CasoAlt casoAlt) {
		
		String lexema = casoAlt.getLexema();
	
		Consola.log("casosAlternativa[2]: \n" + lexema);

		List<TypeIF> tiposDevuelve = casoAlt.getTiposDevuelve();

		CasoAltDatos casoAltDatos = new CasoAltDatos(casoAlt.getValorCondicion(), casoAlt.getIntermediateCode());
		
		List<CasoAltDatos> casosAltDatos = Arrays.asList(casoAltDatos);
		
		return new CasosAlternativa(lexema, tiposDevuelve, casosAltDatos);
		
	}
		
}
