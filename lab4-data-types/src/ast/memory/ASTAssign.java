package ast.memory;

import ast.ASTNode;
import ast.ASTNodeAbstract;
import compiler.MainCodeBlock;
import typeError.IllegalOperatorException;
import typeError.TypeErrorException;
import types.IType;
import types.TypeRef;
import types.TypeVoid;
import util.Coordinates;
import util.Environment;
import values.IValue;
import values.VCell;
import values.VVoid;

public class ASTAssign extends ASTNodeAbstract
{
    public static final String OPERATOR = ":=";

    protected ASTNode left, right;

    /**
     * @param left
     * @param right
     */
    public ASTAssign(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e) {
        // TODO Auto-generated method stub
        throw new Error("Not implemented");
    }

    @Override
    public IValue eval(Environment<IValue> e)
    {
        VCell cell = checkRuntimeTypeVCell(this.left.eval(e));
        IValue value = checkRuntimeTypeValue(this.right.eval(e));
        cell.setValue(value);
        return value;
    }

    @Override
    public IType typecheck(Environment<IType> e) {
        TypeRef ref = checkTypeRef(this.left.typecheck(e));
        IType valueType = checkTypeValue(this.right.typecheck(e));

        // check if the type of the value in this reference equals the valueType
        boolean checked = ref.getValueType().equals(valueType);
        if (!checked)
            throw new TypeErrorException(
                String.format("Incompatible type for assignment - %s.\nExpected value type: %s\n",
                    valueType.show(), ref.getValueType().show()));
        type = valueType;
        return valueType;
    }

    protected VCell checkRuntimeTypeVCell(IValue value)
    {
        boolean checked = value instanceof VCell;

        if (!checked)
            throw new IllegalOperatorException(OPERATOR, "Cannot assign a value to a non reference type.");

        return (VCell)value;
    }

    protected IValue checkRuntimeTypeValue(IValue value)
    {
        boolean voidValue = value instanceof VVoid;

        if (voidValue)
            throw new IllegalOperatorException(OPERATOR, "Cannot set reference value to 'void'.");

        return value;
    }

    protected TypeRef checkTypeRef(IType type)
    {
        boolean checked = type instanceof TypeRef;

        if (!checked)
            throw new IllegalOperatorException(OPERATOR, "Cannot assign a value to a non reference type.");

        return (TypeRef)type;
    }

    protected IType checkTypeValue(IType type)
    {
        boolean voidValue = type instanceof TypeVoid;

        if (voidValue)
            throw new IllegalOperatorException(OPERATOR, "Cannot set reference value to 'void'.");

        return type;
    }
}
