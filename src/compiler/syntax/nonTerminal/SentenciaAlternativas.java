package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import compiler.utils.UtilsTiposDevuelve;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class SentenciaAlternativas extends NonTerminal {

	private List<TypeIF> tiposDevuelve = new ArrayList<>();
		
	public SentenciaAlternativas(String lexema, List<TypeIF> tiposDevuelveCasos) {

		this(lexema, tiposDevuelveCasos, UtilsTiposDevuelve.ramaSinDevuelve());
		
	}

	public SentenciaAlternativas(String lexema, List<TypeIF> tiposDevuelveCasos, List<TypeIF> tiposDevuelvePorDefecto) {
		super(lexema);
		
		this.tiposDevuelve.addAll(
				UtilsTiposDevuelve.unirRamas(tiposDevuelveCasos, tiposDevuelvePorDefecto)
			);

	}

	public List<TypeIF> getTiposDevuelve() {
		return new ArrayList<>(this.tiposDevuelve);
	}
	
	// sentenciaAlternativas ::= ALTENATIVAS:alternativas OPEN_KEY:openKey expresion:expresion CLOSE_KEY:closeKey OPEN_PARENTHESIS:openParenthesis casosAlternativa:casosAlternativa porDefecto:porDefecto CLOSE_PARENTHESIS:closeParenthesis
	public static SentenciaAlternativas produccion(TokenIF alternativas, TokenIF openKey, Expresion expresion, TokenIF closeKey, TokenIF openParenthesis, CasosAlternativa casosAlternativa, PorDefecto porDefecto, TokenIF closeParenthesis) {

		String lexema = alternativas.getLexema() + " " + openKey.getLexema() + expresion.getLexema() + closeKey.getLexema() + " " + openParenthesis.getLexema() + "\n" + casosAlternativa.getLexema() + "\n" + porDefecto.getLexema() + "\n" + closeParenthesis.getLexema();
			 
		Consola.log("sentenciaAlternativas[1]: \n" + lexema); 
		
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
		
		TypeIF tipoExpresion = expresion.getTipo();
		
		// Comprobar que la condicion es de tipo numerico	
		if (! tipoEntero.equals(tipoExpresion)) {
			
			Contexto.semanticErrorManager.semanticFatalError("Error, el tipo de la alternativa debe ser numerico: " + tipoExpresion.getName());
			
		}
		
		List<TypeIF> tiposDevuelveCasosAlternativa = casosAlternativa.getTiposDevuelve();
		
		List<TypeIF> tiposDevuelvePorDefecto = porDefecto.getTiposDevuelve();
		
		return new SentenciaAlternativas(lexema, tiposDevuelveCasosAlternativa, tiposDevuelvePorDefecto);
	
	}
	
}
