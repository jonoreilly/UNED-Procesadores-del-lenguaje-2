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

public class Referencia extends NonTerminal {

	private TypeIF tipo;
	
	private TemporalIF punteroTemporal;
	
	public Referencia(String lexema, TypeIF tipo, TemporalIF punteroTemporal, List<QuadrupleIF> intermediateCode) {
		
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
	
	// referencia ::= accesoVector:accesoVector
	public static Referencia produccion_accesoVector(AccesoVector accesoVector) {
 	
 		String lexema = accesoVector.getLexema();
 		
    	Consola.log("referencia[1]: \n" + lexema);
 	
 		return new Referencia(lexema, accesoVector.getTipo(), accesoVector.getPunteroTemporal(), accesoVector.getIntermediateCode());
		
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

 		// Generar codigo intermedio de esta expresion
 		
 		TemporalIF punteroTemporal = temporalFactory.create();
 		
 		VariableIF variable = new Variable(nombre, simbolo.getScope());
 		
 		intermediateCodeBuilder.addQuadruple("MV", punteroTemporal, variable);
	 	
 		return new Referencia(lexema, simbolo.getType(), punteroTemporal, intermediateCodeBuilder.create());
	}

	// referencia ::= OPEN_KEY:openKey referencia:referencia CLOSE_KEY:closeKey
	public static Referencia produccion_OPEN_KEY_referencia_CLOSE_KEY(TokenIF openKey, Referencia referencia, TokenIF closeKey) {

		String lexema = openKey.getLexema() + referencia.getLexema() + closeKey.getLexema();
	
    	Consola.log("expresion[3]: \n" + lexema);
	
		return new Referencia(lexema, referencia.getTipo(), referencia.getPunteroTemporal(), referencia.getIntermediateCode());
		
	}	
		
}
