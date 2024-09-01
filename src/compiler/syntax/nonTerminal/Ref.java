package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.intermediate.Variable;
import compiler.semantic.symbol.SymbolParameter;
import compiler.semantic.symbol.SymbolVariable;
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

public class Ref extends NonTerminal {

	private TypeIF tipo;
	
	private TemporalIF punteroTemporal;
	
	public Ref(String lexema, TypeIF tipo, TemporalIF punteroTemporal, List<QuadrupleIF> intermediateCode) {

		super(lexema, intermediateCode);
		
		this.tipo = tipo;
		
		this.punteroTemporal = punteroTemporal;
		
	}
	
	public TypeIF getTipo() {
		
		return this.tipo;
		
	}
	
	public TemporalIF getPunteroTemporal() {
		
		return this.punteroTemporal;
		
	}
	
	// ref ::= IDENTIFICADOR:identificador
	public static Ref produccion_IDENTIFICADOR(TokenIF identificador) {

		String lexema = identificador.getLexema();
	 
    	Consola.log("ref[1]: \n" + lexema);
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);
 		
 		TemporalFactoryIF temporalFactory = new TemporalFactory(scope);
	 
	 	String nombre = identificador.getLexema();
	 
	 	// Comprobar que la variable esta definida
	 	if (!Contexto.scopeManager.containsSymbol(nombre)) {
	 	
	 		Contexto.semanticErrorManager.semanticFatalError("Error, variable no definida: " + nombre);
	 	
	 	}
 		
 		SymbolIF simbolo = Contexto.scopeManager.searchSymbol(nombre);

		// Comprobar que ref es una variable o parametro, y no una constante o funcion
		if (!(simbolo instanceof SymbolVariable || simbolo instanceof SymbolParameter)) {
				
			Contexto.semanticErrorManager.semanticFatalError("Error, la parte izquierda de una asignacion debe ser una variable: " + nombre);
		
		}

 		// Generar codigo intermedio de esta expresion
 		
 		TemporalIF punteroTemporal = temporalFactory.create();
 		
 		VariableIF variable = new Variable(nombre, simbolo.getScope());
 		
 		intermediateCodeBuilder.addQuadruple("POINT", punteroTemporal, variable);
	 	
 		return new Ref(lexema, simbolo.getType(), punteroTemporal, intermediateCodeBuilder.create());
 		
	}

	// ref ::= accesoVector:accesoVector
	public static Ref produccion_accesoVector(AccesoVector accesoVector) {
		
		String lexema = accesoVector.getLexema();
	
		Consola.log("ref[2]: \n" + lexema);
		
		return new Ref(lexema, accesoVector.getTipo(), accesoVector.getPunteroTemporal(), accesoVector.getIntermediateCode());
		
	}
	
}
