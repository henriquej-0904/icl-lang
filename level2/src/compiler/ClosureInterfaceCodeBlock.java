package compiler;

import java.io.PrintStream;
import java.util.List;
import java.util.function.Function;

import types.IType;
import util.Utils;

public class ClosureInterfaceCodeBlock {
    public static String CODE = ".interface	public %s\n" +
    ".super	java/lang/Object\n" +
    ".method public	abstract apply(%s)%s\n" +
    ".end method";

    
    private List<IType> argTypes;
    private String interfaceName;
    private String  returnTypeString;
    public ClosureInterfaceCodeBlock(List<IType> argTypes , IType returnType){
        this.argTypes = argTypes;
        returnTypeString = returnType.getJvmType();
        interfaceName = generateInterfaceName();
      
    }

    public void dump(PrintStream f) { // dumps code to f
        String argsList = generateArgsList();
     
        f.printf(CODE,interfaceName,argsList,returnTypeString);
        f.flush();
    }

    private String generateInterfaceName(){
        StringBuilder toReturn = new StringBuilder( "closure_interface_");
       return Utils.toStringList(argTypes, (Function<IType,String>)((arg) -> arg.getJvmType()), "_", null, toReturn).append("_" + returnTypeString).toString();
    }

    private String generateArgsList(){
        StringBuilder toReturn = new StringBuilder();
        return Utils.toStringList(argTypes, (Function<IType,String>)((arg) -> arg.getJvmType()), ",", null, toReturn).toString();
    }

    public int getNumArgs(){
        return argTypes.size();
    }
}
