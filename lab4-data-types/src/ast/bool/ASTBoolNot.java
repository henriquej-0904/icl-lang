package ast.bool;

import ast.ASTNode;
import compiler.MainCodeBlock;
import typeError.IllegalOperatorException;
import util.Coordinates;
import util.Environment;
import values.IValue;
import values.primitive.VBool;

public class ASTBoolNot implements ASTNode
{
    public static final String OPERATOR = "~";

    protected ASTNode node;

    /**
     * @param node
     */
    public ASTBoolNot(ASTNode node) {
        this.node = node;
    }

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e) {
        // TODO Auto-generated method stub
        throw new Error("Not implemented");
    }

    @Override
    public IValue eval(Environment<IValue> e)
    {
        return new VBool( !checkType(this.node.eval(e)).getValue() );
    }

    protected VBool checkType(IValue val)
    {
        boolean checked = val instanceof VBool;
        if (!checked)
            throw new IllegalOperatorException(OPERATOR);

        return (VBool)val;
    }
}
