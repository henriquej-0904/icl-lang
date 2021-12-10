package ast.number;

import ast.ASTNode;
import ast.ASTNodeAbstract;
import compiler.MainCodeBlock;
import typeError.IllegalOperatorException;
import types.IType;
import types.primitves.TypeBool;
import types.primitves.TypeInt;
import util.Coordinates;
import util.Environment;
import values.IValue;
import values.primitive.VBool;
import values.primitive.VInt;

public class ASTNumberRelationalBinaryOperation extends ASTNodeAbstract
{
    protected final ASTNode left, rigth;

    protected final NumberRelationalBinaryOperator operator;

    /**
     * @param left
     * @param rigth
     */
    public ASTNumberRelationalBinaryOperation(ASTNode left, ASTNode rigth, NumberRelationalBinaryOperator operator) {
        this.left = left;
        this.rigth = rigth;
        this.operator = operator;
        type = TypeBool.TYPE;
    }

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e) {
        //TODO: 
        
        throw new Error("Not implemented");

        /* this.left.compile(c, e);
        this.rigth.compile(c, e);
        
        switch(this.operator)
        {
            case EQUALS:
                break;
            case GREATER_THAN:
                break;
            case GREATER_THAN_OR_EQUAL_TO:
                break;
            case LESS_THAN:
                break;
            case LESS_THAN_OR_EQUAL_TO:
                break;
            case NOT_EQUALS:
                break;
        } */
    }

    @Override
    public IValue eval(Environment<IValue> e)
    {
        VInt val1 = checkRuntimeType(this.left.eval(e));
        VInt val2 = checkRuntimeType(this.rigth.eval(e));

        IValue result = null;
        switch(this.operator)
        {
            case EQUALS:
                result = new VBool(val1.getValue() == val2.getValue());
                break;
            case GREATER_THAN:
                result = new VBool(val1.getValue() > val2.getValue());
                break;
            case GREATER_THAN_OR_EQUAL_TO:
                result = new VBool(val1.getValue() >= val2.getValue());
                break;
            case LESS_THAN:
                result = new VBool(val1.getValue() < val2.getValue());
                break;
            case LESS_THAN_OR_EQUAL_TO:
                result = new VBool(val1.getValue() <= val2.getValue());
                break;
            case NOT_EQUALS:
                result = new VBool(val1.getValue() != val2.getValue());
                break;
        }

        return result;
    }

    @Override
    public IType typecheck(Environment<IType> e) {
        checkType(this.left.typecheck(e));
        checkType(this.rigth.typecheck(e));
        return  type;
    }

    protected VInt checkRuntimeType(IValue val)
    {
        boolean checked = val instanceof VInt;
        if (!checked)
            throw new IllegalOperatorException(this.operator.getOperator());

        return (VInt)val;
    }
    
    protected IType checkType(IType type)
    {
        boolean checked = type instanceof TypeInt;
        if (!checked)
            throw new IllegalOperatorException(this.operator.getOperator());

        return type;
    }
}
