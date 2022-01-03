package ast.executionFlow.loops;

import ast.ASTNode;
import ast.ASTNodeAbstract;
import compiler.MainCodeBlock;
import typeError.IllegalOperatorException;
import types.IType;
import types.primitves.TypeBool;
import util.Coordinates;
import util.Environment;
import values.IValue;
import values.primitive.VBool;

public class ASTWhileLoop extends ASTNodeAbstract
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
        type = TypeBool.TYPE;
    }

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e) {
        String l1, l2;
        l1 = c.getNewId();
        l2 = c.getNewId();

        c.emit(l1 + ":");
        this.whileConditionNode.compile(c, e);
        c.emit("ifeq " + l2);
        this.bodyNode.compile(c, e);
        c.emit("pop");
        c.emit("goto " + l1);
        c.emit(l2 + ":");
    }

    @Override
    public IValue eval(Environment<IValue> e)
    {
        while (checkWhileConditionRuntimeType(this.whileConditionNode.eval(e)).getValue())
        {
            this.bodyNode.eval(e);
        }

        // while always returns false
        return new VBool(false);
    }

    @Override
    public IType typecheck(Environment<IType> e) {
        checkTypeWhile(this.whileConditionNode.typecheck(e));
        this.bodyNode.typecheck(e);
      
        return TypeBool.TYPE;
    }

    protected VBool checkWhileConditionRuntimeType(IValue value)
    {
        boolean checked = value instanceof VBool;

        if (!checked)
            throw new IllegalOperatorException(OPERATOR);

        return (VBool)value;
    }

    protected TypeBool checkTypeWhile(IType type)
    {
        boolean checked = type instanceof TypeBool;

        if (!checked)
            throw new IllegalOperatorException(OPERATOR);

        return (TypeBool)type;
    }
}
