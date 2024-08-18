package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.semantic.type.TypeFunction;
import compiler.semantic.type.TypeProcedure;
import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class LlamadaFuncion extends NonTerminal {

	private TypeIF tipoRetorno;
	
	public LlamadaFuncion(String lexema, TypeIF tipoRetorno) {
		super(lexema);
		
		this.tipoRetorno = tipoRetorno;
	}
	
	public TypeIF getTipoRetorno() {
		return this.tipoRetorno;
	}
	
	// llamadaFuncion ::= IDENTIFICADOR:identificador OPEN_KEY:openKey parametros:parametros CLOSE_KEY:closeKey
	public static LlamadaFuncion produccion_IDENTIFICADOR_OPEN_KEY_parametros_CLOSE_KEY(TokenIF identificador, TokenIF openKey, Parametros parametros, TokenIF closeKey) {

		String lexema = identificador.getLexema() + openKey.getLexema() + parametros.getLexema() + closeKey.getLexema();

		Consola.log("llamadaFuncion[1]: \n" + lexema);
			
		String nombreFuncion = identificador.getLexema();
		
		// Comprobar que existe el simbolo
		if (!Contexto.scopeManager.containsSymbol(nombreFuncion)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, simbolo no encontrado: " + nombreFuncion);
		}
		
		SymbolIF simbolo = Contexto.scopeManager.searchSymbol(nombreFuncion);
		
		TypeIF tipo = simbolo.getType();
		
		// Comprobar que es de tipo funcion, y no de tipo procedure
		if (!(tipo instanceof TypeFunction)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, solo se pueden llamar funciones y procedimientos");
		
		}
		
		List<TypeIF> parametrosFuncion = ((TypeFunction)tipo).getParametros();
		
		List<TypeIF> parametrosExpresion = parametros.getParametros();
		
		// Comprobar que tienen los mismos parametros
		if (parametrosFuncion.size() != parametrosExpresion.size()) {
			
			Contexto.semanticErrorManager.semanticFatalError("Error, faltan o sobran parametros");
	
		} 
		
		boolean coincidenLosParametros = true;
		
		for (int i = 0; i < parametrosFuncion.size(); i++) {
			
			if (!parametrosFuncion.get(i).equals(parametrosExpresion.get(i))) {
				
				coincidenLosParametros = false;
				
				break;
				
			}
			
		}
		
		if (!coincidenLosParametros) {
	
			Contexto.semanticErrorManager.semanticFatalError("Error, los parametros no coinciden");
		
		}
		
		return new LlamadaFuncion(lexema, ((TypeFunction)tipo).getTipoRetorno());
	}
	
	// llamadaFuncion ::= IDENTIFICADOR:identificador OPEN_KEY:openKey CLOSE_KEY:closeKey
	public static LlamadaFuncion produccion_IDENTIFICADOR_OPEN_KEY_CLOSE_KEY(TokenIF identificador, TokenIF openKey, TokenIF closeKey) {

		
		String lexema = identificador.getLexema() + openKey.getLexema() + closeKey.getLexema();
			
		Consola.log("llamadaFuncion[2]: \n" + lexema);
	
		String nombreFuncion = identificador.getLexema();
		
		// Comprobar que existe el simbolo
		if (!Contexto.scopeManager.containsSymbol(nombreFuncion)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, simbolo no encontrado: " + nombreFuncion);

		}
		
		SymbolIF simbolo = Contexto.scopeManager.searchSymbol(nombreFuncion);
		
		TypeIF tipo = simbolo.getType();
		
		// Comprobar que es de tipo procedure, y no de tipo funcion
		if (!(tipo instanceof TypeProcedure)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, solo se pueden llamar funciones y procedimientos");
		
		}
			
		if (tipo instanceof TypeFunction) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, una llamada a funcion requiere parametros");
		
		} 
		
		return new LlamadaFuncion(lexema, ((TypeProcedure)tipo).getTipoRetorno());
	}
		
}
