package compiler.semantic.type;

import java.util.ArrayList;
import java.util.List;

import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

/**
 * Class for TypeFunction.
 */

// TODO: Student work
//       Include properties to characterize function declarations

public class TypeFunction
    extends TypeProcedure
{   
	private List<TypeIF> parametros = new ArrayList<>();
    
    public TypeFunction (ScopeIF scope, String name, TypeIF tipoRetorno, List<TypeIF> parametros)
    {
        super (scope, name, tipoRetorno);
        
        this.parametros.addAll(parametros);
    }
    
    /**
     * Returns the size of the type.
     * @return the size of the type.
     */
    @Override
    public int getSize ()
    {
        // TODO: Student work
        return 1;
    }
    
    public List<TypeIF> getParametros() {
    	return new ArrayList<>(this.parametros);
    }
}