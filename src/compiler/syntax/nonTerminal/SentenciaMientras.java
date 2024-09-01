package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import compiler.utils.UtilsTiposDevuelve;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.lsi.compiler.intermediate.LabelFactory;
import es.uned.lsi.compiler.intermediate.LabelFactoryIF;
import es.uned.lsi.compiler.intermediate.LabelIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class SentenciaMientras extends NonTerminal {

	private List<TypeIF> tiposDevuelve = new ArrayList<>();
			
	public SentenciaMientras(String lexema, List<TypeIF> tiposDevuelve,  List<QuadrupleIF> intermediateCode) {
	
		super(lexema, intermediateCode);
		
		this.tiposDevuelve.addAll(
				UtilsTiposDevuelve.unirRamas(
						tiposDevuelve,
						UtilsTiposDevuelve.ramaSinDevuelve()
					)
			);
	
	}

	public List<TypeIF> getTiposDevuelve() {
	
		return new ArrayList<>(this.tiposDevuelve);
	
	}

	// sentenciaMientras ::= MIENTRAS:mientras OPEN_KEY:openKey expresion:expresion CLOSE_KEY:closeKey
	public static void preProduccion(TokenIF mientras, TokenIF openKey, Expresion expresion, TokenIF closeKey) {

		String lexema = mientras.getLexema() + openKey.getLexema() + expresion.getLexema() + closeKey.getLexema();

		Consola.log("sentenciaMientras[1]: \n" + lexema);

		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
		
		TypeIF tipoExpresion = expresion.getTipo();

		// Comprobar que el tipo de la expresion es numerico
		if (!tipoEntero.equals(tipoExpresion)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, el tipo de la expresion mientras debe ser numerico: " + tipoExpresion.getName());

		}
		
	}
	
	// sentenciaMientras ::= MIENTRAS:mientras OPEN_KEY:openKey expresion:expresion CLOSE_KEY:closeKey sentencia:sentencia
	public static SentenciaMientras produccion(TokenIF mientras, TokenIF openKey, Expresion expresion, TokenIF closeKey, Sentencia sentencia) {

		String lexema = mientras.getLexema() + openKey.getLexema() + expresion.getLexema() + closeKey.getLexema() + " " + sentencia.getLexema();
	
		Consola.log("sentenciaMientras[2]: \n" + lexema);
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);
 		
 		// Generar codigo intermedio
 		
 		LabelFactoryIF labelFactory = new LabelFactory();
		
		// PRINCIPIO_SENTENCIA; if (!expresion) { salta a FIN_SENTENCIA } ejecutar bloque; salta a PRINCIPIO_SENTENCIA; FIN_SENTENCIA
		
		LabelIF labelPrincipioSentencia = labelFactory.create();

		LabelIF labelFinSentencia = labelFactory.create();
		
		// PRINCIPIO_SENTENCIA
		intermediateCodeBuilder.addQuadruple("INL", labelPrincipioSentencia);
		
		// expresion
 		intermediateCodeBuilder.addQuadruples(expresion.getIntermediateCode());
		
		//if (!expresion) { salta a FIN_SENTENCIA }
		intermediateCodeBuilder.addQuadruple("BRF", expresion.getTemporal(), labelFinSentencia);
		
		// ejecutar bloque
		intermediateCodeBuilder.addQuadruples(sentencia.getIntermediateCode());
		
		// salta a PRINCIPIO_SENTENCIA
		intermediateCodeBuilder.addQuadruple("BR", labelPrincipioSentencia);
		
		// FIN_SENTENCIA
		intermediateCodeBuilder.addQuadruple("INL", labelFinSentencia);
				
		return new SentenciaMientras(lexema, sentencia.getTiposDevuelve(), intermediateCodeBuilder.create());
		
	}
	
}
