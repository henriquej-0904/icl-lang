package ast.executionFlow.conditionalBranches;

import ast.ASTNode;
import compiler.MainCodeBlock;
import typeError.IllegalOperatorException;
import util.Coordinates;
import util.Environment;
import values.IValue;
import values.primitive.VBool;

public class ASTIfThenElse implements ASTNode
{
    public static final String OPERATOR = "if then else";

    protected ASTNode ifNode, thenNode, elseNode;

    /**
     * @param ifNode
     * @param thenNode
     * @param elseNode
     */
    public ASTIfThenElse(ASTNode ifNode, ASTNode thenNode, ASTNode elseNode) {
        this.ifNode = ifNode;
        this.thenNode = thenNode;
        this.elseNode = elseNode;
    }

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e) {
        // TODO Auto-generated method stub
        throw new Error("Not implemented");
    }

    @Override
    public IValue eval(Environment<IValue> e)
    {
        return checkIfType(this.ifNode.eval(e)).getValue() ?
                this.thenNode.eval(e) : this.elseNode.eval(e);
    }

    protected VBool checkIfType(IValue value)
    {
        boolean checked = value instanceof VBool;

        if (!checked)
            throw new IllegalOperatorException(OPERATOR);

        return (VBool)value;
    }
    
    
}
