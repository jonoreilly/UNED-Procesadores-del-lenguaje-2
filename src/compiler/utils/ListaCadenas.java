package compiler.utils;

import java.util.ArrayList;
import java.util.List;

import compiler.intermediate.Value;
import es.uned.lsi.compiler.intermediate.LabelIF;
import es.uned.lsi.compiler.intermediate.Quadruple;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;

public class ListaCadenas {

	private List<QuadrupleIF> intermediateCode = new ArrayList<>();
	 
	public void addCadena(LabelIF label, String cadena) {

		QuadrupleIF cuadrupla = new Quadruple("CADENA", label, new Value(cadena));
		
		this.intermediateCode.add(cuadrupla);
		
	}
	
	public List<QuadrupleIF> getIntermediateCode() {
		
		return new ArrayList<>(this.intermediateCode);
		
	}
	
}
