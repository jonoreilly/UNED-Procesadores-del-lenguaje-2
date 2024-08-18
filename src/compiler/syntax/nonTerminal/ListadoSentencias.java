package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import compiler.utils.UtilsTiposDevuelve;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class ListadoSentencias extends NonTerminal {

	private List<TypeIF> tiposDevuelve = new ArrayList<>();
	
	public ListadoSentencias(String lexema, List<TypeIF> tiposDevuelve) {
	
		super(lexema);
		
		this.tiposDevuelve.addAll(tiposDevuelve);

	}
	
	public List<TypeIF> getTiposDevuelve() {
		
		return new ArrayList<>(this.tiposDevuelve);
	
	}
	
	// listadoSentencias ::= listadoSentencias:listadoSentencias sentencia:sentencia
	public static ListadoSentencias produccion_listadoSentencias_sentencia(ListadoSentencias listadoSentencias, Sentencia sentencia) {

		String lexema = listadoSentencias.getLexema() + "\n" + sentencia.getLexema();

		Consola.log("listadoSentencias[1]: \n" + lexema);

		List<TypeIF> tiposDevuelveListadoSentencias = listadoSentencias.getTiposDevuelve();
		
		List<TypeIF> tiposDevuelveSentencia = sentencia.getTiposDevuelve();
		
		// Comprobar si la sentencia es inalcanzable porque todas las ramas anteriores devuelven
		if (UtilsTiposDevuelve.todasLasRamasDevuelven(tiposDevuelveListadoSentencias)) {
		
			Contexto.semanticErrorManager.semanticInfo("Aviso, se ha encontrado codigo inalcanzable");
		
			return new ListadoSentencias(lexema, tiposDevuelveListadoSentencias);
		
		} 

		List<TypeIF> tiposDevuelveConsolidados = UtilsTiposDevuelve.consolidarRamas(tiposDevuelveListadoSentencias, tiposDevuelveSentencia);

		return new ListadoSentencias(lexema, tiposDevuelveConsolidados);
		
	}

	// listadoSentencias ::= sentencia:sentencia
	public static ListadoSentencias produccion_sentencia(Sentencia sentencia) {
		
		String lexema = sentencia.getLexema();
 
		Consola.log("listadoSentencias[2]: \n" + lexema);
 
 		return new ListadoSentencias(lexema, sentencia.getTiposDevuelve());
		
	}
	
}
