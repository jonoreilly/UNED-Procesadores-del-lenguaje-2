package compiler.syntax.nonTerminal;

import compiler.utils.Consola;
import es.uned.lsi.compiler.lexical.TokenIF;

public class SentenciaSalida extends NonTerminal {

	public SentenciaSalida(String lexema) {
		
		super(lexema);
		
	}
	
	// sentenciaSalida ::= ESCRIBE:escribe OPEN_KEY:openKey opcionesEscribe:opcionesEscribe CLOSE_KEY:closeKey SEMI_COLON:semiColon
	public static SentenciaSalida produccion_ESCRIBE_OPEN_KEY_opcionesEscribe_CLOSE_KEY_SEMI_COLON(TokenIF escribe, TokenIF openKey, OpcionesEscribe opcionesEscribe, TokenIF closeKey, TokenIF semiColon) {

		String lexema = escribe.getLexema() + openKey.getLexema() + opcionesEscribe.getLexema() + closeKey.getLexema() + semiColon.getLexema();

		Consola.log("sentenciaSalida[1]: \n" + lexema);
		
		return new SentenciaSalida(lexema);
		
	}
	
	// sentenciaSalida ::= ESCRIBE_ENT:escribeEnt OPEN_KEY:openKey opcionesEscribeEnt:opcionesEscribeEnt CLOSE_KEY:closeKey SEMI_COLON:semiColon
	public static SentenciaSalida produccion_ESCRIBE_ENT_OPEN_KEY_opcionesEscribeEnt_CLOSE_KEY_SEMI_COLON(TokenIF escribeEnt, TokenIF openKey, OpcionesEscribeEnt opcionesEscribeEnt, TokenIF closeKey, TokenIF semiColon) {

		String lexema = escribeEnt.getLexema() + openKey.getLexema() + opcionesEscribeEnt.getLexema() + closeKey.getLexema() + semiColon.getLexema();

		Consola.log("sentenciaSalida[2]: \n" + lexema);
		
		return new SentenciaSalida(lexema);
		
	}

}
