package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.Consola;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class CasoAlt extends NonTerminal {

	private Integer valorCondicion;
	
	private List<TypeIF> tiposDevuelve = new ArrayList<>();
			
	public CasoAlt(String lexema, Integer valorCondicion, List<TypeIF> tiposDevuelve, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
		this.valorCondicion = valorCondicion;
		
		this.tiposDevuelve.addAll(tiposDevuelve);
		
	}
	
	public Integer getValorCondicion() {
		
		return this.valorCondicion;
		
	}

	public List<TypeIF> getTiposDevuelve() {
		
		return new ArrayList<>(this.tiposDevuelve);
		
	}
	
	// casoAlt ::= CASO:caso NUMERO:numero TWO_POINTS:twoPoints bloque:bloque CORTE:corte SEMI_COLON:semiColon
	public static CasoAlt produccion(TokenIF caso, TokenIF numero, TokenIF twoPoints, Bloque bloque, TokenIF corte, TokenIF semiColon) {

		String lexema = caso.getLexema() + " " + numero.getLexema() + twoPoints.getLexema() + " " + bloque.getLexema() + " " + corte.getLexema() + semiColon.getLexema();

		Consola.log("casoAlt[1]: \n" + lexema);
		
		Integer valorCondicion = Integer.valueOf(numero.getLexema());

		return new CasoAlt(lexema, valorCondicion, bloque.getTiposDevuelve(), bloque.getIntermediateCode());

	}
	
}
