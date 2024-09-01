package compiler.utils;

import compiler.code.ExecutionEnvironmentEns2001;
import compiler.semantic.symbol.SymbolVariable;
import es.uned.lsi.compiler.code.FinalCodeFactoryIF;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.SemanticErrorManager;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;
import es.uned.lsi.compiler.syntax.SyntaxErrorManager;

public class Contexto {

	public static SyntaxErrorManager syntaxErrorManager;

	public static SemanticErrorManager semanticErrorManager;
	
	public static ScopeManagerIF scopeManager;
	
	public static FinalCodeFactoryIF finalCodeFactory;
	
	public static ListaCadenas listaCadenas = new ListaCadenas();
	
	public static void init(SyntaxErrorManager syntaxErrorManager, SemanticErrorManager semanticErrorManager, ScopeManagerIF scopeManager, FinalCodeFactoryIF finalCodeFactory) {
		Contexto.syntaxErrorManager = syntaxErrorManager;
		Contexto.semanticErrorManager = semanticErrorManager;
		Contexto.scopeManager = scopeManager;
		Contexto.finalCodeFactory = finalCodeFactory;
		
		finalCodeFactory.setEnvironment(new ExecutionEnvironmentEns2001());
	}
	
	public static void populateAddresses() {

		int cont = 3000;
		
		for (ScopeIF scope : scopeManager.getAllScopes()) {
			
			for (SymbolIF simbolo : scope.getSymbolTable().getSymbols()) {
			
				if (simbolo instanceof SymbolVariable) {
				
					semanticErrorManager.semanticInfo(scope.getName() + " - " + simbolo.getName() + " = /" + cont);
					
					((SymbolVariable)simbolo).setAddress(cont);
			
					cont++;
					
				}
		
			}
			
			for (TemporalIF temporal : scope.getTemporalTable().getTemporals()) {

				temporal.setAddress(cont);
				
				semanticErrorManager.semanticInfo(scope.getName() + " - " + temporal.getName() + " = /" + temporal.getAddress());
				
				
				cont++;
				
			}
		
		}
		
	}
		
}
