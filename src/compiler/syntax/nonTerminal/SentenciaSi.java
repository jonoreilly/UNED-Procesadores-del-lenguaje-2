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

public class SentenciaSi extends NonTerminal {

	private List<TypeIF> tiposDevuelve = new ArrayList<>();
	
	public SentenciaSi(String lexema, List<TypeIF> tiposDevuelve, List<QuadrupleIF> intermediateCode) {
	
		super(lexema, intermediateCode);

		this.tiposDevuelve.addAll(tiposDevuelve);
	
	}
	
	public List<TypeIF> getTiposDevuelve() {
	
		return new ArrayList<>(this.tiposDevuelve);
	
	}
	
	// sentenciaSi ::= SI:si OPEN_KEY:openKey expresion:expresion CLOSE_KEY:closeKey sentencia:sentenciaSi SINO:sino sentencia:sentenciaSino
	public static SentenciaSi produccion_SI_OPEN_KEY_expresion_CLOSE_KEY_sentencia_SINO_sentencia(TokenIF si, TokenIF openKey, Expresion expresion, TokenIF closeKey, Sentencia sentenciaSi, TokenIF sino, Sentencia sentenciaSino) {

		String lexema = si.getLexema() + " " + openKey.getLexema() + expresion.getLexema() + closeKey.getLexema() + " " + sentenciaSi.getLexema() + "\n" + sino.getLexema() + " " + sentenciaSino.getLexema();
		
		Consola.log("sentenciaSi[1]: \n" + lexema); 
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);
 		
 		LabelFactoryIF labelFactory = new LabelFactory();
				
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
		
		TypeIF tipoExpresion = expresion.getTipo();
		
		// Comprobar que expresion es de tipo numerico
		if (!tipoEntero.equals(tipoExpresion)) {
			
			Contexto.semanticErrorManager.semanticFatalError("Error, el tipo de la condicion debe ser numerico: " + tipoExpresion.getName());
			
		}
		
		List<TypeIF> tiposDevuelveSi = sentenciaSi.getTiposDevuelve();
		
		List<TypeIF> tiposDevuelveSino = sentenciaSino.getTiposDevuelve();

		List<TypeIF> tiposDevuelve = UtilsTiposDevuelve.unirRamas(tiposDevuelveSi, tiposDevuelveSino);

 		// Encapsular codigo intermedio de las subexpresiones
 		 		
 		intermediateCodeBuilder.addQuadruples(expresion.getIntermediateCode());
 		
		// Generar codigo intermedio

 		LabelIF labelBloqueSino = labelFactory.create();

 		LabelIF labelFinSentencia = labelFactory.create();
		
		// if (!expresion) { salto a BLOQUE_SINO } ejecutar bloque si; salto a FIN_SENTENCIA; BLOQUE_SINO; ejecutar bloque sino; FIN_SENTENCIA

 		// if (!expresion) { salto a BLOQUE_SINO }
		intermediateCodeBuilder.addQuadruple("BRF", expresion.getTemporal(), labelBloqueSino);
		
		// ejecutar bloque si
		intermediateCodeBuilder.addQuadruples(sentenciaSi.getIntermediateCode());

 		// salto a FIN_SENTENCIA
		intermediateCodeBuilder.addQuadruple("BR", labelFinSentencia);

		// BLOQUE_SINO
		intermediateCodeBuilder.addQuadruple("INL", labelBloqueSino); 
		
		// ejecutar bloque sino
		intermediateCodeBuilder.addQuadruples(sentenciaSino.getIntermediateCode());

		// FIN_SENTENCIA
		intermediateCodeBuilder.addQuadruple("INL", labelFinSentencia); 
		
		return new SentenciaSi(lexema, tiposDevuelve, intermediateCodeBuilder.create());
			
	}
	
	// sentenciaSi ::= SI:si OPEN_KEY:openKey expresion:expresion CLOSE_KEY:closeKey sentencia:sentenciaSi
	public static SentenciaSi produccion_SI_OPEN_KEY_expresion_CLOSE_KEY_sentencia(TokenIF si, TokenIF openKey, Expresion expresion, TokenIF closeKey, Sentencia sentenciaSi) {

		String lexema = si.getLexema() + " " + openKey.getLexema() + expresion.getLexema() + closeKey.getLexema() + " " + sentenciaSi.getLexema();
			 
		Consola.log("sentenciaSi[2]: \n" + lexema); 
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);
 		
 		LabelFactoryIF labelFactory = new LabelFactory();
	 			 
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
		
		TypeIF tipoExpresion = expresion.getTipo();
				
		// Comprobar que expresion es de tipo numerico
		if (!tipoEntero.equals(tipoExpresion)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, el tipo de la condicion debe ser numerico: " + tipoExpresion.getName());
			
		}

		List<TypeIF> tiposDevuelveSi = sentenciaSi.getTiposDevuelve();
		
		List<TypeIF> tiposDevuelveSino = UtilsTiposDevuelve.ramaSinDevuelve();

		List<TypeIF> tiposDevuelve = UtilsTiposDevuelve.unirRamas(tiposDevuelveSi, tiposDevuelveSino);

 		// Encapsular codigo intermedio de las subexpresiones
 		 		
 		intermediateCodeBuilder.addQuadruples(expresion.getIntermediateCode());
		
		// Generar codigo intermedio

 		LabelIF labelFinSentencia = labelFactory.create();
		
		// if (!expresion) { salto a FIN_SENTENCIA } ejecutar bloque si; FIN_SENTENCIA

 		// if (!expresion) { salto a FIN_SENTENCIA }
		intermediateCodeBuilder.addQuadruple("BRF", expresion.getTemporal(), labelFinSentencia);
		
		// ejecutar bloque si
		intermediateCodeBuilder.addQuadruples(sentenciaSi.getIntermediateCode());

		// FIN_SENTENCIA
		intermediateCodeBuilder.addQuadruple("INL", labelFinSentencia); 
	
		return new SentenciaSi(lexema, tiposDevuelve, intermediateCodeBuilder.create());
			
	}
	
}
