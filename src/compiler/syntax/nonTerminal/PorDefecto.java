package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.Consola;
import compiler.utils.UtilsTiposDevuelve;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class PorDefecto extends NonTerminal {

	private List<TypeIF> tiposDevuelve = new ArrayList<>();
			
	public PorDefecto(String lexema, List<TypeIF> tiposDevuelve) {
	
		super(lexema);
		
		this.tiposDevuelve.addAll(tiposDevuelve);

	}

	public List<TypeIF> getTiposDevuelve() {
	
		return new ArrayList<>(this.tiposDevuelve);
	
	}
	
	// porDefecto ::= PORDEFECTO:pordefecto TWO_POINTS:twoPoints bloque:bloque CORTE:corte SEMI_COLON:semiColon
	public static PorDefecto produccion_PORDEFECTO_TWO_POINTS_bloque_CORTE_SEMI_COLON(TokenIF pordefecto, TokenIF twoPoints, Bloque bloque, TokenIF corte, TokenIF semiColon) {

		String lexema = pordefecto.getLexema() + twoPoints.getLexema() + " " + bloque.getLexema() + " " + corte.getLexema() + semiColon.getLexema();

		Consola.log("porDefecto[1]: \n" + lexema);

		return new PorDefecto(lexema, bloque.getTiposDevuelve());

	}

	// porDefecto ::= epsilon:epsilon
	public static PorDefecto produccion_epsilon(Epsilon epsilon) {

		String lexema = epsilon.getLexema();
	
		Consola.log("porDefecto[2]: \n" + lexema);
		
		return new PorDefecto(lexema, UtilsTiposDevuelve.ramaSinDevuelve());

	}
	
}
