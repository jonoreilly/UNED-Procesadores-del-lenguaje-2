package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.semantic.symbol.SymbolConstant;
import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;

public class TamTipo extends NonTerminal {
	
	private int valor;
	
	public TamTipo(String lexema, int valor, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
		this.valor = valor;
	
	}
	
	public int getValor() {
	
		return this.valor;
	
	}
	
	// tamTipo ::= NUMERO:numero
	public static TamTipo produccion_NUMERO(TokenIF numero) {

		String lexema = numero.getLexema();

		Consola.log("tamTipo[1]: \n" + lexema); 
		
		int valor = Integer.parseInt(numero.getLexema());
		
		return new TamTipo(lexema, valor, new ArrayList<>());
		
	}
	
	// tamTipo ::= IDENTIFICADOR:identificador
	public static TamTipo produccion_IDENTIFICADOR(TokenIF identificador) {

		String lexema = identificador.getLexema();
		
		Consola.log("tamTipo[2]: \n" + lexema);
		
		String nombreConstante = identificador.getLexema();
		
		// Comprobar que existe el simbolo
		if (!Contexto.scopeManager.containsSymbol(nombreConstante)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, constante no definida: " + nombreConstante);
				
		}
			
		SymbolIF symbol = Contexto.scopeManager.searchSymbol(nombreConstante);
		
		// Comprobar que es una constante, y no una variable o funcion
		if (!(symbol instanceof SymbolConstant)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, solo se pueden usar constantes para definir tipos: " + nombreConstante);
		
		}
		
		SymbolConstant symbolConstant = (SymbolConstant)symbol;
		
		int valor = symbolConstant.getValor();

		return new TamTipo(lexema, valor, new ArrayList<>());
		
	}
	
}
