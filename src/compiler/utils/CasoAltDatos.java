package compiler.utils;

import java.util.ArrayList;
import java.util.List;

import es.uned.lsi.compiler.intermediate.QuadrupleIF;

public class CasoAltDatos {

	private Integer valorCondicion;
	
	private List<QuadrupleIF> intermediateCode = new ArrayList<>();
	
	public CasoAltDatos(Integer valorCondicion, List<QuadrupleIF> intermediateCode) {
		
		this.valorCondicion = valorCondicion;
		
		this.intermediateCode = intermediateCode;
		
	}
	
	public Integer getValorCondicion() {
		
		return this.valorCondicion;
		
	}
	
	public List<QuadrupleIF> getIntermediateCode() {
		
		return new ArrayList<>(this.intermediateCode);
		
	}
	
}
