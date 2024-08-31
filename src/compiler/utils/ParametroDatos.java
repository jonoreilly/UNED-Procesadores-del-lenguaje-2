package compiler.utils;

import es.uned.lsi.compiler.semantic.type.TypeIF;

public class ParametroDatos {

	private TypeIF tipo;
	
	private String nombre;

	public ParametroDatos(TypeIF tipo, String nombre) {
		
		this.tipo = tipo;
		
		this.nombre = nombre;
	
	}
	
	public TypeIF getTipo() {
		return this.tipo;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
}
