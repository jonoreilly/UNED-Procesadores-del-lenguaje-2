package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.intermediate.Value;
import compiler.intermediate.Variable;
import compiler.semantic.symbol.SymbolVariable;
import compiler.semantic.type.TypeArray;
import compiler.utils.Consola;
import compiler.utils.Contexto;
import compiler.utils.IdDatos;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.intermediate.TemporalFactory;
import es.uned.lsi.compiler.intermediate.TemporalFactoryIF;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolTableIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class DeclaracionVariable extends NonTerminal {

	public DeclaracionVariable(String lexema, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
	}
	
	// declaracionVariable ::= ENTERO:entero listadoIDs:listadoIDs SEMI_COLON:semiColon
	public static DeclaracionVariable produccion_ENTERO_listadoIDs_SEMI_COLON(TokenIF entero, ListadoIDs listadoIDs, TokenIF semiColon) {

		String lexema = entero.getLexema() + " " + listadoIDs.getLexema() + semiColon.getLexema();

		Consola.log("declaracionVariable[1]: \n" + lexema); 
		 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);
 		
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");

		SymbolTableIF symbolTable = scope.getSymbolTable();

 		// Encapsular codigo intermedio de las subexpresiones
 		 		
 		intermediateCodeBuilder.addQuadruples(listadoIDs.getIntermediateCode());
				
		for (IdDatos id : listadoIDs.getIds()) {
		
			String nombreVariable = id.getNombre();
			
			Integer valorVariable = id.getValor();
			
			// Comprobar si la variable ya esta definida en el ambito actual
			if (symbolTable.containsSymbol(nombreVariable)) {
				
				Contexto.semanticErrorManager.semanticFatalError("Error, variable ya definida: " + nombreVariable);
					
			} 
				
			symbolTable.addSymbol(nombreVariable, new SymbolVariable(scope, nombreVariable, tipoEntero));
			
			// Crear codigo intermedio para inicializar la variable
			
			Variable var = new Variable(nombreVariable, scope);
			
			Value value = new Value(valorVariable == null ? 0 : valorVariable);
			
			intermediateCodeBuilder.addQuadruple("COPY", var, value);
							
		}
		
		return new DeclaracionVariable(lexema, intermediateCodeBuilder.create());

	}
	
	// declaracionVariable ::= IDENTIFICADOR:identificador listadoIDs:listadoIDs SEMI_COLON:semiColon
	public static DeclaracionVariable produccion_IDENTIFICADOR_listadoIDs_SEMI_COLON(TokenIF identificador, ListadoIDs listadoIDs, TokenIF semiColon) {

		String lexema = identificador.getLexema() + " " + listadoIDs.getLexema() + semiColon.getLexema();

		Consola.log("declaracionVariable[2]: \n" + lexema); 
			
		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);
 		
 		TemporalFactoryIF temporalFactory = new TemporalFactory(scope);

		SymbolTableIF symbolTable = scope.getSymbolTable();
		
		String nombreTipo = identificador.getLexema();
		
		// Comprobar que el tipo usado existe
		if (!Contexto.scopeManager.containsType(nombreTipo)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, tipo no definido: " + nombreTipo);
			
		}
		
		TypeIF tipoVariable = Contexto.scopeManager.searchType(nombreTipo);

		// Comprobar que el tipo es valido (tipo vector)
		if (!(tipoVariable instanceof TypeArray)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, tan solo se pueden definir variables de tipo entero o vector: " + nombreTipo);
			
		}

 		// Encapsular codigo intermedio de las subexpresiones
 		 		
 		intermediateCodeBuilder.addQuadruples(listadoIDs.getIntermediateCode());
		
		for (IdDatos id : listadoIDs.getIds()) {
		
			String nombreVariable = id.getNombre();
			
			// Comprobar si la variable ya se ha definido en el ambito actual
			if (symbolTable.containsSymbol(nombreVariable)) {
				
				Contexto.semanticErrorManager.semanticFatalError("Error, variable ya definida: " + nombreVariable);
					
			} 
				
			// Comprobar que no se ha inicializado con un numero
			if (id.getValor() != null) {
			
				Contexto.semanticErrorManager.semanticFatalError("Error, no se puede asignar un numero a la variable: " + nombreVariable);
			
			}
			
			symbolTable.addSymbol(nombreVariable, new SymbolVariable(scope, nombreVariable, tipoVariable));

			// Crear codigo intermedio para inicializar la variable

			Integer longitudVector = ((TypeArray)tipoVariable).getLongitud();

			Integer tamanoElemento = ((TypeArray)tipoVariable).getTipoElemento().getSize();
			
			Variable var = new Variable(nombreVariable, scope);

	 		TemporalIF temporalPuntero = temporalFactory.create();
	 		
	 		intermediateCodeBuilder.addQuadruple("POINT", temporalPuntero, var);
			
			for(int i = 0; i < longitudVector; i++) {

		 		intermediateCodeBuilder.addQuadruple("STORE", temporalPuntero, new Value(0));

		 		intermediateCodeBuilder.addQuadruple("ADD", temporalPuntero, temporalPuntero, new Value(tamanoElemento));
				
			}
		
		}
		
		return new DeclaracionVariable(lexema, intermediateCodeBuilder.create());
		
	}
}
