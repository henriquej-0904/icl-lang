package ast.print;

import ast.ASTNode;
import compiler.MainCodeBlock;
import types.IType;
import types.TypeVoid;
import util.Coordinates;
import util.Environment;
import values.IValue;
import values.VVoid;

public class ASTPrintln implements ASTNode
{
    public static final String OPERATOR = "println";

    protected ASTNode node;

    /**
     * @param node
     */
    public ASTPrintln(ASTNode node) {
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
        IValue result = this.node.eval(e);
        System.out.println(result.show());

        // println returns nothing (void)
        return VVoid.V_VOID;
    }

    @Override
    public IType typecheck(Environment<IType> e)
    {
        this.node.typecheck(e);
        
        // println returns nothing (void)
        return TypeVoid.TYPE;
    }
    
}
