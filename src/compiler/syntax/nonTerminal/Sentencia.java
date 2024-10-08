package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import compiler.utils.Consola;
import compiler.utils.UtilsTiposDevuelve;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class Sentencia extends NonTerminal {

	private List<TypeIF> tiposDevuelve = new ArrayList<>();
		
	public Sentencia(String lexema, List<QuadrupleIF> intermediateCode) {
	
		this(lexema, UtilsTiposDevuelve.ramaSinDevuelve(), intermediateCode);
	
	}
	
	public Sentencia(String lexema, List<TypeIF> tiposDevuelve, List<QuadrupleIF> intermediateCode) {
	
		super(lexema, intermediateCode);
		
		this.tiposDevuelve.addAll(tiposDevuelve);
	
	}

	public List<TypeIF> getTiposDevuelve() {
	
		return new ArrayList<>(this.tiposDevuelve);
	
	}
	
	// sentencia ::= sentenciaDevuelve:sentenciaDevuelve
	public static Sentencia produccion_sentenciaDevuelve(SentenciaDevuelve sentenciaDevuelve) {

		String lexema = sentenciaDevuelve.getLexema();
		
		Consola.log("sentencia[1]: \n" + lexema); 
		
		return new Sentencia(lexema, Arrays.asList(sentenciaDevuelve.getTipoDevuelve()), sentenciaDevuelve.getIntermediateCode()); 
		
	}

	// sentencia ::= sentenciaIncremento:sentenciaIncremento
	public static Sentencia produccion_sentenciaIncremento(SentenciaIncremento sentenciaIncremento) {

		String lexema = sentenciaIncremento.getLexema();
	
		Consola.log("sentencia[2]: \n" + lexema); 
		
		return new Sentencia(lexema, sentenciaIncremento.getIntermediateCode()); 
		
	}

	// sentencia ::= sentenciaAsignacion:sentenciaAsignacion
	public static Sentencia produccion_sentenciaAsignacion(SentenciaAsignacion sentenciaAsignacion) {
	
		String lexema = sentenciaAsignacion.getLexema();
	
		Consola.log("sentencia[3]: \n" + lexema); 
		
		return new Sentencia(lexema, sentenciaAsignacion.getIntermediateCode()); 
		
	}	

	// sentencia ::= sentenciaAsignacionSuma:sentenciaAsignacionSuma
	public static Sentencia produccion_sentenciaAsignacionSuma(SentenciaAsignacionSuma sentenciaAsignacionSuma) {

		String lexema = sentenciaAsignacionSuma.getLexema();
	
		Consola.log("sentencia[4]: \n" + lexema); 
		
		return new Sentencia(lexema, sentenciaAsignacionSuma.getIntermediateCode()); 
		
	}

	// sentencia ::= sentenciaSi:sentenciaSi
	public static Sentencia produccion_sentenciaSi(SentenciaSi sentenciaSi) {

		String lexema = sentenciaSi.getLexema();
	
		Consola.log("sentencia[5]: \n" + lexema); 
		
		return new Sentencia(lexema, sentenciaSi.getTiposDevuelve(), sentenciaSi.getIntermediateCode());  
		
	}

	// sentencia ::= sentenciaAlternativas:sentenciaAlternativas
	public static Sentencia produccion_sentenciaAlternativas(SentenciaAlternativas sentenciaAlternativas) {
	
		String lexema = sentenciaAlternativas.getLexema();
	
		Consola.log("sentencia[6]: \n" + lexema); 
		
		return new Sentencia(lexema, sentenciaAlternativas.getTiposDevuelve(), sentenciaAlternativas.getIntermediateCode()); 
		
	}

	// sentencia ::= sentenciaMientras:sentenciaMientras
	public static Sentencia produccion_sentenciaMientras(SentenciaMientras sentenciaMientras) {
	
		String lexema = sentenciaMientras.getLexema();
	
		Consola.log("sentencia[7]: \n" + lexema); 
		
		return new Sentencia(lexema, sentenciaMientras.getTiposDevuelve(), sentenciaMientras.getIntermediateCode()); 
		
	}

	// sentencia ::= sentenciaLlamadaFuncion:sentenciaLlamadaFuncion
	public static Sentencia produccion_sentenciaLlamadaFuncion(SentenciaLlamadaFuncion sentenciaLlamadaFuncion) {
	
		String lexema = sentenciaLlamadaFuncion.getLexema();
	
		Consola.log("sentencia[8]: \n" + lexema); 
		
		return new Sentencia(lexema, sentenciaLlamadaFuncion.getIntermediateCode()); 
		
	}

	// sentencia ::= sentenciaSalida:sentenciaSalida
	public static Sentencia produccion_sentenciaSalida(SentenciaSalida sentenciaSalida) {

		String lexema = sentenciaSalida.getLexema();
	
		Consola.log("sentencia[9]: \n" + lexema); 
		
		return new Sentencia(lexema, sentenciaSalida.getIntermediateCode()); 
		
	}

	// sentencia ::= bloque:bloque
	public static Sentencia produccion_bloque(Bloque bloque) {
	
		String lexema = bloque.getLexema();
	
		Consola.log("sentencia[10]: \n" + lexema); 
		
		return new Sentencia(lexema, bloque.getTiposDevuelve(), bloque.getIntermediateCode()); 
		
	}
	
}
