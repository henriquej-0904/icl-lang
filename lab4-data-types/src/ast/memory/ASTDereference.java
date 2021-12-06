package ast.memory;

import ast.ASTNode;
import compiler.MainCodeBlock;
import typeError.IllegalOperatorException;
import util.Coordinates;
import util.Environment;
import values.IValue;
import values.VCell;

public class ASTDereference implements ASTNode
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
        return checkType(this.reference.eval(e)).getValue();
    }

    protected VCell checkType(IValue value)
    {
        boolean checked = value instanceof VCell;

        if (!checked)
            throw new IllegalOperatorException(OPERATOR);

        return (VCell)value;
    }

    
}
