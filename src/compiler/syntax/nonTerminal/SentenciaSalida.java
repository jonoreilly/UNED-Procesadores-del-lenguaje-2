package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.lsi.compiler.intermediate.LabelIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.ScopeIF;

public class SentenciaSalida extends NonTerminal {

	public SentenciaSalida(String lexema, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
	}
	
	// sentenciaSalida ::= ESCRIBE:escribe OPEN_KEY:openKey opcionesEscribe:opcionesEscribe CLOSE_KEY:closeKey SEMI_COLON:semiColon
	public static SentenciaSalida produccion_ESCRIBE_OPEN_KEY_opcionesEscribe_CLOSE_KEY_SEMI_COLON(TokenIF escribe, TokenIF openKey, OpcionesEscribe opcionesEscribe, TokenIF closeKey, TokenIF semiColon) {

		String lexema = escribe.getLexema() + openKey.getLexema() + opcionesEscribe.getLexema() + closeKey.getLexema() + semiColon.getLexema();

		Consola.log("sentenciaSalida[1]: \n" + lexema);
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);

 		// Encapsular codigo intermedio de las subexpresiones
 		
 		intermediateCodeBuilder.addQuadruples(opcionesEscribe.getIntermediateCode());
 		
 		// Generar codigo intermedio
		
 		LabelIF label = opcionesEscribe.getLabel();
 		
 		if (label == null) {

 			intermediateCodeBuilder.addQuadruple("PRINT_LINE");
 				
 		} else {

 			intermediateCodeBuilder.addQuadruple("PRINT", label);
 			
 		}
		
		return new SentenciaSalida(lexema, intermediateCodeBuilder.create());
		
	}
	
	// sentenciaSalida ::= ESCRIBE_ENT:escribeEnt OPEN_KEY:openKey opcionesEscribeEnt:opcionesEscribeEnt CLOSE_KEY:closeKey SEMI_COLON:semiColon
	public static SentenciaSalida produccion_ESCRIBE_ENT_OPEN_KEY_opcionesEscribeEnt_CLOSE_KEY_SEMI_COLON(TokenIF escribeEnt, TokenIF openKey, OpcionesEscribeEnt opcionesEscribeEnt, TokenIF closeKey, TokenIF semiColon) {

		String lexema = escribeEnt.getLexema() + openKey.getLexema() + opcionesEscribeEnt.getLexema() + closeKey.getLexema() + semiColon.getLexema();

		Consola.log("sentenciaSalida[2]: \n" + lexema);
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);
 		
 		// Encapsular codigo intermedio de las subexpresiones
 		
 		intermediateCodeBuilder.addQuadruples(opcionesEscribeEnt.getIntermediateCode());
 		
 		// Generar codigo intermedio
		
 		TemporalIF temporal = opcionesEscribeEnt.getTemporal();
 		
 		if (temporal == null) {

 			intermediateCodeBuilder.addQuadruple("PRINT_LINE");
 				
 		} else {

 			intermediateCodeBuilder.addQuadruple("PRINT_INT", temporal);
 			
 		}
 		
		return new SentenciaSalida(lexema, intermediateCodeBuilder.create());
		
	}

}
