package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.utils.Consola;
import compiler.utils.Contexto;
import compiler.utils.UtilsTiposDevuelve;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.lsi.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class ListadoSentencias extends NonTerminal {

	private List<TypeIF> tiposDevuelve = new ArrayList<>();
	
	public ListadoSentencias(String lexema, List<TypeIF> tiposDevuelve, List<QuadrupleIF> intermediateCode) {
	
		super(lexema, intermediateCode);
		
		this.tiposDevuelve.addAll(tiposDevuelve);

	}
	
	public List<TypeIF> getTiposDevuelve() {
		
		return new ArrayList<>(this.tiposDevuelve);
	
	}
	
	// listadoSentencias ::= listadoSentencias:listadoSentencias sentencia:sentencia
	public static ListadoSentencias produccion_listadoSentencias_sentencia(ListadoSentencias listadoSentencias, Sentencia sentencia) {

		String lexema = listadoSentencias.getLexema() + "\n" + sentencia.getLexema();

		Consola.log("listadoSentencias[1]: \n" + lexema);
 		
 		ScopeIF scope = Contexto.scopeManager.getCurrentScope();
 		
 		IntermediateCodeBuilderIF intermediateCodeBuilder = new IntermediateCodeBuilder(scope);

		List<TypeIF> tiposDevuelveListadoSentencias = listadoSentencias.getTiposDevuelve();
		
		List<TypeIF> tiposDevuelveSentencia = sentencia.getTiposDevuelve();
		
		// Comprobar si la sentencia es inalcanzable porque todas las ramas anteriores devuelven
		if (UtilsTiposDevuelve.todasLasRamasDevuelven(tiposDevuelveListadoSentencias)) {
		
			Contexto.semanticErrorManager.semanticInfo("Aviso, se ha encontrado codigo inalcanzable");
		
			return new ListadoSentencias(lexema, tiposDevuelveListadoSentencias, listadoSentencias.getIntermediateCode());
		
		} 

		List<TypeIF> tiposDevuelveConsolidados = UtilsTiposDevuelve.consolidarRamas(tiposDevuelveListadoSentencias, tiposDevuelveSentencia);

		// Encapsular codigo intermedio de las subexpresiones
 		 		
 		intermediateCodeBuilder.addQuadruples(listadoSentencias.getIntermediateCode());

 		intermediateCodeBuilder.addQuadruples(sentencia.getIntermediateCode());
 		
		return new ListadoSentencias(lexema, tiposDevuelveConsolidados, intermediateCodeBuilder.create());
		
	}

	// listadoSentencias ::= sentencia:sentencia
	public static ListadoSentencias produccion_sentencia(Sentencia sentencia) {
		
		String lexema = sentencia.getLexema();
 
		Consola.log("listadoSentencias[2]: \n" + lexema);
 
 		return new ListadoSentencias(lexema, sentencia.getTiposDevuelve(), sentencia.getIntermediateCode());
		
	}
	
}
