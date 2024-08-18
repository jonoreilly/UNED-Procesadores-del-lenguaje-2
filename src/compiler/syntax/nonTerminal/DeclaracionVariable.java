package compiler.syntax.nonTerminal;

import compiler.semantic.symbol.SymbolVariable;
import compiler.utils.Consola;
import compiler.utils.Contexto;
import compiler.utils.IdDatos;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolTableIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class DeclaracionVariable extends NonTerminal {

	public DeclaracionVariable(String lexema) {
		
		super(lexema);
		
	}
	
	// declaracionVariable ::= ENTERO:entero listadoIDs:listadoIDs SEMI_COLON:semiColon
	public static DeclaracionVariable produccion_ENTERO_listadoIDs_SEMI_COLON(TokenIF entero, ListadoIDs listadoIDs, TokenIF semiColon) {

		String lexema = entero.getLexema() + " " + listadoIDs.getLexema() + semiColon.getLexema();

		Consola.log("declaracionVariable[1]: \n" + lexema); 
		
		ScopeIF scope = Contexto.scopeManager.getCurrentScope();

		SymbolTableIF symbolTable = scope.getSymbolTable();
		
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
		
		for (IdDatos id : listadoIDs.getIds()) {
		
			String nombreVariable = id.getNombre();
			
			// Comprobar si la variable ya esta definida en el ambito actual
			if (symbolTable.containsSymbol(nombreVariable)) {
				
				Contexto.semanticErrorManager.semanticFatalError("Error, variable ya definida: " + nombreVariable);
					
			} 
				
			symbolTable.addSymbol(nombreVariable, new SymbolVariable(scope, nombreVariable, tipoEntero));
							
		}
		
		return new DeclaracionVariable(lexema);

	}
	
	// declaracionVariable ::= IDENTIFICADOR:identificador listadoIDs:listadoIDs SEMI_COLON:semiColon
	public static DeclaracionVariable produccion_IDENTIFICADOR_listadoIDs_SEMI_COLON(TokenIF identificador, ListadoIDs listadoIDs, TokenIF semiColon) {

		String lexema = identificador.getLexema() + " " + listadoIDs.getLexema() + semiColon.getLexema();

			Consola.log("declaracionVariable[2]: \n" + lexema); 
			
		ScopeIF scope = Contexto.scopeManager.getCurrentScope();

		SymbolTableIF symbolTable = scope.getSymbolTable();
		
		String nombreTipo = identificador.getLexema();
		
		// Comprobar que el tipo usado existe
		if (!Contexto.scopeManager.containsType(nombreTipo)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, tipo no definido: " + nombreTipo);
			
		}
		
		TypeIF tipoVariable = Contexto.scopeManager.searchType(nombreTipo);
		
		for (IdDatos id : listadoIDs.getIds()) {
		
			String nombreVariable = id.getNombre();
			
			// Comprobar si la variable ya se ha definido en el ambito actual
			if (symbolTable.containsSymbol(nombreVariable)) {
				
				Contexto.semanticErrorManager.semanticFatalError("Error, variable ya definida: " + nombreVariable);
					
			} 
				
			// Comprobar que no se ha inicializado con un numero
			if (id.getValor() != null) {
			
				Contexto.semanticErrorManager.semanticFatalError("Error, no se puede asignar un numero a la variable: " + nombreVariable);
			
			}
			
			symbolTable.addSymbol(nombreVariable, new SymbolVariable(scope, nombreVariable, tipoVariable));
								
		}
		
		return new DeclaracionVariable(lexema);
		
	}
}
