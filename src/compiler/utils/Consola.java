package compiler.utils;

import compiler.semantic.symbol.SymbolFunction;
import compiler.semantic.symbol.SymbolProcedure;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolTableIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;
import es.uned.lsi.compiler.semantic.type.TypeTableIF;

public class Consola {
	
	public static void logAmbiente() {
		
		Contexto.semanticErrorManager.semanticInfo("Ambiente");
		
		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
		
		while (true) {
			
			Contexto.semanticErrorManager.semanticInfo("  Nivel: " + scope.getLevel());
			
			// Simbolos
			
			Contexto.semanticErrorManager.semanticInfo("    Simbolos");
			
			SymbolTableIF tablaSimbolos = scope.getSymbolTable();
			
			for (SymbolIF simbolo : tablaSimbolos.getSymbols()) {
				
				Contexto.semanticErrorManager.semanticInfo("      " + simbolo.getName() + ": " + getSimboloTypeName(simbolo));
				
			}

			// Tipos
			
			Contexto.semanticErrorManager.semanticInfo("    Tipos");
			
			TypeTableIF tablaTipos = scope.getTypeTable();
			
			for (TypeIF tipo : tablaTipos.getTypes()) {
				
				Contexto.semanticErrorManager.semanticInfo("      " + tipo.getName() + ": " + tipo.getClass().getName());
				
			}
		
			scope = scope.getParentScope();
			
			if (scope == null) {
				break;
			}
			
		}
		
	}
	
	public static void log(String cadena) {
		/*
		Contexto.semanticErrorManager.semanticInfo("");
		Contexto.semanticErrorManager.semanticInfo("");
		
		String[] lineas = cadena.split("\n");
		
		for (String linea : lineas) {
			
			Contexto.semanticErrorManager.semanticInfo(linea);

		}

		Contexto.semanticErrorManager.semanticInfo("");
		
		logAmbiente();
		*/
	}
	
	private static String getSimboloTypeName(SymbolIF simbolo) {
		
		if (simbolo instanceof SymbolFunction) {
			
			return "(" + String.join(", ", ((SymbolFunction)simbolo).getTiposParametros().stream().map((p) -> p.getName()).toList()) + ") -> " + simbolo.getType().getName();
			
		}
		
		if (simbolo instanceof SymbolProcedure) {
			
			return "() -> " + simbolo.getType().getName();
			
		}
		
		return simbolo.getType().getName();
		
	}
	
}
