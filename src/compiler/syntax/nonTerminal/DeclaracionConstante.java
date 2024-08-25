package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.semantic.symbol.SymbolConstant;
import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolTableIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class DeclaracionConstante extends NonTerminal {

	public DeclaracionConstante(String lexema, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
	}
	
	// declaracionConstante ::= CONSTANTE:constante IDENTIFICADOR:identificador NUMERO:numero SEMI_COLON:semiColon
	public static DeclaracionConstante produccion(TokenIF constante, TokenIF identificador, TokenIF numero, TokenIF semiColon) {

		String lexema = constante.getLexema() + " " + identificador.getLexema() + " " + numero.getLexema() + semiColon.getLexema();

		Consola.log("declaracionConstante[1]: \n" + lexema); 

		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
		
		SymbolTableIF symbolTable = scope.getSymbolTable();
		
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
		
		String nombreConstante = identificador.getLexema();
		
		int valor = Integer.parseInt(numero.getLexema());
		
		// Comprobar si la constante ya se ha definido
		if (symbolTable.containsSymbol(nombreConstante)) {
			
			Contexto.semanticErrorManager.semanticFatalError("Error, constante ya definida: " + nombreConstante);
				
		} 
			
		symbolTable.addSymbol(nombreConstante, new SymbolConstant(scope, nombreConstante, tipoEntero, valor));
					
		return new DeclaracionConstante(lexema);
		
	}
	
}
