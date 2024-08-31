package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.utils.Consola;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.lexical.TokenIF;

public class SentenciaLlamadaFuncion extends NonTerminal {
	
	public SentenciaLlamadaFuncion(String lexema, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
	}
	
	// sentenciaLlamadaFuncion ::= llamadaFuncion:llamadaFuncion SEMI_COLON:semiColon
	public static SentenciaLlamadaFuncion produccion(LlamadaFuncion llamadaFuncion, TokenIF semiColon) {

		String lexema = llamadaFuncion.getLexema() + semiColon.getLexema();

		Consola.log("sentenciaLlamadaFuncion[1]: \n" + lexema);
		
		return new SentenciaLlamadaFuncion(lexema, llamadaFuncion.getIntermediateCode());
		
	}

}
