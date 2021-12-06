package ast.executionFlow.loops;

import ast.ASTNode;
import compiler.MainCodeBlock;
import typeError.IllegalOperatorException;
import util.Coordinates;
import util.Environment;
import values.IValue;
import values.primitive.VBool;

public class ASTWhileLoop implements ASTNode
{
    public static final String OPERATOR = "while do";

    protected ASTNode whileConditionNode, bodyNode;

    /**
     * @param whileConditionNode
     * @param bodyNode
     */
    public ASTWhileLoop(ASTNode whileConditionNode, ASTNode bodyNode) {
        this.whileConditionNode = whileConditionNode;
        this.bodyNode = bodyNode;
    }

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e) {
        // TODO Auto-generated method stub
        throw new Error("Not implemented");
    }

    @Override
    public IValue eval(Environment<IValue> e)
    {
        while (checkWhileConditionType(this.whileConditionNode.eval(e)).getValue())
        {
            this.bodyNode.eval(e);
        }

        // while always returns false
        return new VBool(false);
    }

    protected VBool checkWhileConditionType(IValue value)
    {
        boolean checked = value instanceof VBool;

        if (!checked)
            throw new IllegalOperatorException(OPERATOR);

        return (VBool)value;
    }
}
