package compiler.utils;

public class IdDatos {

	private String nombre;
	
	private Integer valor;
	
	public IdDatos(String nombre, Integer valor) {
				
		this.nombre = nombre;

		this.valor = valor;
		
	}
	
	public String getNombre() {
		
		return this.nombre;
		
	}
	
	public Integer getValor() {
		
		return this.valor;
		
	}
	
}
