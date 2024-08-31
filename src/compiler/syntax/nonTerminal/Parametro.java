package compiler.syntax.nonTerminal;

import java.util.ArrayList;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class Parametro extends NonTerminal {

	private TypeIF tipo;
	
	private String nombre;
	
	public Parametro(String lexema, TypeIF tipo, String nombre) {
		super(lexema, new ArrayList<>());
		
		this.tipo = tipo;
		
		this.nombre = nombre;
	}
	
	public TypeIF getTipo() {
		return this.tipo;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	// parametro ::= ENTERO:entero IDENTIFICADOR:identificador
	public static Parametro produccion_ENTERO_IDENTIFICADOR(TokenIF entero, TokenIF identificador) {

		String lexema = entero.getLexema() + " " + identificador.getLexema(); 

    	Consola.log("parametro[1]: \n" + lexema);
		
		TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
		
		return new Parametro(lexema, tipoEntero, identificador.getLexema());
		
	}

	// parametro ::= IDENTIFICADOR:identificador1 IDENTIFICADOR:identificador2 
	public static Parametro produccion_IDENTIFICADOR_IDENTIFICADOR(TokenIF identificador1, TokenIF identificador2) {

		String lexema = identificador1.getLexema() + " " + identificador2.getLexema(); 
	
    	Consola.log("parametro[2]: \n" + lexema);
    	
    	String nombreTipo = identificador1.getLexema();
    	
    	String nombreParametro = identificador2.getLexema();
    	
    	// Comprobar que el tipo esta definido
    	if (!Contexto.scopeManager.containsType(nombreTipo)) {
		
			Contexto.semanticErrorManager.semanticFatalError("Error, tipo no definido: " + nombreTipo);
    		
    	}
		
		TypeIF tipoParametro = Contexto.scopeManager.searchType(nombreTipo);
		
		return new Parametro(lexema, tipoParametro, nombreParametro);
		
	}
	
}
