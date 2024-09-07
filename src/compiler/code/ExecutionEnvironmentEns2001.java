package compiler.code;

import java.util.Arrays;
import java.util.List;

import compiler.intermediate.Label;
import compiler.intermediate.Temporal;
import compiler.intermediate.Value;
import compiler.intermediate.Variable;
import compiler.semantic.symbol.SymbolVariable;
import compiler.semantic.type.TypeSimple;

import es.uned.lsi.compiler.code.ExecutionEnvironmentIF;
import es.uned.lsi.compiler.code.MemoryDescriptorIF;
import es.uned.lsi.compiler.code.RegisterDescriptorIF;
import es.uned.lsi.compiler.intermediate.OperandIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolTableIF;

/**
 * Class for the ENS2001 Execution environment.
 */

public class ExecutionEnvironmentEns2001 
    implements ExecutionEnvironmentIF
{    
    private final static int      MAX_ADDRESS = 65535; 
    private final static String[] REGISTERS   = {
       ".PC", ".SP", ".SR", ".IX", ".IY", ".A", 
       ".R0", ".R1", ".R2", ".R3", ".R4", 
       ".R5", ".R6", ".R7", ".R8", ".R9"
    };
    
    private RegisterDescriptorIF registerDescriptor;
    private MemoryDescriptorIF   memoryDescriptor;
    
    /**
     * Constructor for ENS2001Environment.
     */
    public ExecutionEnvironmentEns2001 ()
    {       
        super ();
    }
    
    /**
     * Returns the size of the type within the architecture.
     * @return the size of the type within the architecture.
     */
    @Override
    public final int getTypeSize (TypeSimple type)
    {      
        return 1;  
    }
    
    /**
     * Returns the registers.
     * @return the registers.
     */
    @Override
    public final List<String> getRegisters ()
    {
        return Arrays.asList (REGISTERS);
    }
    
    /**
     * Returns the memory size.
     * @return the memory size.
     */
    @Override
    public final int getMemorySize ()
    {
        return MAX_ADDRESS;
    }
           
    /**
     * Returns the registerDescriptor.
     * @return Returns the registerDescriptor.
     */
    @Override
    public final RegisterDescriptorIF getRegisterDescriptor ()
    {
        return registerDescriptor;
    }

    /**
     * Returns the memoryDescriptor.
     * @return Returns the memoryDescriptor.
     */
    @Override
    public final MemoryDescriptorIF getMemoryDescriptor ()
    {
        return memoryDescriptor;
    }

    /**
     * Translate a quadruple into a set of final code instructions. 
     * @param cuadruple The quadruple to be translated.
     * @return a quadruple into a set of final code instructions. 
     */
    @Override
    public final String translate (QuadrupleIF quadruple)
    {      

    	OperandIF res = quadruple.getResult();
    	OperandIF op1 = quadruple.getFirstOperand();
    	OperandIF op2 = quadruple.getSecondOperand();

		String resVal = this.getValorOperando(res);
		String op1Val = this.getValorOperando(op1);
		String op2Val = this.getValorOperando(op2);

		Integer resAdd = this.getDireccionOperando(res);
		Integer op1Add = this.getDireccionOperando(op1);
		Integer op2Add = this.getDireccionOperando(op2);
		
		String codigoFinal = "; " + quadruple.toString() + "\n";
    	
		switch(quadruple.getOperation()) {
			
			// COPY X:Temporal/Variable Y:Temporal/Varible/Value
			// Almacenar en X el valor contenido en Y
			case "COPY": {
							
				codigoFinal += "MOVE " + op1Val + ", " + resVal + " \n";
				
				break;
							
			}
			
			// POINT X:Temporal/Variable Y:Temporal/Varible
			// Almacenar en X la direccion de Y
			case "POINT": {
				
				codigoFinal += "MOVE #" + op1Add + ", " + resVal + " \n";
				
				break;
				
			}
			
			// FIND X:Temporal/Variable Y:Temporal/Varible
			// Almacenar en X el valor almacenado en la direccion a la que apunta el valor conteindo en Y
			case "FIND": {

				codigoFinal += "MOVE " + op1Val + ", .R1 \n";
				
				codigoFinal += "MOVE [.R1], " + resVal + " \n";
				
				break;
				
			}
			
			// STORE X:Temporal/Variable Y:Temporal/Varible/Value
			// Almacenar en la direccion a la que apunta el valor contenido en X el valor contenido en Y
			case "STORE": {

				codigoFinal += "MOVE " + resVal + ", .R1 \n";
				
				codigoFinal += "MOVE " + op1Val + ", [.R1] \n";
				
				break;
				
			}
			
			// RETURN X:Temporal/Variable/Value
			// Retorna de la funcion con el valor almacenado en X
			case "RETURN": {
				
				// TODO: implementar retorno de valores correctamente
				codigoFinal += "HALT \n";
				
				break;
				
			}
			
			// ADD X:Temporal/Variable Y:Temporal/Variable/Value Z:Temporal/Variable/Value
			// Almacenar en X el resultado de sumar los valores contenidos en Y y Z
			case "ADD": {
				
				codigoFinal += "ADD " + op1Val + ", " + op2Val + "\n";
				
				codigoFinal += "MOVE .A, " + resVal + " \n";
				
				break;
				
			}
			
			// MUL X:Temporal/Variable Y:Temporal/Variable/Value Z:Temporal/Variable/Value
			// Almacenar en X el resultado de multiplicar los valores contenidos en Y y Z
			case "MUL": {
				
				codigoFinal += "MUL " + op1Val + ", " + op2Val + "\n";
				
				codigoFinal += "MOVE .A, " + resVal + " \n";
				
				break;
				
			}
			
			// GR X:Temporal/Variable Y:Temporal/Variable/Value Z:Temporal/Variable/Value
			// Almacenar en X un 1 si el valor de Y es mayor que Z, si no almacena un 0
			case "GR": {
				
				codigoFinal += "CMP " + op2Val + ", " + op1Val + "\n"; // op2:Z - op1:Y
				
				codigoFinal += "BN $5 \n"; // Si negativo (Z < Y), salta al bloque verdadero

				// Bloque Y > Z = falso
				codigoFinal +="MOVE #0, " + resVal + "\n";
				
				codigoFinal += "BR $3 \n"; // Saltar a despues del bloque verdadero

				// Bloque Y > Z = verdadero
				codigoFinal += "MOVE #1, " + resVal + " \n";
						
				
				break;
				
			}
			
			// LS X:Temporal/Variable Y:Temporal/Variable/Value Z:Temporal/Variable/Value
			// Almacenar en X un 1 si el valor de Y es menor que Z, si no almacena un 0
			case "LS": {
				
				codigoFinal += "CMP " + op1Val + ", " + op2Val + "\n"; // op1:Y - op2:Z
						
				codigoFinal += "BN $5 \n"; // Si negativo (Y < Z), salta al bloque verdadero
						
				// Bloque Y < Z = falso
				codigoFinal += "MOVE #0, " + resVal + " \n" ; // Resultado = 0
						
				codigoFinal += "BR $3 \n"; // Saltar a despues del bloque verdadero
						
				// Bloque Y < Z = verdadero
				codigoFinal += "MOVE #1, " + resVal + "\n";
				
				break;
				
			}
			
			// EQ X:Temporal/Variable Y:Temporal/Variable/Value Z:Temporal/Variable/Value
			// Almacenar en X un 1 si el valor de Y es igual que Z, si no almacena un 0
			case "EQ": {
				
				codigoFinal += "CMP " + op1Val + ", " + op2Val + "\n"; // op1 - op2
						
				codigoFinal += "BZ $5 \n"; // Si cero, salta al bloque verdadero
						
				// Bloque Y == Z = falso
				codigoFinal += "MOVE #0, " + resVal + " \n"; // Resultado = 0
						
				codigoFinal += "BR $3 \n"; // Saltar a despues del bloque verdadero
						
				// Bloque Y == Z verdadero
				codigoFinal += "MOVE #1, " + resVal + "\n";
				
				break;
				
			}
			
			// BR L:Label
			// Salta a la etiqueta L
			case "BR": {
				
				codigoFinal += "BR /" + resVal + "\n";
				
				break;
				
			}
			
			// BRT X:Temporal/Variable/Value L:Label
			// Si X > 0, salta a L
			case "BRT": {
				
				codigoFinal += "CMP " + resVal + ", #1 \n"; // res - 1 
						
				codigoFinal += "BP /" + op1Val + "\n"; // Si positivo (res - 1 >= 0), salta a la etiqueta
				
				break;
				
			}
			
			// BRF X:Temporal/Variable/Value L:Label
			// Si X < 1, salta a L
			case "BRF": {
				
				codigoFinal += "CMP #0, " + resVal + " \n"; // 0 - res
						
				codigoFinal += "BP /" + op1Val + "\n"; // Si positivo (0 - res >= 0), salta a la etiqueta
				
				break;
						
			}
			
			// INL L:Label
			// Inserta la etiqueta L
			case "INL": {
				
				codigoFinal += resVal + " : NOP \n";
				
				break;
						
			}
			
			// PRINT_STR L:Label
			// Muestra por pantalla la cadena L
			case "PRINT_STR": {
				
				codigoFinal += "WRSTR /" + resVal + "\n";
				
				codigoFinal += "WRCHAR #10 \n";
				
				break;
						
			}
			
			// PRINT_INT X:Temporal/Variable/Value
			// Muestra por pantalla el valor de X
			case "PRINT_INT": {
				
				codigoFinal += "WRINT " + resVal + "\n";
				
				codigoFinal += "WRCHAR #10 \n";
				
				break;
						
			}
			
			// PRINT_LINE
			// Crea un salto de linea en pantalla
			case "PRINT_LINE": {
				
				codigoFinal += "WRCHAR #10 \n";
				
				break;
						
			}
			
			// PRINT_LINE
			// Crea un salto de linea en pantalla
			case "CADENA": {
				
				codigoFinal += resVal + " : DATA " + getCadena(op1) + "\n";
				
				break;
						
			}
			
			// SET_STACK_POINTER X:Value
			// Pone el StackPointer al valor de X
			case "SET_STACK_POINTER": {
				
				codigoFinal += "MOVE " + resVal + ", .SP \n";
				
				break;
						
			}
		
		
		}
		
		for (String linea : codigoFinal.split("\n")) {
			System.out.println(linea);
		}

		return codigoFinal;
		
    }
    
    private String getValorOperando(OperandIF operando) {
    	
    	if (operando instanceof Variable || operando instanceof Temporal) {
    		
    		return "/" + this.getDireccionOperando(operando);

    	}

    	if (operando instanceof Value) {
    		
    		return "#" + ((Value)operando).getValue();
    		
    	}

    	if (operando instanceof Label) {
    		
    		return ((Label)operando).getName();
    		
    	}

    	return null;
    	
    }
    
    private Integer getDireccionOperando(OperandIF operando) {

    	if (operando instanceof Variable) {

    		Variable variable = ((Variable)operando);
    		
    		ScopeIF scope = variable.getScope();
    		
    		SymbolTableIF symbolTable = scope.getSymbolTable();
    		
    		SymbolIF simbolo = symbolTable.getSymbol(variable.getName());
    		
    		if (simbolo instanceof SymbolVariable) {

        		return ((SymbolVariable)simbolo).getAddress();
        		
    		}
    		
    	}

    	if (operando instanceof Temporal) {
    		
    		return ((Temporal)operando).getAddress();
    		
    	}

    	return null;
    	
    }
    
    private Object getCadena(OperandIF operando) {

    	if (operando instanceof Value) {
    		
    		return "\"" + ((Value)operando).getValue() + "\"";
    		
    	}

    	return null;
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
