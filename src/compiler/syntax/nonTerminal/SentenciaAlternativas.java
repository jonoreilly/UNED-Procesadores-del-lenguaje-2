package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.intermediate.Value;
import compiler.utils.CasoAltDatos;
import compiler.utils.Consola;
import compiler.utils.Contexto;
import compiler.utils.UtilsTiposDevuelve;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.lsi.compiler.intermediate.LabelFactory;
import es.uned.lsi.compiler.intermediate.LabelFactoryIF;
import es.uned.lsi.compiler.intermediate.LabelIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.intermediate.TemporalFactory;
import es.uned.lsi.compiler.intermediate.TemporalFactoryIF;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class SentenciaAlternativas extends NonTerminal {

	private List<TypeIF> tiposDevuelve = new ArrayList<>();
		
	public SentenciaAlternativas(String lexema, List<TypeIF> tiposDevuelve, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
		this.tiposDevuelve.addAll(tiposDevuelve);

	}

	public List<TypeIF> getTiposDevuelve() {
		
		return new ArrayList<>(this.tiposDevuelve);
		
	}
	
	// sentenciaAlternativas ::= ALTENATIVAS:alternativas OPEN_KEY:openKey expresion:expresion CLOSE_KEY:closeKey OPEN_PARENTHESIS:openParenthesis casosAlternativa:casosAlternativa porDefecto:porDefecto CLOSE_PARENTHESIS:closeParenthesis
	public static SentenciaAlternativas produccion(TokenIF alternativas, TokenIF openKey, Expresion expresion, TokenIF closeKey, TokenIF openParenthesis, CasosAlternativa casosAlternativa, PorDefecto porDefecto, TokenIF closeParenthesis) {

		String lexema = alternativas.getLexema() + " " + openKey.getLexema() + expresion.getLexema() + closeKey.getLexema() + " " + openParenthesis.getLexema() + "\n" + casosAlternativa.getLexema() + "\n" + porDefecto.getLexema() + "\n" + closeParenthesis.getLexema();
			 
		Consola.log("sentenciaAlternativas[1]: \n" + lexema); 
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);
 		
 		TemporalFactoryIF temporalFactory = new TemporalFactory(scope);
 		
 		LabelFactoryIF labelFactory = new LabelFactory();
		
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
		
		TypeIF tipoExpresion = expresion.getTipo();
		
		// Comprobar que la condicion es de tipo numerico	
		if (! tipoEntero.equals(tipoExpresion)) {
			
			Contexto.semanticErrorManager.semanticFatalError("Error, el tipo de la alternativa debe ser numerico: " + tipoExpresion.getName());
			
		}

		List<TypeIF> tiposDevuelveCasosAlternativa = casosAlternativa.getTiposDevuelve();
		
		List<TypeIF> tiposDevuelvePorDefecto = porDefecto.getTiposDevuelve();
		
		List<TypeIF> tiposDevuelve = UtilsTiposDevuelve.unirRamas(tiposDevuelveCasosAlternativa, tiposDevuelvePorDefecto);
 		
 		// Encapsular codigo intermedio de las subexpresiones
 		 		
 		intermediateCodeBuilder.addQuadruples(expresion.getIntermediateCode());

 		// Generar codigo intermedio de esta expresion

 		LabelIF labelFinSentencia = labelFactory.create();
 		
 		TemporalIF temporalExpresion = expresion.getTemporal();

 		TemporalIF temporalCondicionIgual = temporalFactory.create();
 		
 		for (CasoAltDatos casoAltDatos : casosAlternativa.getCasosAltDatos()) {

 			LabelIF labelFinCondicion = labelFactory.create();

 			Integer valorCondicion = casoAltDatos.getValorCondicion();
 			
 			// if (expresion != valorCondicion) { salto a FIN_CONDICION } ejecutar bloque caso; salto a FIN_SENTENCIA; FIN_CONDICION
 			 			
 			// expresion == valorCondicion
 			intermediateCodeBuilder.addQuadruple("EQ", temporalCondicionIgual, temporalExpresion, new Value(valorCondicion));
 			
 			// if (expresion != valorCondicion) salto a FIN_CONDICION
 			intermediateCodeBuilder.addQuadruple("BRF", temporalCondicionIgual, labelFinCondicion);
 			
 			// ejecutar bloque caso 			
 			intermediateCodeBuilder.addQuadruples(casoAltDatos.getIntermediateCode());
 			
 			// salto a FIN_SENTENCIA
 			intermediateCodeBuilder.addQuadruple("BR", labelFinSentencia);
 			
 			// FIN_CONDICION
 			intermediateCodeBuilder.addQuadruple("INL", labelFinCondicion); 			
 			
 		}

		// ejecutar bloque por defecto (vacio si no definido)
		intermediateCodeBuilder.addQuadruples(porDefecto.getIntermediateCode());

		// FIN_SENTENCIA
		intermediateCodeBuilder.addQuadruple("INL", labelFinSentencia); 
 		
		return new SentenciaAlternativas(lexema, tiposDevuelve, intermediateCodeBuilder.create());
	
	}
	
}
