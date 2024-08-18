package compiler.syntax.nonTerminal;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class Expresion extends NonTerminal {

	public TypeIF tipo;
	
	public Expresion(String lexema, TypeIF tipo) {
		
		super(lexema);
		
		this.tipo = tipo;

	}
	
	public TypeIF getTipo() {
	
		return this.tipo;
	
	}
	
	// expresion ::= NUMERO:numero
	public static Expresion produccion_NUMERO(TokenIF numero) {

		String lexema = numero.getLexema();

    	Consola.log("expresion[1]: \n" + lexema);

		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
		
		return new Expresion(lexema, tipoEntero);
		
	}
	
	// expresion ::= IDENTIFICADOR:identificador
	public static Expresion produccion_IDENTIFICADOR(TokenIF identificador) {

		String lexema = identificador.getLexema();
	 
    	Consola.log("expresion[2]: \n" + lexema);
	 
	 	String nombre = identificador.getLexema();
	 
	 	// Comprobar que la variable esta definida
	 	if (!Contexto.scopeManager.containsSymbol(nombre)) {
	 	
	 		Contexto.semanticErrorManager.semanticFatalError("Error, variable no definida: " + nombre);
	 	
	 	} 
	 	
 		return new Expresion(lexema, Contexto.scopeManager.searchSymbol(nombre).getType());
		
	}

	// expresion ::= OPEN_KEY:openKey expresion:expresion CLOSE_KEY:closeKey
	public static Expresion produccion_OPEN_KEY_expresion_CLOSE_KEY(TokenIF openKey, Expresion expresion, TokenIF closeKey) {

		String lexema = openKey.getLexema() + expresion.getLexema() + closeKey.getLexema();
	
    	Consola.log("expresion[3]: \n" + lexema);
	
		return new Expresion(lexema, expresion.getTipo());
		
	}

	// expresion ::= expresion:expresion1 PLUS:plus expresion:expresion2
	public static Expresion produccion_expresion_PLUS_expresion(Expresion expresion1, TokenIF plus, Expresion expresion2) {

		String lexema = expresion1.getLexema() + " " + plus.getLexema() + " " + expresion2.getLexema();
 	
    	Consola.log("expresion[4]: \n" + lexema);
 	
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
 		
 		// Comprobar que ambas expresiones son de tipo numerico
 		if (!tipoEntero.equals(expresion1.getTipo()) || !tipoEntero.equals(expresion2.getTipo())) {
 		
 			Contexto.semanticErrorManager.semanticFatalError("Error, expresion no es de tipo entero: " + expresion1.getTipo().getName() + " + " + expresion2.getTipo().getName());
			
 		}
 		
		return new Expresion(lexema, tipoEntero);
		
	}

	// expresion ::= expresion:expresion1 MULT:mult expresion:expresion2
	public static Expresion produccion_expresion_MULT_expresion(Expresion expresion1, TokenIF mult, Expresion expresion2) {

		String lexema = expresion1.getLexema() + " " + mult.getLexema() + " " + expresion2.getLexema();
 	 	
    	Consola.log("expresion[5]: \n" + lexema);
 	
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
 		
 		// Comprobar que ambas expresiones son de tipo numerico
 		if (!tipoEntero.equals(expresion1.getTipo()) || !tipoEntero.equals(expresion2.getTipo())) {
 		
 			Contexto.semanticErrorManager.semanticFatalError("Error, expresion no es de tipo entero: " + expresion1.getTipo().getName() + " * " + expresion2.getTipo().getName());
			
 		} 
 		
		return new Expresion(lexema, tipoEntero);
		
	}

	// expresion ::= expresion:expresion AUTO_INCREMENTO:autoIncremento
	public static Expresion produccion_expresion_AUTO_INCREMENTO(Expresion expresion, TokenIF autoIncremento) {

		String lexema = expresion.getLexema() + autoIncremento.getLexema();
 	
    	Consola.log("expresion[6]: \n" + lexema);
 	
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
 		
 		// Comprobar la expresion es de tipo numerico
 		if (!tipoEntero.equals(expresion.getTipo())) {
 		
			Contexto.semanticErrorManager.semanticFatalError("Error, expresion no es de tipo entero: " + expresion.getTipo().getName() + "++");
			
 		} 
 		
 		return new Expresion(lexema, tipoEntero);
		
	}

	// expresion ::= NEGACION:negacion expresion:expresion
	public static Expresion produccion_NEGACION_expresion(TokenIF negacion, Expresion expresion) {

		String lexema = negacion.getLexema() + expresion.getLexema();
 	
    	Consola.log("expresion[7]: \n" + lexema);
 	
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
 		
 		// Comprobar la expresion es de tipo numerico
 		if (!tipoEntero.equals(expresion.getTipo())) {
 		
 			Contexto.semanticErrorManager.semanticFatalError("Error, expresion no es de tipo entero: !" + expresion.getTipo().getName());
			
 		} 
 		
		return new Expresion(lexema, tipoEntero);
		
	}

	// expresion ::= expresion:expresion1 LOWER_THAN:lowerThan expresion:expresion2
	public static Expresion produccion_expresion_LOWER_THAN_expresion(Expresion expresion1, TokenIF lowerThan, Expresion expresion2) {

		String lexema = expresion1.getLexema() + " " + lowerThan.getLexema() + " " + expresion2.getLexema();
 	
    	Consola.log("expresion[8]: \n" + lexema);
 	
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
 		
 		// Comprobar que ambas expresiones son de tipo numerico
 		if (!tipoEntero.equals(expresion1.getTipo()) || !tipoEntero.equals(expresion2.getTipo())) {
 		
			Contexto.semanticErrorManager.semanticFatalError("Error, expresion no es de tipo entero: " + expresion1.getTipo().getName() + " < " + expresion2.getTipo().getName());
			
 		}
 		
		return new Expresion(lexema, tipoEntero);
		
	}

	// expresion ::= expresion:expresion1 EQUAL:equal expresion:expresion2
	public static Expresion produccion_expresion_EQUAL_expresion(Expresion expresion1, TokenIF equal, Expresion expresion2) {

 		String lexema = expresion1.getLexema() + " " + equal.getLexema() + " " + expresion2.getLexema();
 	
    	Consola.log("expresion[9]: \n" + lexema);
 	
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
 		
 		// Comprobar que ambas expresiones son de tipo numerico
 		if (!tipoEntero.equals(expresion1.getTipo()) || !tipoEntero.equals(expresion2.getTipo())) {
 		
 			Contexto.semanticErrorManager.semanticFatalError("Error, expresion no es de tipo entero: " + expresion1.getTipo().getName() + " = " + expresion2.getTipo().getName());
			
 		} 
 		
		return new Expresion(lexema, tipoEntero);
		
	}

	// expresion ::= expresion:expresion1 AND_LOGICA:andLogica expresion:expresion2
	public static Expresion produccion_expresion_AND_LOGICA_expresion(Expresion expresion1, TokenIF andLogica, Expresion expresion2) {

 		String lexema = expresion1.getLexema() + " " + andLogica.getLexema() + " " + expresion2.getLexema();
 	
    	Consola.log("expresion[10]: \n" + lexema);
 	
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
 		
 		// Comprobar que ambas expresiones son de tipo numerico
 		if (!tipoEntero.equals(expresion1.getTipo()) || !tipoEntero.equals(expresion2.getTipo())) {
 		
 			Contexto.semanticErrorManager.semanticFatalError("Error, expresion no es de tipo entero: " + expresion1.getTipo().getName() + " && " + expresion2.getTipo().getName());
			
 		} 		
			
		return new Expresion(lexema, tipoEntero);
		
	}

	// expresion ::= accesoVector:accesoVector
	public static Expresion produccion_accesoVector(AccesoVector accesoVector) {
 	
 		String lexema = accesoVector.getLexema();
 		
    	Consola.log("expresion[11]: \n" + lexema);
 	
 		return new Expresion(lexema, accesoVector.getTipo());
		
	}

	// expresion ::= llamadaFuncion:llamadaFuncion
	public static Expresion produccion_llamadaFuncion(LlamadaFuncion llamadaFuncion) {

 		String lexema = llamadaFuncion.getLexema();
 	
    	Consola.log("expresion[12]: \n" + lexema);
 	
 		return new Expresion(lexema, llamadaFuncion.getTipoRetorno());
		
	}
	
}
