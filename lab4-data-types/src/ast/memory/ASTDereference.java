package ast.memory;

import ast.ASTNode;
import ast.ASTNodeAbstract;
import compiler.MainCodeBlock;
import typeError.IllegalOperatorException;
import types.IType;
import types.TypeRef;
import util.Coordinates;
import util.Environment;
import values.IValue;
import values.VCell;

public class ASTDereference extends ASTNodeAbstract
{
    public static final String OPERATOR = "!";

    protected ASTNode reference;

    /**
     * @param reference
     */
    public ASTDereference(ASTNode reference) {
        this.reference = reference;
    }

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e) {
        // TODO Auto-generated method stub
        throw new Error("Not implemented");
    }

    @Override
    public IValue eval(Environment<IValue> e)
    {
        return checkRuntimeType(this.reference.eval(e)).getValue();
    }

    @Override
    public IType typecheck(Environment<IType> e) {
         type = checkType(this.reference.typecheck(e)).getValueType();
         return type;
    }

    protected VCell checkRuntimeType(IValue value)
    {
        boolean checked = value instanceof VCell;

        if (!checked)
            throw new IllegalOperatorException(OPERATOR);

        return (VCell)value;
    }

    protected TypeRef checkType(IType type)
    {
        boolean checked = type instanceof TypeRef;

        if (!checked)
            throw new IllegalOperatorException(OPERATOR);

        return (TypeRef)type;
    }

    
}
