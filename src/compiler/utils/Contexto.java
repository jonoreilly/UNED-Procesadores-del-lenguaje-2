package compiler.utils;

import es.uned.lsi.compiler.code.FinalCodeFactoryIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.SemanticErrorManager;
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
	}
		
}
