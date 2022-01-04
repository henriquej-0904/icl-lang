package ast;

import types.IType;

public abstract class ASTNodeAbstract implements ASTNode{

    protected IType type;

    @Override
    public IType getType() {
        // TODO Auto-generated method stub
        return type;
    }
    
    @Override
    public String toString() {
        return toString(new StringBuilder()).toString();
    }

    

}
