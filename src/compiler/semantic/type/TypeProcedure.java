package compiler.semantic.type;

import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeBase;
import es.uned.lsi.compiler.semantic.type.TypeIF;

/**
 * Class for TypeProcedure.
 */

public class TypeProcedure
    extends TypeBase
{   

	private TypeIF tipoRetorno;
	
    public TypeProcedure (ScopeIF scope, String name, TypeIF tipoRetorno)
    {
        super (scope, name);
        
        this.tipoRetorno = tipoRetorno;
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
    
    public TypeIF getTipoRetorno() {
    	return this.tipoRetorno;
    }
}