package compiler;

import java.io.PrintStream;

import types.IType;
import types.primitves.TypePrimitive;

public class RefCodeBlock {
    
    private static final String CLASS_NAME = "ref_of_%s";

    private static final String CODE =

    ".class %s\n" +
    ".super java/lang/Object\n" +
    ".field public v %s\n" +

    ".method public <init>()V\n" +

    "  aload_0\n" +
    "  invokenonvirtual java/lang/Object/<init>()V\n" +
    "  return\n\n" +
    ".end method";


    private String className;

    private String valueFieldType;
    public RefCodeBlock(IType valueType)
    {

        className = String.format(CLASS_NAME, valueType);
        if(valueType instanceof TypePrimitive){
            valueFieldType  = "I";
        }
        else{
            valueFieldType = "L" + valueType + ";";
        } 
       
    }
 

    /**
     * @return the className
     */
    public String getClassName() {
        return className;
    }

    public String getValueFieldType(){
        return valueFieldType;
    }


    public void dump(PrintStream f) { // dumps code to f

        
            f.printf(CODE,className,valueFieldType);
        
        

        f.flush();
    }
}

