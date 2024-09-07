package compiler.utils;

import java.util.ArrayList;
import java.util.List;

import compiler.intermediate.Value;
import compiler.semantic.symbol.SymbolVariable;
import es.uned.lsi.compiler.intermediate.Quadruple;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;

public class Memoria {

	public static int poblarMemoria() {

		int stackPointer = Contexto.finalCodeFactory.getEnvironment().getMemorySize();
		
		for (ScopeIF scope : Contexto.scopeManager.getAllScopes()) {
			
			for (SymbolIF simbolo : scope.getSymbolTable().getSymbols()) {
			
				if (simbolo instanceof SymbolVariable) {
					
					stackPointer -= simbolo.getType().getSize();
					
					int direccion = stackPointer + 1;
				
					((SymbolVariable)simbolo).setAddress(direccion);

					Contexto.semanticErrorManager.semanticInfo(scope.getName() + " - " + simbolo.getName() + " = /" + direccion);
										
				}
		
			}
			
			for (TemporalIF temporal : scope.getTemporalTable().getTemporals()) {
				
				stackPointer--;
				
				int direccion = stackPointer + 1;
				
				temporal.setAddress(direccion);
				
				Contexto.semanticErrorManager.semanticInfo(scope.getName() + " - " + temporal.getName() + " = /" + direccion);
								
			}
		
		}
		
		return stackPointer;
		
	}
	
	public static List<QuadrupleIF> getCuadruplasMoverStackPointer(int stackPointer) {
		
		List<QuadrupleIF> codigoIntermedio = new ArrayList<>();
		
		codigoIntermedio.add(new Quadruple("SET_STACK_POINTER", new Value(stackPointer)));
		
		return codigoIntermedio;
		
	}
	
}
