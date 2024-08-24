package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.intermediate.Variable;
import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.intermediate.TemporalFactory;
import es.uned.lsi.compiler.intermediate.TemporalFactoryIF;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.intermediate.VariableIF;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class Expresion extends NonTerminal {

	public TypeIF tipo;
	
	public TemporalIF temporal;
	
	public Expresion(String lexema, TypeIF tipo, TemporalIF temporal, List<QuadrupleIF> intermediateCode) {
		
		super(lexema);
		
		this.tipo = tipo;
		
		this.temporal = temporal;
		
		this.setIntermediateCode(intermediateCode);
		
	}
	
	public TypeIF getTipo() {
	
		return this.tipo;
	
	}
	
	public TemporalIF getTemporal() {
	
		return this.temporal;
	
	}
	
	// expresion ::= NUMERO:numero
	public static Expresion produccion_NUMERO(TokenIF numero) {

		String lexema = numero.getLexema();

    	Consola.log("expresion[1]: \n" + lexema);

		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
		
		Integer valor = Integer.valueOf(numero.getLexema());

 		// Generar codigo intermedio de esta expresion
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);
 		
 		TemporalFactoryIF temporalFactory = new TemporalFactory(scope);
 		
 		TemporalIF temporal = temporalFactory.create();
 		
 		intermediateCodeBuilder.addQuadruple("MV", temporal, valor);
 		
 		List<QuadrupleIF> intermediateCode = intermediateCodeBuilder.create();	
	 	
		return new Expresion(lexema, tipoEntero, temporal, intermediateCode);
		
	}
	
	// expresion ::= IDENTIFICADOR:identificador
	public static Expresion produccion_IDENTIFICADOR(TokenIF identificador) {

		String lexema = identificador.getLexema();
	 
    	Consola.log("expresion[2]: \n" + lexema);
	 
	 	String nombre = identificador.getLexema();
	 
	 	// Comprobar que la variable esta definida
	 	if (!Contexto.scopeManager.containsSymbol(nombre)) {
	 	
	 		Contexto.semanticErrorManager.semanticFatalError("Error, variable no definida: " + nombre);
	 	
	 	}
 		
 		SymbolIF simbolo = Contexto.scopeManager.searchSymbol(nombre);

 		// Generar codigo intermedio de esta expresion
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);
 		
 		TemporalFactoryIF temporalFactory = new TemporalFactory(scope);
 		
 		TemporalIF temporal = temporalFactory.create();
 		
 		VariableIF variable = new Variable(nombre, simbolo.getScope());
 		
 		intermediateCodeBuilder.addQuadruple("MVA", temporal, variable);
 		
 		List<QuadrupleIF> intermediateCode = intermediateCodeBuilder.create();	
	 	
 		return new Expresion(lexema, simbolo.getType(), temporal, intermediateCode);
		
	}

	// expresion ::= OPEN_KEY:openKey expresion:expresion CLOSE_KEY:closeKey
	public static Expresion produccion_OPEN_KEY_expresion_CLOSE_KEY(TokenIF openKey, Expresion expresion, TokenIF closeKey) {

		String lexema = openKey.getLexema() + expresion.getLexema() + closeKey.getLexema();
	
    	Consola.log("expresion[3]: \n" + lexema);
	
		return new Expresion(lexema, expresion.getTipo());
		
	}

	// expresion ::= expresion:expresion1 PLUS:plus expresion:expresion2
	public static Expresion produccion_expresion_PLUS_expresion(Expresion expresion1, TokenIF plus, Expresion expresion2) {

		String lexema = expresion1.getLexema() + " " + plus.getLexema() + " " + expresion2.getLexema();
 	
    	Consola.log("expresion[4]: \n" + lexema);
 	
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
 		
 		// Comprobar que ambas expresiones son de tipo numerico
 		if (!tipoEntero.equals(expresion1.getTipo()) || !tipoEntero.equals(expresion2.getTipo())) {
 		
 			Contexto.semanticErrorManager.semanticFatalError("Error, expresion no es de tipo entero: " + expresion1.getTipo().getName() + " + " + expresion2.getTipo().getName());
			
 		}
 		
 		// Encapsular codigo intermedio de las subexpresiones
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);
 		 		
 		intermediateCodeBuilder.addQuadruples(expresion1.getIntermediateCode());

 		intermediateCodeBuilder.addQuadruples(expresion2.getIntermediateCode());

 		// Generar codigo intermedio de esta expresion
 		
 		TemporalFactoryIF temporalFactory = new TemporalFactory(scope);
 		
 		TemporalIF temporal = temporalFactory.create();
 		
 		TemporalIF temporalExpresion1 = expresion1.getTemporal();

 		TemporalIF temporalExpresion2 = expresion2.getTemporal();
 		
 		intermediateCodeBuilder.addQuadruple("ADD", temporal, temporalExpresion1, temporalExpresion2);
 		
 		List<QuadrupleIF> intermediateCode = intermediateCodeBuilder.create();
 		
		return new Expresion(lexema, tipoEntero, temporal, intermediateCode);
		
	}

	// expresion ::= expresion:expresion1 MULT:mult expresion:expresion2
	public static Expresion produccion_expresion_MULT_expresion(Expresion expresion1, TokenIF mult, Expresion expresion2) {

		String lexema = expresion1.getLexema() + " " + mult.getLexema() + " " + expresion2.getLexema();
 	 	
    	Consola.log("expresion[5]: \n" + lexema);
 	
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
 		
 		// Comprobar que ambas expresiones son de tipo numerico
 		if (!tipoEntero.equals(expresion1.getTipo()) || !tipoEntero.equals(expresion2.getTipo())) {
 		
 			Contexto.semanticErrorManager.semanticFatalError("Error, expresion no es de tipo entero: " + expresion1.getTipo().getName() + " * " + expresion2.getTipo().getName());
			
 		} 
 		
		return new Expresion(lexema, tipoEntero);
		
	}

	// expresion ::= expresion:expresion AUTO_INCREMENTO:autoIncremento
	public static Expresion produccion_expresion_AUTO_INCREMENTO(Expresion expresion, TokenIF autoIncremento) {

		String lexema = expresion.getLexema() + autoIncremento.getLexema();
 	
    	Consola.log("expresion[6]: \n" + lexema);
 	
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
 		
 		// Comprobar la expresion es de tipo numerico
 		if (!tipoEntero.equals(expresion.getTipo())) {
 		
			Contexto.semanticErrorManager.semanticFatalError("Error, expresion no es de tipo entero: " + expresion.getTipo().getName() + "++");
			
 		} 
 		
 		return new Expresion(lexema, tipoEntero);
		
	}

	// expresion ::= NEGACION:negacion expresion:expresion
	public static Expresion produccion_NEGACION_expresion(TokenIF negacion, Expresion expresion) {

		String lexema = negacion.getLexema() + expresion.getLexema();
 	
    	Consola.log("expresion[7]: \n" + lexema);
 	
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
 		
 		// Comprobar la expresion es de tipo numerico
 		if (!tipoEntero.equals(expresion.getTipo())) {
 		
 			Contexto.semanticErrorManager.semanticFatalError("Error, expresion no es de tipo entero: !" + expresion.getTipo().getName());
			
 		} 
 		
		return new Expresion(lexema, tipoEntero);
		
	}

	// expresion ::= expresion:expresion1 LOWER_THAN:lowerThan expresion:expresion2
	public static Expresion produccion_expresion_LOWER_THAN_expresion(Expresion expresion1, TokenIF lowerThan, Expresion expresion2) {

		String lexema = expresion1.getLexema() + " " + lowerThan.getLexema() + " " + expresion2.getLexema();
 	
    	Consola.log("expresion[8]: \n" + lexema);
 	
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
 		
 		// Comprobar que ambas expresiones son de tipo numerico
 		if (!tipoEntero.equals(expresion1.getTipo()) || !tipoEntero.equals(expresion2.getTipo())) {
 		
			Contexto.semanticErrorManager.semanticFatalError("Error, expresion no es de tipo entero: " + expresion1.getTipo().getName() + " < " + expresion2.getTipo().getName());
			
 		}
 		
		return new Expresion(lexema, tipoEntero);
		
	}

	// expresion ::= expresion:expresion1 EQUAL:equal expresion:expresion2
	public static Expresion produccion_expresion_EQUAL_expresion(Expresion expresion1, TokenIF equal, Expresion expresion2) {

 		String lexema = expresion1.getLexema() + " " + equal.getLexema() + " " + expresion2.getLexema();
 	
    	Consola.log("expresion[9]: \n" + lexema);
 	
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
 		
 		// Comprobar que ambas expresiones son de tipo numerico
 		if (!tipoEntero.equals(expresion1.getTipo()) || !tipoEntero.equals(expresion2.getTipo())) {
 		
 			Contexto.semanticErrorManager.semanticFatalError("Error, expresion no es de tipo entero: " + expresion1.getTipo().getName() + " = " + expresion2.getTipo().getName());
			
 		} 
 		
		return new Expresion(lexema, tipoEntero);
		
	}

	// expresion ::= expresion:expresion1 AND_LOGICA:andLogica expresion:expresion2
	public static Expresion produccion_expresion_AND_LOGICA_expresion(Expresion expresion1, TokenIF andLogica, Expresion expresion2) {

 		String lexema = expresion1.getLexema() + " " + andLogica.getLexema() + " " + expresion2.getLexema();
 	
    	Consola.log("expresion[10]: \n" + lexema);
 	
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
 		
 		// Comprobar que ambas expresiones son de tipo numerico
 		if (!tipoEntero.equals(expresion1.getTipo()) || !tipoEntero.equals(expresion2.getTipo())) {
 		
 			Contexto.semanticErrorManager.semanticFatalError("Error, expresion no es de tipo entero: " + expresion1.getTipo().getName() + " && " + expresion2.getTipo().getName());
			
 		} 		
			
		return new Expresion(lexema, tipoEntero);
		
	}

	// expresion ::= accesoVector:accesoVector
	public static Expresion produccion_accesoVector(AccesoVector accesoVector) {
 	
 		String lexema = accesoVector.getLexema();
 		
    	Consola.log("expresion[11]: \n" + lexema);
 	
 		return new Expresion(lexema, accesoVector.getTipo());
		
	}

	// expresion ::= llamadaFuncion:llamadaFuncion
	public static Expresion produccion_llamadaFuncion(LlamadaFuncion llamadaFuncion) {

 		String lexema = llamadaFuncion.getLexema();
 	
    	Consola.log("expresion[12]: \n" + lexema);
 	
 		return new Expresion(lexema, llamadaFuncion.getTipoRetorno());
		
	}
	
}
