package compiler.semantic.type;

import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeBase;
import es.uned.lsi.compiler.semantic.type.TypeIF;

/**
 * Class for TypeArray.
 */

public class TypeArray
    extends TypeBase
{   
	private int longitud;
	
	private TypeIF tipoElemento;

    public TypeArray (ScopeIF scope, String name, int longitud, TypeIF tipoElemento)
    {
        super (scope, name);
        
        this.longitud = longitud;

        this.tipoElemento = tipoElemento;
    }
    
    /**
     * Returns the size of the type.
     * @return the size of the type.
     */
    @Override
    public int getSize ()
    {
    	return this.tipoElemento.getSize() * this.longitud;
    }

    public int getLongitud() {
    	return this.longitud;
    }
    
    public TypeIF getTipoElemento() {
    	return this.tipoElemento;
    }
}
