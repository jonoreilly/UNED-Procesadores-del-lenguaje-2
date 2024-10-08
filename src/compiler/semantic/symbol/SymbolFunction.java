package compiler.semantic.symbol;

import compiler.semantic.type.TypeFunction;
import es.uned.lsi.compiler.semantic.ScopeIF;

/**
 * Class for SymbolFunction.
 */

public class SymbolFunction
    extends SymbolProcedure
{
      
    /**
     * Constructor for SymbolFunction.
     * @param scope The declaration scope.
     * @param name The symbol name.
     * @param type The symbol type.
     */
    public SymbolFunction (ScopeIF scope, 
                           String name,
                           TypeFunction type)
    {
        
    	super (scope, name, type);
        
    } 
    
}