package compiler.utils;

import compiler.semantic.type.TypeArray;
import compiler.semantic.type.TypeFunction;
import compiler.semantic.type.TypeProcedure;
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
				
				Contexto.semanticErrorManager.semanticInfo("      " + simbolo.getName() + ": " + getDescripcionTipoSimbolo(simbolo.getType()));
				
			}

			// Tipos
			
			Contexto.semanticErrorManager.semanticInfo("    Tipos");
			
			TypeTableIF tablaTipos = scope.getTypeTable();
			
			for (TypeIF tipo : tablaTipos.getTypes()) {
				
				Contexto.semanticErrorManager.semanticInfo("      " + tipo.getName() + ": " + getDescripcionTipo(tipo));
				
			}
		
			scope = scope.getParentScope();
			
			if (scope == null) {
				break;
			}
			
		}
		
	}
	
	public static void log(String cadena) {
		
		Contexto.semanticErrorManager.semanticInfo("");
		Contexto.semanticErrorManager.semanticInfo("");
		
		String[] lineas = cadena.split("\n");
		
		for (String linea : lineas) {
			
			Contexto.semanticErrorManager.semanticInfo(linea);

		}

		Contexto.semanticErrorManager.semanticInfo("");
		
		logAmbiente();
		
	}
	
	private static String getDescripcionTipoSimbolo(TypeIF tipo) {

		if (tipo instanceof TypeFunction) {
			
			return "(" + String.join(", ", ((TypeFunction)tipo).getParametros().stream().map((p) -> p.getName()).toList()) + ") -> " + ((TypeFunction)tipo).getTipoRetorno().getName();
			
		}
		
		if (tipo instanceof TypeProcedure) {
			
			return "() -> " + ((TypeProcedure)tipo).getTipoRetorno().getName();
			
		}
		
		return tipo.getClass().getName();
		
	}

	private static String getDescripcionTipo(TypeIF tipo) {

		if (tipo instanceof TypeFunction) {
			
			return "(" + String.join(", ", ((TypeFunction)tipo).getParametros().stream().map((p) -> p.getName()).toList()) + ") -> " + ((TypeFunction)tipo).getTipoRetorno().getName();
			
		}
		
		if (tipo instanceof TypeProcedure) {
			
			return "() -> " + ((TypeProcedure)tipo).getTipoRetorno().getName();
			
		}
		
		if (tipo instanceof TypeArray) {
			
			return ((TypeArray) tipo).getTipoElemento().getName() + "[" + ((TypeArray) tipo).getLongitud() + "]";
					
		}
		
		return tipo.getClass().getName();
		
	}
	
}
