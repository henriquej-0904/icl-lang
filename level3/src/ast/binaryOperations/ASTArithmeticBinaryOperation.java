package ast.binaryOperations;

import ast.ASTNode;
import ast.ASTNodeAbstract;
import ast.binaryOperations.operators.ArithmeticBinaryOperator;
import compiler.Coordinates;
import compiler.MainCodeBlock;
import environment.Environment;
import typeError.IllegalOperatorException;
import types.IType;
import types.primitives.TypeInt;
import types.primitives.TypeString;
import values.IValue;
import values.primitive.VBool;
import values.primitive.VInt;
import values.primitive.VPrimitive;
import values.primitive.VString;

public class ASTArithmeticBinaryOperation extends ASTNodeAbstract
{
    protected final ASTNode left, rigth;

    protected final ArithmeticBinaryOperator operator;

    /**
     * @param left
     * @param rigth
     */
    public ASTArithmeticBinaryOperation(ASTNode left, ASTNode rigth, ArithmeticBinaryOperator operator) {
        this.left = left;
        this.rigth = rigth;
        this.operator = operator;
        type = TypeInt.TYPE;
    }

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e) {
        this.left.compile(c, e);
        this.rigth.compile(c, e);
        
        switch(this.operator)
        {
            case ADD:
                c.emit("iadd");
                break;
            case DIV:
                c.emit("idiv");
                break;
            case MUL:
                c.emit("imul");
                break;
            case SUB:
                c.emit("isub");
                break;
        }
    }

    @Override
    public IValue eval(Environment<IValue> e)
    {
        VPrimitive<?> val1 = checkRuntimeType(this.left.eval(e));
        VPrimitive<?> val2 = checkRuntimeType(this.rigth.eval(e));

        if (val1 instanceof VInt)
            return evalInt((VInt)val1, val2);
        
        return evalString((VString)val1, val2);
    }

    private IValue evalInt(VInt value1, VPrimitive<?> value2)
    {
        VInt valueInt2 = checkRuntimeTypeInt(value2);
        IValue result = null;

        switch(this.operator)
        {
            case ADD:
                result = new VInt(value1.getValue() + valueInt2.getValue());
                break;
            case DIV:
                result = new VInt(value1.getValue() / valueInt2.getValue());
                break;
            case MUL:
                result = new VInt(value1.getValue() * valueInt2.getValue());
                break;
            case SUB:
                result = new VInt(value1.getValue() - valueInt2.getValue());
                break;
        }

        return result;
    }

    private IValue evalString(VString value1, VPrimitive<?> value2)
    {
        IValue result = null;

        switch(this.operator)
        {
            case ADD:
                result = new VString(value1.getValue() + value2.getValue());
                break;
            default:
                throw new IllegalOperatorException(operator.getOperator(), TypeString.TYPE.show());
        }

        return result;
    }

    @Override
    public IType typecheck(Environment<IType> e) {
        checkType(this.left.typecheck(e));
        checkType(this.rigth.typecheck(e));
        return type;
    }

    protected VPrimitive<?> checkRuntimeType(IValue val)
    {
        boolean checked = (val instanceof VPrimitive) && !(val instanceof VBool);

        if (!checked)
            throw new IllegalOperatorException(this.operator.getOperator(), "Integer or String", val.getType().show());

        return (VPrimitive<?>)val;
    }

    protected VInt checkRuntimeTypeInt(IValue val)
    {
        boolean checked = (val instanceof VInt);

        if (!checked)
            throw new IllegalOperatorException(this.operator.getOperator(), TypeInt.TYPE.show(), val.getType().show());

        return (VInt)val;
    }

    protected IType checkType(IType type)
    {
        boolean checked =  type instanceof TypeInt;
        if (!checked)
            throw new IllegalOperatorException(this.operator.getOperator(), TypeInt.TYPE.show(), type.show());

        return type;
    }

    @Override
    public StringBuilder toString(StringBuilder builder) {
        this.left.toString(builder);
        builder.append(' ');
        builder.append(this.operator.getOperator());
        builder.append(' ');
        this.rigth.toString(builder);

        return builder;
    }
}
