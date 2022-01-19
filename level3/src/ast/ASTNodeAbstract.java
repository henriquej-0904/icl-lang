package ast;

import types.IType;
import types.TypeNull;
import util.Utils;
import values.IValue;

public abstract class ASTNodeAbstract implements ASTNode
{
    protected IType type;

    protected ASTNodeAbstract()
    {
        this.type = TypeNull.TYPE;
    }

    @Override
    public IType getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return toString(new StringBuilder()).toString();
    }

    /**
     * A function to check if a value can be used.
     * @param value - The value to check.
     * @return - The value if it is valid.
     */
    protected IValue check(IValue value)
    {
        return Utils.requireNonNull(value);
    }

    /**
     * A function to check if a type is valid.
     * @param value - The type to check.
     * @return - The type if it is valid.
     */
    protected IType check(IType type)
    {
        return Utils.requireNonNull(type);
    }
}