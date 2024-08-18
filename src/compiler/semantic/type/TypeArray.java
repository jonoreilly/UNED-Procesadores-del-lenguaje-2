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
	private int tamanio;
	
	private TypeIF tipoElemento;

    public TypeArray (ScopeIF scope, String name, int tamanio, TypeIF tipoElemento)
    {
        super (scope, name);
        
        this.tamanio = tamanio;

        this.tipoElemento = tipoElemento;
    }
    
    /**
     * Returns the size of the type.
     * @return the size of the type.
     */
    @Override
    public int getSize ()
    {
    	return this.tipoElemento.getSize() * this.tamanio;
    }
    
    public TypeIF getTipoElemento() {
    	return this.tipoElemento;
    }
}
