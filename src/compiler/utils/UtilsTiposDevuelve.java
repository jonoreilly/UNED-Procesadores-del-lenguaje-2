package compiler.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.uned.lsi.compiler.semantic.type.TypeIF;

public class UtilsTiposDevuelve {

	private static final TypeIF TIPO_RAMA_SIN_DEVUELVE = null;

	public static List<TypeIF> ramaSinDevuelve() {
		
		return Arrays.asList((TypeIF)null);
	
	}
		
	/**
	 * Unifica los posibles tipos devueltos por ramas paralelas.
	 * Las ramas son independientes y el orden no importa.
	 * Puede contener el tipo de rama sin devuelve
	 */
	@SafeVarargs
	public static List<TypeIF> unirRamas(List<TypeIF>... ramas) {

		List<TypeIF> resultado = new ArrayList<>();

		for (List<TypeIF> rama : ramas) {

			for (TypeIF tipo : rama) {
			
				if (!contiene(resultado, tipo)) {
					
					resultado.add(tipo);
					
				}
			
			}
			
		}
		
		return resultado;
		
	}

	/**
	 * Unifica los posibles tipos devueltos por ramas secuenciales.
	 * Al alcanzar una rama terminal (todas sus subramas devuelven) la ejecucion se detiene, y se elimina el tipo de rama sin devuelve.
	 * Puede contener el tipo de rama sin devuelve, si no se ha encontrado una rama terminal
	 */
	@SafeVarargs
	public static List<TypeIF> consolidarRamas(List<TypeIF>... ramas) {
		
		List<TypeIF> resultado = new ArrayList<>();

		for (List<TypeIF> rama : ramas) {

			for (TypeIF tipo : rama) {
			
				if (!contiene(resultado, tipo)) {
					
					resultado.add(tipo);
					
				}
			
			}
			
			if (todasLasRamasDevuelven(rama)) {
				
				resultado.removeIf((tipo) -> tipo == null);
				
				break;
				
			}
			
		}
		
		return resultado;
		
	}
	
	
	public static boolean contiene(List<TypeIF> lista, TypeIF tipo) {
		
		for (TypeIF t : lista) {
			
			if (t == null && tipo == null) {
				return true;
			}
			
			if (t != null && tipo != null && tipo.equals(t)) {
				return true;
			}
			
		}
		
		return false;
	
	}
	
	public static boolean tieneRamasSinResolver(List<TypeIF> lista) {
		
		return contiene(lista, TIPO_RAMA_SIN_DEVUELVE);
		
	}

	public static boolean todasLasRamasDevuelven(List<TypeIF> lista) {
		
		return !tieneRamasSinResolver(lista);
		
	}
	
}
