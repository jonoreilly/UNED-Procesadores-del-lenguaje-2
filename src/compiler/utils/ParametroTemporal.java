package compiler.utils;

import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class ParametroTemporal {

	private TypeIF tipo;
	
	private TemporalIF temporal;

	public ParametroTemporal(TypeIF tipo, TemporalIF temporal) {
		
		this.tipo = tipo;
		
		this.temporal = temporal;
	
	}
	
	public TypeIF getTipo() {
		return this.tipo;
	}
	
	public TemporalIF getTemporal() {
		return this.temporal;
	}

}
