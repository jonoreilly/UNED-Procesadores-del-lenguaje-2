package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.lexical.TokenIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class TipoFuncion extends NonTerminal {

	private TypeIF tipo;
	
	private String nombreFuncion;
	
	public TipoFuncion(String lexema, TypeIF tipo, String nombreFuncion, List<QuadrupleIF> intermediateCode) {
		
		super(lexema, intermediateCode);
		
		this.tipo = tipo;
		
		this.nombreFuncion = nombreFuncion;
		
	}
	
	public TypeIF getTipo() {
		
		return this.tipo;
	
	}
	
	public String getNombreFuncion() {
		
		return this.nombreFuncion;
		
	}

	// tipoFuncion ::= VACIO:vacio IDENTIFICADOR:identificador OPEN_KEY:openKey
	public static TipoFuncion produccion_VACIO_IDENTIFICADOR_OPEN_KEY(TokenIF vacio, TokenIF identificador, TokenIF openKey) {

		String lexema = vacio.getLexema() + " " + identificador.getLexema() + openKey.getLexema();
    	
    	Consola.log("tipoFuncion[1]: \n" + lexema);

    	TypeIF tipoVacio = Contexto.scopeManager.searchType("vacio");
    	
    	String nombreFuncion = identificador.getLexema();
    	
    	return new TipoFuncion(lexema, tipoVacio, nombreFuncion, new ArrayList<>());
    	
	}

	// tipoFuncion ::= ENTERO:entero IDENTIFICADOR:identificador OPEN_KEY:openKey
	public static TipoFuncion produccion_ENTERO_IDENTIFICADOR_OPEN_KEY(TokenIF entero, TokenIF identificador, TokenIF openKey) {

		String lexema = entero.getLexema() + " " + identificador.getLexema() + openKey.getLexema();
    	
    	Consola.log("tipoFuncion[2]: \n" + lexema);
    	
    	TypeIF tipoEntero = Contexto.scopeManager.searchType("entero");
    	
    	String nombreFuncion = identificador.getLexema();

    	return new TipoFuncion(lexema, tipoEntero, nombreFuncion, new ArrayList<>());
    	
	}
	
}
