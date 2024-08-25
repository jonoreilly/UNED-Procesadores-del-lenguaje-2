package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.semantic.symbol.SymbolProcedure;
import compiler.utils.Consola;
import compiler.utils.Contexto;
import compiler.utils.UtilsTiposDevuelve;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolTableIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class FuncionPrincipal extends NonTerminal {
	
	public FuncionPrincipal(String lexema, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
	}
	
	// funcionPrincipal ::= VACIO:vacio principal:principal OPEN_KEY:openKey CLOSE_KEY:closeKey OPEN_PARENTHESIS:openParenthesis
	public static void preProduccion_VACIO_principal_OPEN_KEY_CLOSE_KEY_OPEN_PARENTHESIS(TokenIF vacio, TokenIF principal, TokenIF openKey, TokenIF closeKey, TokenIF openParenthesis) {

		String lexema = vacio.getLexema() + " " + principal.getLexema() + openKey.getLexema() + closeKey.getLexema() + " " + openParenthesis.getLexema();

    	Consola.log("funcionPrincipal[1]: \n" + lexema);
		
		String nombreFuncion = principal.getLexema();
    	
    	TypeIF tipoVacio = Contexto.scopeManager.searchType("vacio");
		
		// Comprobar que no hay otra variable con este nombre en el ambito actual
		if (Contexto.scopeManager.containsSymbol(nombreFuncion)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, la funcion principal ya ha sido definida: " + nombreFuncion);
		
		}	

    	// Anadir la funcion al ambiente (para permitir recursividad)

		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
		
		SymbolTableIF symbolTable = scope.getSymbolTable();

		symbolTable.addSymbol(nombreFuncion, new SymbolProcedure(scope, nombreFuncion, tipoVacio));
		
		// Abrir nuevo ambiente para el bloque de la funcion
    	
    	Contexto.scopeManager.openScope();
		
	}
	
	// funcionPrincipal ::= VACIO:vacio principal:principal OPEN_KEY:openKey CLOSE_KEY:closeKey OPEN_PARENTHESIS:openParenthesis bloqueFuncionPrincipal:bloqueFuncionPrincipal
	public static FuncionPrincipal preProduccion_VACIO_principal_OPEN_KEY_CLOSE_KEY_OPEN_PARENTHESIS_bloqueFuncionPrincipal(TokenIF vacio, TokenIF principal, TokenIF openKey, TokenIF closeKey, TokenIF openParenthesis, BloqueFuncionPrincipal bloqueFuncionPrincipal) {

		String lexema = vacio.getLexema() + " " + principal.getLexema() + openKey.getLexema() + closeKey.getLexema() + " " + openParenthesis.getLexema() + "\n" + bloqueFuncionPrincipal.getLexema();

    	Consola.log("funcionPrincipal[2]: \n" + lexema);

		String nombreFuncion = principal.getLexema();
    	
		Contexto.scopeManager.closeScope();

		TypeIF tipoVacio = Contexto.scopeManager.searchType("vacio");

		List<TypeIF> tiposDevuelve = bloqueFuncionPrincipal.getTiposDevuelve();
		
		// Comprobar si todas las ramas de la funcion devuelven
		if (UtilsTiposDevuelve.tieneRamasSinResolver(tiposDevuelve)) {
			
			Contexto.semanticErrorManager.semanticFatalError("Error, la funcion tiene ramas sin resolver: " + nombreFuncion);
				
		} 

		// Comprobar que hay solo un tipo devuelto
		if(tiposDevuelve.size() > 1) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, la funcion tiene devuelve multiples tipos: " + nombreFuncion);
				
		} 
		
		// Comprobar que la funcion devuelve
		if (tiposDevuelve.size() == 0) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, la funcion no devuelve: " + nombreFuncion);
			
		} 

		TypeIF tipoDevuelve = tiposDevuelve.get(0);

		// Comprobar que el tipo de retorno de la funcion es igual que el tipo devuelto por las sentencias
		if (!tipoVacio.equals(tipoDevuelve)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, el tipo de retorno no coincide con el tipo de la funcion: " + nombreFuncion);
		
		} 
		
		return new FuncionPrincipal(lexema);
		
	}
	
}
