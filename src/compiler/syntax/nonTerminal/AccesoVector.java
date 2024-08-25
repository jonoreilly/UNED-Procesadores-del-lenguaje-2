package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.intermediate.Value;
import compiler.intermediate.Variable;
import compiler.semantic.type.TypeArray;
import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.lsi.compiler.intermediate.LabelFactory;
import es.uned.lsi.compiler.intermediate.LabelFactoryIF;
import es.uned.lsi.compiler.intermediate.LabelIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.intermediate.TemporalFactory;
import es.uned.lsi.compiler.intermediate.TemporalFactoryIF;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.intermediate.ValueIF;
import es.uned.lsi.compiler.intermediate.VariableIF;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class AccesoVector extends NonTerminal {

	private TypeIF tipo;
	
	private TemporalIF punteroTemporal;
	
	public AccesoVector(String lexema, TypeIF tipo, TemporalIF punteroTemporal, List<QuadrupleIF> intermediateCode) {
		
		super(lexema);
		
		this.tipo = tipo;
		
		this.punteroTemporal = punteroTemporal;
		
		this.setIntermediateCode(intermediateCode);
	
	}
	
	public TypeIF getTipo() {
	
		return this.tipo;
	
	}
	
	public TemporalIF getPunteroTemporal() {
		
		return this.punteroTemporal;
		
	}
		
	// accesoVector ::= IDENTIFICADOR:identificador OPEN_BRACKET:openBracket expresion:expresion CLOSE_BRACKET:closeBracket
	public static AccesoVector produccion(TokenIF identificador, TokenIF openBracket, Expresion expresion, TokenIF closeBracket) {

		String lexema = identificador.getLexema() + openBracket.getLexema() + expresion.getLexema() + closeBracket.getLexema();

    	Consola.log("accesoVector[1]: \n" + lexema);
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);

 		TemporalFactoryIF temporalFactory = new TemporalFactory(scope);
 		
 		LabelFactoryIF labelFactory = new LabelFactory();
 	
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
 		
 		String nombreVector = identificador.getLexema();
 		
 		// Comprobabr que la expresion de acceso es de tipo numerica
 		if (!tipoEntero.equals(expresion.getTipo())) {
 		
 			Contexto.semanticErrorManager.semanticFatalError("Error, expresion de acceso no es de tipo entero: " + expresion.getTipo().getName());
		}
			
		// Comprobabr que el simbolo del vector existe
		if (!Contexto.scopeManager.containsSymbol(nombreVector)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, simbolo no definido: " + nombreVector);
		}
		
		SymbolIF simboloVector = Contexto.scopeManager.searchSymbol(nombreVector);
		
		TypeIF tipoVector = simboloVector.getType();
	
		// Comprobar que el symbolo del vector es de tipo vector
		if (!(tipoVector instanceof TypeArray)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, expresion de acceso solo permitida sobre vectores");
		
		}
		
		TypeIF tipoElemento = ((TypeArray)tipoVector).getTipoElemento();

		int tamanioElemento = tipoElemento.getSize();
		
		int longitudVector = ((TypeArray)tipoVector).getLongitud();
		
 		// Encapsular codigo intermedio de las subexpresiones
 		 		
 		intermediateCodeBuilder.addQuadruples(expresion.getIntermediateCode());

 		// Generar codigo intermedio de esta expresion

 		VariableIF variable = new Variable(nombreVector, scope);
 		
 		ValueIF valorTamanioElemento = new Value(tamanioElemento);

 		ValueIF valorLongitudVector = new Value(longitudVector);
 		
 		LabelIF labelDespuesDeComprobacionLongitud = labelFactory.create();

 		LabelIF labelErrorComprobacionLongitud = labelFactory.create();
 		
 		// n
 		TemporalIF temporalExpresion = expresion.getTemporal();

 		// a
 		TemporalIF punteroTemporalVector = temporalFactory.create();

 		// a.length
 		TemporalIF temporalLongitudVector = temporalFactory.create();

 		// a[].size
 		TemporalIF temporalTamanioElemento = temporalFactory.create();

 		// (n * a[].size)
 		TemporalIF temporalOffset = temporalFactory.create();

 		// a.length > n
 		TemporalIF temporalCondicionLongitudMayorQueExpresion = temporalFactory.create();

 		// a[n]
 		TemporalIF punteroTemporalElemento = temporalFactory.create();

 		// Generar cuadruplas

 		// a
 		intermediateCodeBuilder.addQuadruple("MVP", punteroTemporalVector, variable);
 		
 		// a.length
 		intermediateCodeBuilder.addQuadruple("MV", temporalLongitudVector, valorLongitudVector);

 		// a[].size
 		intermediateCodeBuilder.addQuadruple("MV", temporalTamanioElemento, valorTamanioElemento);
 		
		// Comprobar que no se pasa de la longitud del vector 		
 		// if (a.length > n) goto DESPUES; else print "Error"; DESPUES
 		
 		// a.length > n
 		intermediateCodeBuilder.addQuadruple("GR", temporalCondicionLongitudMayorQueExpresion, temporalLongitudVector, temporalExpresion);
 		
 		// if (a.length > n) goto DESPUES
 		intermediateCodeBuilder.addQuadruple("BRT", temporalCondicionLongitudMayorQueExpresion, labelDespuesDeComprobacionLongitud); 
 		
 		// print "Error"
 		intermediateCodeBuilder.addQuadruple("PRINT", labelErrorComprobacionLongitud);
 		
 		Contexto.listaCadenas.addCadena(labelErrorComprobacionLongitud, "ERROR: indice fuera del limite del vector");
 		
 		// DESPUES
 		intermediateCodeBuilder.addQuadruple("INL", labelDespuesDeComprobacionLongitud);
 		 				
 		// Generar un temporal que apunta a la posicion deseada
 		// a[n] -> a + (n * a[].size)
 		
 		// (n * a[].size)
 		intermediateCodeBuilder.addQuadruple("MUL", temporalOffset, temporalExpresion, temporalTamanioElemento);

 		// a + (n * a[].size)
 		intermediateCodeBuilder.addQuadruple("ADD", punteroTemporalElemento, punteroTemporalVector, temporalOffset);
 		
		return new AccesoVector(lexema, tipoElemento, punteroTemporalElemento, intermediateCodeBuilder.create());
		
	}
	
}


















