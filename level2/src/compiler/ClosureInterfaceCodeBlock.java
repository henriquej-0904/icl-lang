package compiler;

import java.io.PrintStream;
import java.util.function.Consumer;

import types.IType;
import types.TypeFunction;
import util.Utils;

public class ClosureInterfaceCodeBlock {
    public static String CODE = ".interface	public %s\n" +
    ".super	java/lang/Object\n" +
    ".method public	abstract %s\n" +
    ".end method";

    
    public final TypeFunction typeFunction;
    public final String interfaceName;
    public final String returnTypeString;

    private String applySignature;

    public ClosureInterfaceCodeBlock(TypeFunction typeFunction){
        this.typeFunction = typeFunction;
        returnTypeString = getType(typeFunction.getReturnType());
        interfaceName = generateInterfaceName();
      
    }

    public void dump(PrintStream f) { // dumps code to f     
        f.printf(CODE,interfaceName, getApplySignature());
        f.flush();
    }

    private String generateInterfaceName(){
        StringBuilder toReturn = new StringBuilder( "closure_interface_");
       return Utils.toStringList(typeFunction.getArgs(), (Consumer<IType>)((arg) -> getType(arg))
        , "_", null, toReturn).append("_" + returnTypeString).toString();
    }

    private String generateArgsList(){
        StringBuilder toReturn = new StringBuilder();
        return Utils.toStringList(typeFunction.getArgs(), (Consumer<IType>)((arg) -> getType(arg)),
        ",", Utils.DEFAULT_DELIMITERS, toReturn).toString();
    }

    public int getNumArgs(){
        return typeFunction.getArgs().size();
    }

    public String getApplySignature()
    {
        if (applySignature != null)
            return applySignature;
        
        StringBuilder builder = new StringBuilder("apply");
        builder.append(generateArgsList());
        builder.append(returnTypeString);

        applySignature = builder.toString();

        return applySignature;
    }

    public static String getType(IType type)
    {
        if (type instanceof TypeFunction)
                return "Ljava/lang/Object;";
 
        return type.getJvmType();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof ClosureInterfaceCodeBlock))
            return false;

        ClosureInterfaceCodeBlock other = (ClosureInterfaceCodeBlock)obj;
        return interfaceName.equals(other.interfaceName);
    }

    @Override
    public int hashCode() {
        return interfaceName.hashCode();
    }

}
