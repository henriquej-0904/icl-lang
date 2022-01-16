package ast.binaryOperations;

import ast.ASTNode;
import ast.ASTNodeAbstract;
import ast.binaryOperations.operators.RelationalBinaryOperator;
import compiler.Coordinates;
import compiler.MainCodeBlock;
import environment.Environment;
import typeError.IllegalOperatorException;
import types.IType;
import types.primitives.TypeBool;
import types.primitives.TypeInt;
import values.IValue;
import values.primitive.VBool;
import values.primitive.VPrimitive;

public class ASTRelationalBinaryOperation extends ASTNodeAbstract
{
    protected final ASTNode left, rigth;

    protected final RelationalBinaryOperator operator;

    /**
     * @param left
     * @param rigth
     */
    public ASTRelationalBinaryOperation(ASTNode left, ASTNode rigth, RelationalBinaryOperator operator) {
        this.left = left;
        this.rigth = rigth;
        this.operator = operator;
        type = TypeBool.TYPE;
    }

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e)
    {
        this.left.compile(c, e);
        this.rigth.compile(c, e);
        String l1 = c.getNewLabelId();
        String l2 = c.getNewLabelId();
        switch(this.operator)
        {
            case EQUALS:
            c.emit("if_icmpeq " + l1);
            c.emit("sipush 0");
            c.emit("goto " + l2);
            c.emit(l1 + ":");
            c.emit("sipush 1");
            c.emit(l2 + ":");
                break;
            case GREATER_THAN:
            c.emit("if_icmpgt " + l1);
            c.emit("sipush 0");
            c.emit("goto " + l2);
            c.emit(l1 + ":");
            c.emit("sipush 1");
            c.emit(l2 + ":");
                break;
            case GREATER_THAN_OR_EQUAL_TO:
            c.emit("if_icmpge " + l1);
            c.emit("sipush 0");
            c.emit("goto " + l2);
            c.emit(l1 + ":");
            c.emit("sipush 1");
            c.emit(l2 + ":");
                break;
            case LESS_THAN:
            c.emit("if_icmplt " + l1);
            c.emit("sipush 0");
            c.emit("goto " + l2);
            c.emit(l1 + ":");
            c.emit("sipush 1");
            c.emit(l2 + ":");
                break;
            case LESS_THAN_OR_EQUAL_TO:
            c.emit("if_icmple " + l1);
            c.emit("sipush 0");
            c.emit("goto " + l2);
            c.emit(l1 + ":");
            c.emit("sipush 1");
            c.emit(l2 + ":");
                break;
            case NOT_EQUALS:
            c.emit("if_icmpne " + l1);
            c.emit("sipush 0");
            c.emit("goto " + l2);
            c.emit(l1 + ":");
            c.emit("sipush 1");
            c.emit(l2 + ":");
                break;
        } 
    }

    @Override
    public IValue eval(Environment<IValue> e)
    {
        VPrimitive<?> val1 = checkRuntimeType(this.left.eval(e));
        VPrimitive<?> val2 = checkRuntimeType(this.rigth.eval(e));

        // Check if val1 type equals val2 type
        if (!val1.getClass().equals(val2.getClass()))
            throw new IllegalOperatorException("The type of the 2 expressions must be equal.",
                this.operator.getOperator(), val1.getPrimitiveType().show(), val2.getPrimitiveType().show());

        IValue result = null;
        switch(this.operator)
        {
            case EQUALS:
                result = new VBool(val1.equals(val2));
                break;
            case GREATER_THAN:
                result = new VBool(vPrimitiveCompareTo(val1, val2) > 0);
                break;
            case GREATER_THAN_OR_EQUAL_TO:
                result = new VBool(vPrimitiveCompareTo(val1, val2) >= 0);
                break;
            case LESS_THAN:
                result = new VBool(vPrimitiveCompareTo(val1, val2) < 0);
                break;
            case LESS_THAN_OR_EQUAL_TO:
                result = new VBool(vPrimitiveCompareTo(val1, val2) <= 0);
                break;
            case NOT_EQUALS:
                result = new VBool(!val1.equals(val2));
                break;
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    private static int vPrimitiveCompareTo(VPrimitive<?> value1, VPrimitive<?> value2)
    {
        VPrimitive val1 = (VPrimitive)value1;
        VPrimitive val2 = (VPrimitive)value2;
        return val1.compareTo(val2);
    }

    @Override
    public IType typecheck(Environment<IType> e) {
        checkType(this.left.typecheck(e));
        checkType(this.rigth.typecheck(e));
        return  type;
    }

    protected VPrimitive<?> checkRuntimeType(IValue val)
    {
        boolean checked = (val instanceof VPrimitive);

        if (!checked)
            throw new IllegalOperatorException(this.operator.getOperator(), "Primitive", val.getType().show());

        return (VPrimitive<?>)val;
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