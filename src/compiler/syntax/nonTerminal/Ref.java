package compiler.syntax.nonTerminal;

import compiler.semantic.symbol.SymbolVariable;
import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class Ref extends NonTerminal {

	private TypeIF tipo;
	
	public Ref(String lexema, TypeIF tipo) {
		super(lexema);
		
		this.tipo = tipo;
	}
	
	public TypeIF getTipo() {
		return this.tipo;
	}
	
	// ref ::= IDENTIFICADOR:identificador
	public static Ref produccion_IDENTIFICADOR(TokenIF identificador) {

		String lexema = identificador.getLexema();

		Consola.log("ref[1]: \n" + lexema); 
       			
		String nombreVariable = identificador.getLexema();
			
		// Comprobar que existe el simbolo
		if (!Contexto.scopeManager.containsSymbol(nombreVariable)) {
					
			Contexto.semanticErrorManager.semanticFatalError("Error, variable no encontrada: " + nombreVariable);
		
		}
				
		SymbolIF simbolo = Contexto.scopeManager.searchSymbol(nombreVariable);
				
		// Comprobar que ref es una variable, y no una constante 
		if (!(simbolo instanceof SymbolVariable)) {
				
			Contexto.semanticErrorManager.semanticFatalError("Error, la parte izquierda de una asignacion debe ser una variable: " + nombreVariable);
		
		}
				
		return new Ref(lexema, simbolo.getType());
		
	}

	// ref ::= accesoVector:accesoVector
	public static Ref produccion_accesoVector(AccesoVector accesoVector) {
		
		String lexema = accesoVector.getLexema();
	
		Consola.log("ref[2]: \n" + lexema);
		
		return new Ref(lexema, accesoVector.getTipo());
		
	}
	
}
