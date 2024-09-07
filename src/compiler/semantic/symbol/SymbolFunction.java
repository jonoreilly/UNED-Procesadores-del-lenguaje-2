package compiler.semantic.symbol;

import java.util.ArrayList;
import java.util.List;

import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

/**
 * Class for SymbolFunction.
 */

public class SymbolFunction
    extends SymbolProcedure
{
	
	private List<TypeIF> tiposParametros = new ArrayList<>();
      
    /**
     * Constructor for SymbolFunction.
     * @param scope The declaration scope.
     * @param name The symbol name.
     * @param type The symbol type.
     */
    public SymbolFunction (ScopeIF scope, 
                           String name,
                           TypeIF type,
                           List<TypeIF> tiposParametros)
    {
        
    	super (scope, name, type);
        
        this.tiposParametros.addAll(tiposParametros);
        
    } 
    
    public List<TypeIF> getTiposParametros() {
    	
    	return new ArrayList<>(this.tiposParametros);
    			
    }
}