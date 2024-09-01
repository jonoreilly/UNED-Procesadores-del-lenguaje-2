package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.intermediate.Variable;
import compiler.semantic.symbol.SymbolConstant;
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

public class Referencia extends NonTerminal {

	private TypeIF tipo;
	
	private TemporalIF punteroTemporal;
	
	private boolean esVariable;
	
	public Referencia(String lexema, TypeIF tipo, TemporalIF punteroTemporal, boolean esVariable, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
		this.tipo = tipo;
		
		this.punteroTemporal = punteroTemporal;
		
		this.esVariable = esVariable;
		
	}
	
	public TypeIF getTipo() {
		
		return this.tipo;
		
	}
	
	public TemporalIF getPunteroTemporal() {
		
		return this.punteroTemporal;
		
	}
	
	public boolean getEsVariable() {
		
		return this.esVariable;
		
	}
	
	// referencia ::= accesoVector:accesoVector
	public static Referencia produccion_accesoVector(AccesoVector accesoVector) {
 	
 		String lexema = accesoVector.getLexema();
 		
    	Consola.log("referencia[1]: \n" + lexema);
 	
 		return new Referencia(lexema, accesoVector.getTipo(), accesoVector.getPunteroTemporal(), true, accesoVector.getIntermediateCode());
		
	}

	// referencia ::= IDENTIFICADOR:identificador
	public static Referencia produccion_IDENTIFICADOR(TokenIF identificador) {

		String lexema = identificador.getLexema();
	 
    	Consola.log("referencia[2]: \n" + lexema);
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);
 		
 		TemporalFactoryIF temporalFactory = new TemporalFactory(scope);
	 
	 	String nombre = identificador.getLexema();
	 
	 	// Comprobar que la variable esta definida
	 	if (!Contexto.scopeManager.containsSymbol(nombre)) {
	 	
	 		Contexto.semanticErrorManager.semanticFatalError("Error, variable no definida: " + nombre);
	 	
	 	}
 		
 		SymbolIF simbolo = Contexto.scopeManager.searchSymbol(nombre);

 		boolean esVariable = simbolo instanceof SymbolVariable || simbolo instanceof SymbolParameter;
 		
 		// Generar codigo intermedio de esta expresion

 		TemporalIF punteroTemporal = temporalFactory.create();
 		
 		if (simbolo instanceof SymbolVariable || simbolo instanceof SymbolParameter) {

 	 		VariableIF variable = new Variable(nombre, simbolo.getScope());
 	 		
 	 		intermediateCodeBuilder.addQuadruple("POINT", punteroTemporal, variable);
 	 		
 		} else if (simbolo instanceof SymbolConstant) {

 	 		TemporalIF temporalConstante = temporalFactory.create();
 	 		
 	 		intermediateCodeBuilder.addQuadruple("COPY", temporalConstante, ((SymbolConstant)simbolo).getValor());
 	 		
 	 		intermediateCodeBuilder.addQuadruple("POINT", punteroTemporal, temporalConstante);
 	 		
 		} else {

	 		Contexto.semanticErrorManager.semanticFatalError("Error, referencia no es ni variable ni constante: " + nombre);
	 	
 		}
	 	
 		return new Referencia(lexema, simbolo.getType(), punteroTemporal, esVariable, intermediateCodeBuilder.create());
	}

	// referencia ::= OPEN_KEY:openKey referencia:referencia CLOSE_KEY:closeKey
	public static Referencia produccion_OPEN_KEY_referencia_CLOSE_KEY(TokenIF openKey, Referencia referencia, TokenIF closeKey) {

		String lexema = openKey.getLexema() + referencia.getLexema() + closeKey.getLexema();
	
    	Consola.log("expresion[3]: \n" + lexema);
	
		return new Referencia(lexema, referencia.getTipo(), referencia.getPunteroTemporal(), referencia.esVariable, referencia.getIntermediateCode());
		
	}	
		
}
