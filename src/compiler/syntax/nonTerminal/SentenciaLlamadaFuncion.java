package compiler.syntax.nonTerminal;

import compiler.utils.Consola;
import es.uned.lsi.compiler.lexical.TokenIF;

public class SentenciaLlamadaFuncion extends NonTerminal {
	
	public SentenciaLlamadaFuncion(String lexema) {
		
		super(lexema);
		
	}
	
	// sentenciaLlamadaFuncion ::= llamadaFuncion:llamadaFuncion SEMI_COLON:semiColon
	public static SentenciaLlamadaFuncion produccion(LlamadaFuncion llamadaFuncion, TokenIF semiColon) {

		String lexema = llamadaFuncion.getLexema() + semiColon.getLexema();

		Consola.log("sentenciaLlamadaFuncion[1]: \n" + lexema);
		
		return new SentenciaLlamadaFuncion(lexema);
		
	}

}
