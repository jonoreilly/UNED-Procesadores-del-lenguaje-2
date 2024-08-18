package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import compiler.utils.UtilsTiposDevuelve;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class SentenciaSi extends NonTerminal {

	private List<TypeIF> tiposDevuelve = new ArrayList<>();
	
	public SentenciaSi(String lexema, List<TypeIF> tiposDevuelveSi) {

		this(lexema, tiposDevuelveSi, UtilsTiposDevuelve.ramaSinDevuelve());
			
	}

	public SentenciaSi(String lexema, List<TypeIF> tiposDevuelveSi, List<TypeIF> tiposDevuelveSino) {
	
		super(lexema);

		this.tiposDevuelve.addAll(
				UtilsTiposDevuelve.unirRamas(tiposDevuelveSi, tiposDevuelveSino)
			);
	
	}
	
	public List<TypeIF> getTiposDevuelve() {
	
		return new ArrayList<>(this.tiposDevuelve);
	
	}
	
	// sentenciaSi ::= SI:si OPEN_KEY:openKey expresion:expresion CLOSE_KEY:closeKey sentencia:sentenciaSi SINO:sino sentencia:sentenciaSino
	public static SentenciaSi produccion_SI_OPEN_KEY_expresion_CLOSE_KEY_sentencia_SINO_sentencia(TokenIF si, TokenIF openKey, Expresion expresion, TokenIF closeKey, Sentencia sentenciaSi, TokenIF sino, Sentencia sentenciaSino) {

		String lexema = si.getLexema() + " " + openKey.getLexema() + expresion.getLexema() + closeKey.getLexema() + " " + sentenciaSi.getLexema() + "\n" + sino.getLexema() + " " + sentenciaSino.getLexema();
		
		Consola.log("sentenciaSi[1]: \n" + lexema); 
				
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
		
		TypeIF tipoExpresion = expresion.getTipo();
		
		// Comprobar que expresion es de tipo numerico
		if (!tipoEntero.equals(tipoExpresion)) {
			
			Contexto.semanticErrorManager.semanticFatalError("Error, el tipo de la condicion debe ser numerico: " + tipoExpresion.getName());
			
		}
		
		List<TypeIF> tiposDevuelveSi = sentenciaSi.getTiposDevuelve();
		
		List<TypeIF> tiposDevuelveSino = sentenciaSino.getTiposDevuelve();
		
		return new SentenciaSi(lexema, tiposDevuelveSi, tiposDevuelveSino);
			
	}
	
	// sentenciaSi ::= SI:si OPEN_KEY:openKey expresion:expresion CLOSE_KEY:closeKey sentencia:sentenciaSi
	public static SentenciaSi produccion_SI_OPEN_KEY_expresion_CLOSE_KEY_sentencia(TokenIF si, TokenIF openKey, Expresion expresion, TokenIF closeKey, Sentencia sentenciaSi) {

		String lexema = si.getLexema() + " " + openKey.getLexema() + expresion.getLexema() + closeKey.getLexema() + " " + sentenciaSi.getLexema();
			 
		Consola.log("sentenciaSi[2]: \n" + lexema); 
	 			 
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
		
		TypeIF tipoExpresion = expresion.getTipo();
				
		// Comprobar que expresion es de tipo numerico
		if (!tipoEntero.equals(tipoExpresion)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, el tipo de la condicion debe ser numerico: " + tipoExpresion.getName());
			
		}
		
		List<TypeIF> tiposDevuelveSi = sentenciaSi.getTiposDevuelve();
	
		return new SentenciaSi(lexema, tiposDevuelveSi);
			
	}
	
}
