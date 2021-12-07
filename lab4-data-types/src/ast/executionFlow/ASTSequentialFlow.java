package ast.executionFlow;

import ast.ASTNode;
import compiler.MainCodeBlock;
import types.IType;
import util.Coordinates;
import util.Environment;
import values.IValue;

public class ASTSequentialFlow implements ASTNode
{
    public static final String OPERATOR = ";";
    
    protected ASTNode left, right;

    /**
     * @param left
     * @param right
     */
    public ASTSequentialFlow(ASTNode left, ASTNode right) {
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
        this.left.eval(e);

        // returns the value of the right node.
        return this.right.eval(e);
    }

    @Override
    public IType typecheck(Environment<IType> e) {
        this.left.typecheck(e);
        return this.right.typecheck(e);
    }
}
