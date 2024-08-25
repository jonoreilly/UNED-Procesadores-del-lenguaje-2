package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.semantic.type.TypeArray;
import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;
import es.uned.lsi.compiler.semantic.type.TypeTableIF;

public class DeclaracionTipo extends NonTerminal {

	public DeclaracionTipo(String lexema, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
	}
	
	// declaracionTipo ::= TIPO:tipo ENTERO:entero IDENTIFICADOR:identificador OPEN_BRACKET:openBracket tamTipo:tamTipo CLOSE_BRACKET:closeBracket SEMI_COLON:semiColon
	public static DeclaracionTipo produccion(TokenIF tipo, TokenIF entero, TokenIF identificador, TokenIF openBracket, TamTipo tamTipo, TokenIF closeBracket, TokenIF semiColon) {

		String lexema = tipo.getLexema() + " " + entero.getLexema() + " " + identificador.getLexema() + openBracket.getLexema() + tamTipo.getLexema() + closeBracket.getLexema() + semiColon.getLexema();
		
		Consola.log("declaracionTipo[1]: \n" + lexema); 
			
		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
		
		TypeTableIF typeTable = scope.getTypeTable();
		
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
		
		String nombreTipo = identificador.getLexema();
		
		int tamanio = tamTipo.getValor();
		
		// Comprobar si ya se ha definido este tipo en alguno de los ambitos activos
		if (Contexto.scopeManager.containsType(nombreTipo)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, tipo ya definido: " + nombreTipo);
		
		} 
		
		// Comprobar que el tamanio es mayor que 0
		if (tamanio <= 0) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, el tamanio del vector debe ser mayor que 0: " + nombreTipo);
	
		}
		
		typeTable.addType(nombreTipo, new TypeArray(scope, nombreTipo, tamanio, tipoEntero));
		
		return new DeclaracionTipo(lexema);
		
	}
	
}
