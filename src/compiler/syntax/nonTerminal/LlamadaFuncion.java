package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.intermediate.Value;
import compiler.semantic.symbol.SymbolFunction;
import compiler.semantic.symbol.SymbolProcedure;
import compiler.semantic.type.TypeFunction;
import compiler.semantic.type.TypeProcedure;
import compiler.utils.Consola;
import compiler.utils.Contexto;
import compiler.utils.ParametroTemporal;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.intermediate.TemporalFactory;
import es.uned.lsi.compiler.intermediate.TemporalFactoryIF;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class LlamadaFuncion extends NonTerminal {

	private TypeIF tipoRetorno;
	
	private TemporalIF temporal;
	
	public LlamadaFuncion(String lexema, TypeIF tipoRetorno, TemporalIF temporal, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
		this.tipoRetorno = tipoRetorno;
		
		this.temporal = temporal;
		
	}
	
	public TypeIF getTipoRetorno() {
	
		return this.tipoRetorno;
	
	}
	
	public TemporalIF getTemporal() {
		
		return this.temporal;
		
	}
	
	// llamadaFuncion ::= IDENTIFICADOR:identificador OPEN_KEY:openKey parametros:parametros CLOSE_KEY:closeKey
	public static LlamadaFuncion produccion_IDENTIFICADOR_OPEN_KEY_parametros_CLOSE_KEY(TokenIF identificador, TokenIF openKey, Parametros parametros, TokenIF closeKey) {

		String lexema = identificador.getLexema() + openKey.getLexema() + parametros.getLexema() + closeKey.getLexema();

		Consola.log("llamadaFuncion[1]: \n" + lexema);
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);

 		TemporalFactoryIF temporalFactory = new TemporalFactory(scope);
			
		String nombreFuncion = identificador.getLexema();
		
		// Comprobar que existe el simbolo
		if (!Contexto.scopeManager.containsSymbol(nombreFuncion)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, simbolo no encontrado: " + nombreFuncion);
		}
		
		SymbolIF simbolo = Contexto.scopeManager.searchSymbol(nombreFuncion);
		
		// Comprobar que es de tipo funcion, y no de tipo procedure
		if (!(simbolo instanceof SymbolFunction)) {
			
			Contexto.semanticErrorManager.semanticFatalError("Error, solo se pueden llamar funciones y procedimientos: " + nombreFuncion);
		
		}
		
		TypeFunction tipoFuncion = (TypeFunction)((SymbolFunction)simbolo).getType();
		
		TypeIF tipoRetorno = tipoFuncion.getTipoRetorno();
		
		List<TypeIF> parametrosFuncion = tipoFuncion.getParametros();
		
		List<ParametroTemporal> parametrosExpresion = parametros.getParametros();
		
		// Comprobar que tienen los mismos parametros
		if (parametrosFuncion.size() != parametrosExpresion.size()) {
			
			Contexto.semanticErrorManager.semanticFatalError("Error, faltan o sobran parametros: " + nombreFuncion);
	
		} 
		
		boolean coincidenLosParametros = true;
		
		for (int i = 0; i < parametrosFuncion.size(); i++) {
			
			TypeIF tipoDefinicionParametro = parametrosFuncion.get(i);
			
			TypeIF tipoLlamadaParametro = parametrosExpresion.get(i).getTipo();
			
			if (!tipoDefinicionParametro.equals(tipoLlamadaParametro)) {
				
				coincidenLosParametros = false;
				
				break;
				
			}
			
		}
		
		if (!coincidenLosParametros) {
	
			Contexto.semanticErrorManager.semanticFatalError("Error, los parametros no coinciden: " + nombreFuncion);
		
		}
				
 		// Encapsular codigo intermedio de las subexpresiones
 		 		
 		intermediateCodeBuilder.addQuadruples(parametros.getIntermediateCode());

 		// Generar codigo intermedio de esta expresion

		// TODO: Llamar a la funcion, y usar los parametros

 		Integer valorRetorno = 0;

 		TemporalIF temporalRetorno = temporalFactory.create();

 		intermediateCodeBuilder.addQuadruple("COPY", temporalRetorno, new Value(valorRetorno));
		
		return new LlamadaFuncion(lexema, tipoRetorno, temporalRetorno, intermediateCodeBuilder.create());
		
	}
	
	// llamadaFuncion ::= IDENTIFICADOR:identificador OPEN_KEY:openKey CLOSE_KEY:closeKey
	public static LlamadaFuncion produccion_IDENTIFICADOR_OPEN_KEY_CLOSE_KEY(TokenIF identificador, TokenIF openKey, TokenIF closeKey) {

		
		String lexema = identificador.getLexema() + openKey.getLexema() + closeKey.getLexema();
			
		Consola.log("llamadaFuncion[2]: \n" + lexema);

 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);

 		TemporalFactoryIF temporalFactory = new TemporalFactory(scope);
	
		String nombreFuncion = identificador.getLexema();
		
		// Comprobar que existe el simbolo
		if (!Contexto.scopeManager.containsSymbol(nombreFuncion)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, simbolo no encontrado: " + nombreFuncion);

		}
		
		SymbolIF simbolo = Contexto.scopeManager.searchSymbol(nombreFuncion);
		
		// Comprobar que es de tipo procedure, y no de tipo funcion
		if (!(simbolo instanceof SymbolProcedure)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, solo se pueden llamar funciones y procedimientos: " + nombreFuncion);
		
		}
			
		if (simbolo instanceof SymbolFunction) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, una llamada a funcion requiere parametros: " + nombreFuncion);
		
		} 

		TypeProcedure tipoProcedimiento = (TypeProcedure)((SymbolProcedure)simbolo).getType();
		
		TypeIF tipoRetorno = tipoProcedimiento.getTipoRetorno();

 		// Generar codigo intermedio de esta expresion

		// TODO: Llamar a la funcion, y usar los parametros

 		Integer valorRetorno = 0;

 		TemporalIF temporalRetorno = temporalFactory.create();

 		intermediateCodeBuilder.addQuadruple("COPY", temporalRetorno, new Value(valorRetorno));
		
		return new LlamadaFuncion(lexema, tipoRetorno, temporalRetorno, intermediateCodeBuilder.create());
	}
		
}
