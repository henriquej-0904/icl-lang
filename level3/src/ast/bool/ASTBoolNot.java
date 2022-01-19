package ast.bool;

import ast.ASTNode;
import ast.ASTNodeAbstract;
import ast.ASTNodeShortCircuit;
import compiler.Coordinates;
import compiler.MainCodeBlock;
import environment.Environment;
import typeError.IllegalOperatorException;
import types.IType;
import types.primitives.TypeBool;
import util.Utils;
import values.IValue;
import values.primitive.VBool;

public class ASTBoolNot extends ASTNodeAbstract implements ASTNodeShortCircuit
{
    public static final String OPERATOR = "~";

    protected ASTNode node;

    /**
     * @param node
     */
    public ASTBoolNot(ASTNode node) {
        this.node = node;
        type = TypeBool.TYPE;
    }

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e) {
        node.compile(c, e);
        c.emit("sipush 1");
        c.emit("ixor");
    }

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e, String tl, String fl)
    {
        if (this.node instanceof ASTNodeShortCircuit)
            ((ASTNodeShortCircuit)this.node).compile(c, e, fl, tl);
        else
        {
            this.node.compile(c, e);
            c.emit("ifeq " + tl);
            c.emit("goto " + fl);
        }
    }

    @Override
    public IValue eval(Environment<IValue> e)
    {
        VBool value = Utils.checkValueForOperation(this.node.eval(e), VBool.class, OPERATOR);
        return new VBool( !value.getValue() );
    }

    @Override
    public IType typecheck(Environment<IType> e) {
        return checkType(this.node.typecheck(e));
        
    }

    @Override
    protected IValue check(IValue value) {
        return super.check(value);
    }

    @Override
    protected IType check(IType type) {
        // TODO Auto-generated method stub
        return super.check(type);
    }

    protected IType checkType(IType type)
    {
        boolean checked =  type instanceof TypeBool;
        if (!checked){
            throw new IllegalOperatorException(OPERATOR, TypeBool.TYPE.show(), type.show());    
        }
            
        return type;
    }

    @Override
    public StringBuilder toString(StringBuilder builder) {
        builder.append(OPERATOR);
        this.node.toString(builder);
        return builder;
    }
}
