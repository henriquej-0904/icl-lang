package ast.memory;

import ast.ASTNode;
import compiler.MainCodeBlock;
import typeError.TypeErrorException;
import types.IType;
import types.TypeRef;
import types.TypeVoid;
import util.Coordinates;
import util.Environment;
import values.IValue;
import values.VCell;
import values.VVoid;

public class ASTNew implements ASTNode
{
    protected ASTNode val;

    /**
     * @param val
     */
    public ASTNew(ASTNode val) {
        this.val = val;
    }

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e) {
        // TODO Auto-generated method stub
        throw new Error("Not implemented");
    }

    @Override
    public IValue eval(Environment<IValue> e) {
        return new VCell(checkRuntimeType(this.val.eval(e)));
    }

    @Override
    public IType typecheck(Environment<IType> e) {
        return new TypeRef(checkType(this.val.typecheck(e)));
    }

    protected IValue checkRuntimeType(IValue value)
    {
        boolean voidValue = value instanceof VVoid;

        if (voidValue)
            throw new TypeErrorException("Incompatible type. Cannot create reference to 'void'");

        return value;
    }

    protected IType checkType(IType type)
    {
        boolean voidType = type instanceof TypeVoid;

        if (voidType)
            throw new TypeErrorException("Incompatible type. Cannot create reference to 'void'");

        return type;
    }
}
