package ast.bool;

import ast.ASTNode;
import compiler.MainCodeBlock;
import typeError.IllegalOperatorException;
import types.IType;
import types.primitves.TypeBool;
import util.Coordinates;
import util.Environment;
import values.IValue;
import values.primitive.VBool;

public class ASTLogicalBinaryOperation implements ASTNode
{
    protected final ASTNode left, rigth;

    protected final LogicalBinaryOperator operator;

    /**
     * @param left
     * @param rigth
     */
    public ASTLogicalBinaryOperation(ASTNode left, ASTNode rigth, LogicalBinaryOperator operator) {
        this.left = left;
        this.rigth = rigth;
        this.operator = operator;
    }

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e)
    {
        throw new Error("Not implemented");    
    }

    @Override
    public IValue eval(Environment<IValue> e)
    {
        VBool val1 = checkRuntimeType(this.left.eval(e));
        VBool val2 = checkRuntimeType(this.rigth.eval(e));

        IValue result = null;
        switch(this.operator)
        {
            case AND:
                result = new VBool(val1.getValue() && val2.getValue());
                break;
            case OR:
                result = new VBool(val1.getValue() || val2.getValue());
                break;
        }

        return result;
    }

    @Override
    public IType typecheck(Environment<IType> e) {
        checkType(this.left.typecheck(e));
        return checkType(this.rigth.typecheck(e));
    }

    protected VBool checkRuntimeType(IValue val)
    {
        boolean checked = val instanceof VBool;
        if (!checked)
            throw new IllegalOperatorException(this.operator.getOperator());

        return (VBool)val;
    }

    protected IType checkType(IType type)
    {
        boolean checked = type instanceof TypeBool;
        if (!checked)
            throw new IllegalOperatorException(this.operator.getOperator());

        return type;
    }

}
