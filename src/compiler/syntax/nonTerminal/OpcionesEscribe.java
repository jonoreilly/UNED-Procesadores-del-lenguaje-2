package compiler.syntax.nonTerminal;

import java.util.ArrayList;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import es.uned.lsi.compiler.intermediate.LabelFactory;
import es.uned.lsi.compiler.intermediate.LabelFactoryIF;
import es.uned.lsi.compiler.intermediate.LabelIF;
import es.uned.lsi.compiler.lexical.TokenIF;

public class OpcionesEscribe extends NonTerminal {

	/** Null si no tiene parametros */
	private LabelIF label;
	
	public OpcionesEscribe(String lexema, LabelIF label) {
		
		super(lexema, new ArrayList<>());
		
		this.label = label;
		
	}

	/** @return Null si no tiene parametros */
	public LabelIF getLabel() {
		
		return this.label;
		
	}
	
	// opcionesEscribe ::= epsilon:epsilon
	public static OpcionesEscribe produccion_epsilon(Epsilon epsilon) {
		
		String lexema = epsilon.getLexema();
	
		Consola.log("opcionesEscribe[1]: \n" + lexema);
				
		return new OpcionesEscribe(lexema, null);
		
	}

	// opcionesEscribe ::= STRING:string
	public static OpcionesEscribe produccion_STRING(TokenIF string) {
		
		String lexema = string.getLexema();
	
		Consola.log("opcionesEscribe[2]: \n" + lexema);
 		
 		LabelFactoryIF labelFactory = new LabelFactory();

 		LabelIF label = labelFactory.create();
 		
 		String cadena = lexema.substring(1, lexema.length()-2);

 		Contexto.listaCadenas.addCadena(label, cadena);
				
		return new OpcionesEscribe(lexema, label);
		
	}
	
}
