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
        return new VBool( !checkRuntimeType(this.node.eval(e)).getValue() );
    }

    @Override
    public IType typecheck(Environment<IType> e) {
        return checkType(this.node.typecheck(e));
        
    }

    protected VBool checkRuntimeType(IValue val)
    {
        boolean checked = val instanceof VBool;
        if (!checked)
            throw new IllegalOperatorException(OPERATOR, TypeBool.TYPE.show(), val.show());

        return (VBool)val;
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
