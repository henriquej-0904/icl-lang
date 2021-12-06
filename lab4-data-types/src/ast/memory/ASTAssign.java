package ast.memory;

import ast.ASTNode;
import compiler.MainCodeBlock;
import typeError.IllegalOperatorException;
import util.Coordinates;
import util.Environment;
import values.IValue;
import values.VCell;
import values.VVoid;

public class ASTAssign implements ASTNode
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
        VCell cell = checkTypeVCell(this.left.eval(e));
        IValue value = checkTypeValue(this.right.eval(e));
        cell.setValue(value);
        return value;
    }

    protected VCell checkTypeVCell(IValue value)
    {
        boolean checked = value instanceof VCell;

        if (!checked)
            throw new IllegalOperatorException(OPERATOR);

        return (VCell)value;
    }

    protected IValue checkTypeValue(IValue value)
    {
        boolean voidValue = value instanceof VVoid;

        if (voidValue)
            throw new IllegalOperatorException(OPERATOR);

        return value;
    }
    
    
}
