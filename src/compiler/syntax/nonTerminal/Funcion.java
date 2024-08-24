package compiler.syntax.nonTerminal;

import java.util.List;
import java.util.stream.Collectors;

import compiler.semantic.symbol.SymbolFunction;
import compiler.semantic.symbol.SymbolParameter;
import compiler.semantic.symbol.SymbolProcedure;
import compiler.utils.Consola;
import compiler.utils.Contexto;
import compiler.utils.ParametroDatos;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolTableIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class Funcion extends NonTerminal {

	public Funcion(String lexema) {
		
		super(lexema);
		
	}
	
	// funcion ::= tipoFuncion:tipoFuncion IDENTIFICADOR:identificador OPEN_KEY:openKey
	public static void preProduccion_tipoFuncion(TipoFuncion tipoFuncion) {

		String lexema = tipoFuncion.getLexema();

    	Consola.log("funcion[1]: \n" + lexema);

		ScopeIF scope = Contexto.scopeManager.getCurrentScope();

		SymbolTableIF symbolTable = scope.getSymbolTable();
		
		String nombreFuncion = tipoFuncion.getNombreFuncion();
		
		// Comprobar que no hay otra variable con este nombre en el ambito actual
		if (symbolTable.containsSymbol(nombreFuncion)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, ya existe otra variable con este nombre: " + nombreFuncion);
		
		}	
		
	}

	// funcion ::= tipoFuncion:tipoFuncion seccionParametros:seccionParametros CLOSE_KEY:closeKey OPEN_PARENTHESIS:openParenthesis
	public static void preProduccion_tipoFuncion_seccionParametros_CLOSE_KEY_OPEN_PARENTHESIS(TipoFuncion tipoFuncion, SeccionParametros seccionParametros, TokenIF closeKey, TokenIF openParenthesis) {
		
		String lexema = tipoFuncion.getLexema() + seccionParametros.getLexema() + closeKey.getLexema() + " " + openParenthesis.getLexema();
    	
    	Consola.log("funcion[2]: \n" + lexema);

    	TypeIF tipoVacio = Contexto.scopeManager.searchType("vacio");
    	
    	TypeIF tipoDeLaFuncion = tipoFuncion.getTipo();

		String nombreFuncion = tipoFuncion.getNombreFuncion();

		List<ParametroDatos> parametros = seccionParametros.getParametros();
		
		// Comprobar que el nombre de la funcion no esta usado ya
		if (Contexto.scopeManager.containsSymbol(nombreFuncion)) {

			Contexto.semanticErrorManager.semanticFatalError("Error, ya existe otra variable con este nombre: " + nombreFuncion);
	
		}

    	// Anadir la funcion al ambiente (para permitir recursividad)

		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
		
		SymbolTableIF symbolTable = scope.getSymbolTable();

		List<TypeIF> tiposParametros = parametros.stream().map(p -> p.getTipo()).collect(Collectors.toList());
		
		if (tiposParametros.size() > 0) {

			symbolTable.addSymbol(nombreFuncion, new SymbolFunction(scope, nombreFuncion, tipoDeLaFuncion, tiposParametros));

		} else {

			symbolTable.addSymbol(nombreFuncion, new SymbolProcedure(scope, nombreFuncion, tipoDeLaFuncion));
			
		}

		// Anadir parametros al ambiente interno
    	
    	Contexto.scopeManager.openScope();
    	
		scope = Contexto.scopeManager.getCurrentScope();

		symbolTable = scope.getSymbolTable();
		
		for (ParametroDatos parametro : parametros) {
		
			String nombreParametro = parametro.getNombre();
			
			TypeIF tipoParametro = parametro.getTipo();
			
    		Consola.log("Parametro funcion " + nombreFuncion + ": " + tipoParametro.getName() + " " + nombreParametro);
			
			// Comprobar que no hay otro parametro con el mismo nombre
			if (symbolTable.containsSymbol(nombreParametro)) {
			
				Contexto.semanticErrorManager.semanticFatalError("Error, ya existe otro parametro con este nombre: " + nombreParametro);
		
			}
								
			// Comprobar que el tipo del parametro es aceptable (entero, array) = no vacio
			if (tipoVacio.equals(tipoParametro)) {

				Contexto.semanticErrorManager.semanticFatalError("Error, un parametro no puede ser de tipo vacio: " + nombreParametro);
		
			}
			
			symbolTable.addSymbol(nombreParametro, new SymbolParameter(scope, nombreParametro, tipoParametro));
		
		}
		
		
	}

	// funcion ::= tipoFuncion:tipoFuncion seccionParametros:seccionParametros CLOSE_KEY:closeKey OPEN_PARENTHESIS:openParenthesis seccionTipos:seccionTipos funcion1:funcion1
	public static Funcion produccion_tipoFuncion_seccionParametros_CLOSE_KEY_OPEN_PARENTHESIS_seccionTipos_funcion1(TipoFuncion tipoFuncion, SeccionParametros seccionParametros, TokenIF closeKey, TokenIF openParenthesis, SeccionTipos seccionTipos, Funcion1 funcion1) {

		String lexema = tipoFuncion.getLexema() + seccionParametros.getLexema() + closeKey.getLexema() + " " + openParenthesis.getLexema() + "\n" + seccionTipos.getLexema() + "\n" + funcion1.getLexema();
    	
    	Consola.log("funcion[3]: \n" + lexema);
    	
		Contexto.scopeManager.closeScope();
    	
    	TypeIF tipoDeLaFuncion = tipoFuncion.getTipo();

		TypeIF tipoDevuelve = funcion1.getTipoDevuelve();

		String nombreFuncion = tipoFuncion.getNombreFuncion();

		// Comprobar que el tipo de retorno de la funcion es igual que el tipo devuelto por las sentencias
		if (!tipoDeLaFuncion.equals(tipoDevuelve)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, el tipo de retorno no coincide con el tipo de la funcion: " + nombreFuncion);
		
		} 
		
		return new Funcion(lexema);
		
	}
	
}
