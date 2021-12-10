package ast;

import types.IType;
import types.TypeVoid;

public abstract class ASTNodeAbstract implements ASTNode{

    protected IType type = TypeVoid.TYPE;
    @Override
    public IType getType() {
        // TODO Auto-generated method stub
        return type;
    }
    
    

}
