package types;


import java.util.List;

public class TypeFunction implements IType{
List<IType> args;
IType returnType;

public TypeFunction(List<IType> args, IType returnType){
    this.args = args;
    this.returnType = returnType;
}
    @Override
    public String show() {
        // TODO Auto-generated method stub
        String toReturn = null;
        for (IType iType : args) {
            toReturn += iType.show() + ",";
        }
        return "( " +  toReturn + " ) -> " +  returnType.show();
    }

    @Override
    public String getJvmType() {
        // TODO Auto-generated method stub
        return "L" + this + ";";
    }

    @Override
    public String toString(){
        String toReturn  = "fun_";
        for (IType iType : args) {
            toReturn += iType.toString() + "_";
        }

        toReturn += returnType.toString();
        
    return toReturn;
    }

    @Override
    public int hashCode(){
        return args.hashCode() ^ returnType.hashCode();
    }
    
}
