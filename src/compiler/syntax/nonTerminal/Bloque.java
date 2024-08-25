package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class Bloque extends NonTerminal {

	private List<TypeIF> tiposDevuelve = new ArrayList<>();
			
	public Bloque(String lexema, List<TypeIF> tiposDevuelve, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
		this.tiposDevuelve.addAll(tiposDevuelve);
		
	}

	public List<TypeIF> getTiposDevuelve() {
		
		return new ArrayList<>(this.tiposDevuelve);
		
	}
	
	// bloque ::= OPEN_PARENTHESIS:openParenthesis
	public static void preProduccion(TokenIF openParenthesis) {
		
		String lexema = openParenthesis.getLexema();

		Consola.log("bloque[1]: \n" + lexema);

		Contexto.scopeManager.openScope();
		
	}
	
	// bloque ::= OPEN_PARENTHESIS:openParenthesis contenidoBloque:contenidoBloque CLOSE_PARENTHESIS:closeParenthesis
	public static Bloque produccion(TokenIF openParenthesis, ContenidoBloque contenidoBloque, TokenIF closeParenthesis) {

		String lexema = openParenthesis.getLexema() + "\n" + contenidoBloque.getLexema() + "\n" + closeParenthesis.getLexema();
	
		Consola.log("bloque[2]: \n" + lexema);
		
		Contexto.scopeManager.closeScope();
		
		return new Bloque(lexema, contenidoBloque.getTiposDevuelve(), contenidoBloque.getIntermediateCode());
		
	}
	
}
